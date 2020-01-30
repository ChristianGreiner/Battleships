package network;


import core.Game;
import game.HitType;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class NetworkThread extends Thread {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private NetworkType networkType;
    private NetworkManager networkManager;
    private int mapSize = -1;
    private String saveGameId = null;
    private volatile boolean confirmed = false;
    private BlockingQueue<String> messageQueue = new ArrayBlockingQueue<String>(1);

    public synchronized void setConfirmed() {
        this.confirmed = true;
    }

    public synchronized void addMessage(String message) {
        if(this.messageQueue.isEmpty()) {
            this.messageQueue.add(message);
        }
    }

    public NetworkThread(NetworkManager networkManager, ServerSocket serverSocket, int mapSize) {
        this.networkManager = networkManager;
        this.serverSocket = serverSocket;
        this.networkType = NetworkType.Host;
        this.mapSize = mapSize;
    }

    public NetworkThread(NetworkManager networkManager, ServerSocket serverSocket, String saveGameId) {
        this.networkManager = networkManager;
        this.serverSocket = serverSocket;
        this.networkType = NetworkType.Host;
        this.saveGameId = saveGameId;
    }

    public NetworkThread(NetworkManager networkManager, Socket socket) {
        this.networkManager = networkManager;
        this.clientSocket = socket;
        this.networkType = NetworkType.Client;
    }

    private Point parseShotMessage(String message) {
        String[] size = message.split(" ");
        if(size.length > 0) {
            if (size[1] != null && size[2] != null) {
                if (!size[1].isEmpty() && !size[2].isEmpty()) {
                    int x = Integer.parseInt(size[1]);
                    int y = Integer.parseInt(size[2]);
                    return new Point(x, y);
                }
            }
        }
        return null;
    }

    private int parseSizeMessage(String message) {
        String[] size = message.split(" ");
        if(size.length > 0) {
            if(size[1] != null) {
                if(!size[1].isEmpty()) {
                    return Integer.parseInt(size[1]);
                }
            }
        }
        return 0;
    }

    private String parseSaveLoadMessage(String message) {
        String[] size = message.split(" ");
        if(size.length > 0) {
            if(size[1] != null) {
                if(!size[1].isEmpty()) {
                    return size[1];
                }
            }
        }
        return null;
    }
    private HitType parseAnswerMessage(String message) {
        String[] messageArray = message.split(" ");
        if(messageArray.length > 0) {
            if(messageArray[1] != null) {
                if(!messageArray[1].isEmpty()) {
                    HitType hitType = HitType.Water;
                    String value = messageArray[1];
                    if(value.equals("0")) {
                        hitType = HitType.Water;
                    } else if(value.equals("1")) {
                        hitType = HitType.Ship;
                    } else if(value.equals("2")) {
                        hitType = HitType.ShipDestroyed;
                    }

                    return hitType;
                }
            }
        }
        return null;
    }

    @Override
    public void run() {
        super.run();

        try {
            while (true) {
                BufferedReader in = null;
                Writer out =  null;

                // HOST STUFF
                if(this.networkType == NetworkType.Host) {

                    // Wait for client connects to the game session
                    Socket socket = null;
                    try {
                        socket = this.serverSocket.accept();

                    } catch (Exception ex) {
                        System.out.println("SERVER CLOSED");
                        break;
                    }

                    Game.getInstance().getLogger().info("Player connected");

                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out = new OutputStreamWriter(socket.getOutputStream());

                    for (int i = 0; i < this.networkManager.getListeners().size(); i++) {
                        this.networkManager.getListeners().get(i).OnPlayerConnected();
                    }

                    // 1. Sends the map size
                    System.out.println("[SERVER] Sending Map Size");
                    if(this.mapSize != -1) {
                        Game.getInstance().getLogger().info(this.networkType.toString() +  ": Sends message: " + "SIZE " + this.mapSize);
                        out.write(String.format("%s%n", "SIZE " + this.mapSize));
                    }
                    else if(this.saveGameId != null) {
                        Game.getInstance().getLogger().info(this.networkType.toString() +  ": Sends message: " + "LOAD " + this.saveGameId);
                        out.write(String.format("%s%n", "LOAD " + this.saveGameId));
                    }

                    out.flush();

                    // wait till client sends confirmed
                    while (true) {
                        String message = in.readLine();
                        if (message == null) continue;
                        message = message.toUpperCase();
                        if(message.contains("CONFIRMED")) {
                            Game.getInstance().getLogger().info(this.networkType.toString() +  ": Gets message: CONFIRMED");
                            System.out.println("[Server] Client CONFIRMED");
                        }
                        break;
                    }

                    // sends confirmed when server is ready
                    while (true) {
                        if(!this.confirmed) continue;
                        System.out.println("[SERVER] Sending confirmed");
                        Game.getInstance().getLogger().info(this.networkType.toString() +  ": Send message: CONFIRMED");
                        out.write(String.format("%s%n", "CONFIRMED"));
                        out.flush();

                        for (NetworkListener listener : this.networkManager.getListeners()) {
                            listener.OnGameStarted();
                        }
                        break;
                    }

                    while (true) {
                        while (true) {
                            String message = in.readLine();
                            if (message == null) continue;
                            message = message.toUpperCase();
                            Game.getInstance().getLogger().info(this.networkType.toString() +  ": Get message: " + message);
                            handlingMessage(message);
                            break;
                        }

                        while (true) {
                            String message = this.messageQueue.take();
                            if (message == null) continue;
                            message = message.toUpperCase();
                            Game.getInstance().getLogger().info(this.networkType.toString() +  ": Send message: " + message);
                            out.write(String.format("%s%n", message));
                            out.flush();
                            break;
                        }
                    }

                // CLIENT STUFF
                } else {
                    in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
                    out = new OutputStreamWriter(this.clientSocket.getOutputStream());

                    // Waiting for getting the map size or the load id
                    while(true) {
                        String message = in.readLine();
                        if (message == null) continue;
                        message = message.toUpperCase();
                        if(message.contains("SIZE")) {
                            Game.getInstance().getLogger().info(this.networkType.toString() +  ": Gets message: " + message);
                            int mapSize = parseSizeMessage(message);
                            if(mapSize >= 5 && mapSize <= 30) {
                                for (NetworkListener listener : this.networkManager.getListeners()) {
                                    listener.OnGameJoined(mapSize);
                                }
                            }
                        } else if(message.contains("LOAD")) {
                            Game.getInstance().getLogger().info(this.networkType.toString() +  ": Gets message: " + message);
                            String id = parseSaveLoadMessage(message);
                            for (NetworkListener listener : this.networkManager.getListeners()) {
                                listener.OnReceiveLoad(id);
                            }
                        }
                        break;
                    }

                    // waiting for sending the confirmed message
                    while (true) {
                        if(!this.confirmed) continue;
                        System.out.println("[CLIENT] Sending confirmed");
                        Game.getInstance().getLogger().info(this.networkType.toString() +  ": Send message: CONFIRMED");
                        out.write(String.format("%s%n", "CONFIRMED"));
                        out.flush();
                        break;
                    }

                    //  waiting for getting the confirmed message by the server
                    while (true) {
                        String message = in.readLine();
                        if (message == null) continue;
                        message = message.toUpperCase();
                        if(message.contains("CONFIRMED")) {
                            Game.getInstance().getLogger().info(this.networkType.toString() +  ": Gets message: " + message);
                            System.out.println("Server confirmed");
                            Game.getInstance().getLogger().info(this.networkType.toString() +  ": Listeners:" + this.networkManager.getListeners().size());
                            for (NetworkListener listener : this.networkManager.getListeners()) {
                                Game.getInstance().getLogger().info(this.networkType.toString() +  ": Listener:" + listener);
                                listener.OnGameStarted();
                            }
                        }
                        break;
                    }

                    while (true) {
                        while (true) {
                            String message = this.messageQueue.take();
                            if (message == null) continue;
                            message = message.toUpperCase();
                            Game.getInstance().getLogger().info(this.networkType.toString() +  ": Send message: " + message);
                            out.write(String.format("%s%n", message));
                            out.flush();
                            break;
                        }

                        while (true) {
                            String message = in.readLine();
                            if (message == null) continue;
                            message = message.toUpperCase();
                            Game.getInstance().getLogger().info(this.networkType.toString() +  ": Get message: " + message);
                            handlingMessage(message);
                            break;
                        }
                    }
                }
            }
        } catch (Exception ex)  {

        }
    }

    private void handlingMessage(String message) {
        message = message.toUpperCase();
        if(!message.isEmpty()) {
            if(message.contains("SHOT")) {
                Point location = parseShotMessage(message);
                for (NetworkListener listener : this.networkManager.getListeners()) {
                    listener.OnReceiveShot(location);
                }
            } else if(message.contains("ANSWER")) {
                HitType hitType = parseAnswerMessage(message);
                for (NetworkListener listener : this.networkManager.getListeners()) {
                    listener.OnReceiveAnswer(hitType);
                }
            }else if(message.contains("SAVE")) {
                String id = parseSaveLoadMessage(message);
                Game.getInstance().getLogger().info(this.networkType.toString() +  ": Getting SAVE: " + id);
                for (NetworkListener listener : this.networkManager.getListeners()) {
                    listener.OnReceiveSave(id);
                }
            } else if(message.contains("LOAD")) {
                String id = parseSaveLoadMessage(message);
                Game.getInstance().getLogger().info(this.networkType.toString() +  ": Getting LOAD: " + id);
                for (NetworkListener listener : this.networkManager.getListeners()) {
                    listener.OnReceiveLoad(id);
                }
            } else if(message.contains("PASS")) {
                Game.getInstance().getLogger().info(this.networkType.toString() +  ": GETTING PASS: ");
                for (NetworkListener listener : this.networkManager.getListeners()) {
                    listener.OnReceivePass();
                }
            }
        }
    }

    public void stopNetwork() {
        try {
            if(this.serverSocket != null) {
                this.serverSocket.close();
            }
            if(this.clientSocket != null) {
                this.clientSocket.close();
            }
        } catch (IOException e) {
        }
    }
}
