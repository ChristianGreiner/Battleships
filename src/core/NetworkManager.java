package core;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import game.HitType;
import network.NetworkListener;
import network.NetworkType;

import java.awt.*;
import java.util.ArrayList;

public class NetworkManager implements NetworkListener{

    public ArrayList<NetworkListener> getListeners() {
        return listeners;
    }

    public final static int TCP_PORT = 50000;
    public final static int UDP_PORT = 50001;
    private ArrayList<NetworkListener> listeners = new ArrayList<>();
    private NetworkType networkType;
    private Server server;
    private Client client;
    private boolean canSendMessages = false;

    public boolean isGameStarted() {
        return gameStarted;
    }

    private boolean gameStarted;
    private boolean clientConfirmed, hostConfirmed;

    public NetworkType getPlayerTurn() {
        return playerTurn;
    }

    private NetworkType playerTurn = NetworkType.Client;

    public NetworkType getNetworkType() {
        return networkType;
    }

    public NetworkManager() {
        this.listeners.add(this);
    }

    public void addNetworkListener(NetworkListener listener) {
        this.listeners.add(listener);
    }

    private boolean prepareShotMessage(String message) {
        String[] size = message.split(" ");
        if(size.length > 0) {
            if (size[1] != null && size[2] != null) {
                if (!size[1].isEmpty() && !size[2].isEmpty()) {
                    int x = Integer.parseInt(size[1]);
                    int y = Integer.parseInt(size[2]);
                    for (NetworkListener listener : Game.getInstance().getNetworkManager().getListeners()) {
                        listener.OnReceiveShot(new Point(x, y));
                    }

                    return true;
                }
            }
        }
        return false;
    }

    private boolean prepareAnswerMessage(String message) {
        String[] size = message.split(" ");
        if(size.length > 0) {
            if (size[1] != null) {
                if (!size[1].isEmpty()) {
                    int value = Integer.parseInt(size[1]);

                    HitType hitType = HitType.Water;
                    if(value == 0)
                        hitType = HitType.Water;
                    else if(value == 1)
                        hitType = HitType.Ship;
                    else if(value == 2)
                        hitType = HitType.ShipDestroyed;

                    for (NetworkListener listener : Game.getInstance().getNetworkManager().getListeners()) {
                        listener.OnReceiveAnswer(hitType);
                    }

                    return true;
                }
            }
        }

        return false;
    }

    public void joinServer(String host) {
        try {

            this.client = new Client();
            this.client.start();
            this.client.connect(5000, "localhost", TCP_PORT);

            Game.getInstance().getWindow().setTitle(Game.getInstance().getWindow().getTitle() + " (CLIENT)");

            this.client.addListener(new Listener() {
                public void connected (Connection connection) {

                }

                public void received (Connection connection, Object object) {
                    if(object instanceof String) {
                        String message = (String)object;

                        System.out.println("CLIENT RECEIVED " + message);

                        if(gameStarted) {
                            if(message.contains("SHOT")) {
                                boolean ok = prepareShotMessage(message);
                            } else if(message.contains("ANSWER")) {
                                boolean ok = prepareAnswerMessage(message);
                            }
                        } else {
                            if(message.contains("SIZE")) {
                                String[] size = message.split(" ");
                                if(size.length > 0) {
                                    if (size[1] != null) {
                                        if (!size[1].isEmpty()) {
                                            int mapSize = Integer.parseInt(size[1]);
                                            for (NetworkListener listener : Game.getInstance().getNetworkManager().getListeners()) {
                                                listener.OnGameJoined(mapSize);
                                            }
                                            playerTurn = NetworkType.Client;
                                        }
                                    }
                                }
                            } else if(message.contains("CONFIRMED")) {
                                hostConfirmed = true;
                                gameStarted = true;
                            }
                        }
                    }
                }

                public void disconnected (Connection c) {
                }
            });

            this.networkType = NetworkType.Client;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startServer(int mapSize) {
        try {

            this.server = new Server();
            this.server.start();
            this.server.bind(TCP_PORT);

            Game.getInstance().getWindow().setTitle(Game.getInstance().getWindow().getTitle() + " (SERVER)");

            this.server.addListener(new Listener() {

                public void connected (Connection connection) {
                    // if player connected
                    for (NetworkListener listener : Game.getInstance().getNetworkManager().getListeners()) {
                        listener.OnPlayerConnected();
                    }

                    connection.sendTCP("SIZE " + mapSize);
                    playerTurn = NetworkType.Client;
                }

                public void received (Connection connection, Object object) {
                    if(object instanceof String) {
                        String message = (String)object;

                        System.out.println("SERVER RECEIVED " + message);

                        if(gameStarted) {
                            if(message.contains("SHOT")) {
                                boolean ok = prepareShotMessage(message);
                            } else if(message.contains("ANSWER")) {
                                boolean ok = prepareAnswerMessage(message);
                            }
                        } else {
                            if(message.contains("CONFIRMED")) {
                                clientConfirmed = true;
                            }
                        }
                    }
                }

                public void disconnected (Connection connection) {
                }
            });

            this.networkType = NetworkType.Host;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {

    }

    public void sendConfirmMessage() {
        String message = "CONFIRMED";
        if(this.networkType == NetworkType.Client)
            this.client.sendTCP(message);
        else {

            // waiting till client sends first confirm messages
            while (true) {
                if(clientConfirmed) {
                    Game.getInstance().getWindow().setTitle("CLIENT CONFIRMED");
                    this.server.sendToAllTCP(message);
                    this.gameStarted = true;
                    this.playerTurn = NetworkType.Client;
                    break;
                } else {
                    Game.getInstance().getWindow().setTitle("WAITING FOR CLIENT");
                }
            }
        }
    }

    public void sendShotMessage(Point pos) {
        if(pos != null) {
            System.out.println(this.networkType.toString() + " tries to send shot message");

            String message = "SHOT " + pos.x + " " + pos.y;
            sendMessage(message);
        }
    }

    public void sendAnswerMessage(HitType type) {
        int value = 0;
        if(type == HitType.Water)
            value = 0;
        else if(type == HitType.Ship)
            value = 1;
        else if(type == HitType.ShipDestroyed)
            value = 2;

        String message = "ANSWER " + value;
        System.out.println(networkType.toString() + " sends answer message");
        sendMessage(message);
    }

    private void sendMessage(String message) {
        if(this.playerTurn == this.networkType) {
            if(this.networkType == NetworkType.Client) {
                this.client.sendTCP(message);
            } else
                this.server.sendToAllTCP(message);
        }
    }

    @Override
    public void OnPlayerConnected() {
    }

    @Override
    public void OnGameJoined(int mapSize) {
    }

    @Override
    public void OnOpponentConfirmed() {
    }

    @Override
    public void OnServerStarted() {
    }

    @Override
    public void OnGameStarted() {
    }

    @Override
    public void OnReceiveShot(Point pos) {
    }

    @Override
    public void OnReceiveAnswer(HitType type) {
    }
}
