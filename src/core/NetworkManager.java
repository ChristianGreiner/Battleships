package core;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import network.NetworkListener;
import network.NetworkMessage;
import network.NetworkType;

import java.io.IOException;
import java.util.ArrayList;

public class NetworkManager{

    public ArrayList<NetworkListener> getListeners() {
        return listeners;
    }

    public final static int TCP_PORT = 50000;
    public final static int UDP_PORT = 50001;
    private ArrayList<NetworkListener> listeners = new ArrayList<>();
    private Server server;
    private Client client;
    private NetworkType networkType;
    private boolean serverConfirmed, clientConfirmed;

    public boolean isServerConfirmed() {
        return serverConfirmed;
    }

    public boolean isClientConfirmed() {
        return clientConfirmed;
    }

    public NetworkType getNetworkType() {
        return networkType;
    }

    public NetworkManager() {
    }

    public void addNetworkListener(NetworkListener listener) {
        this.listeners.add(listener);
    }

    public void joinServer(String host) {
        this.client = new Client();
        this.client.start();
        try {
            this.client.connect(5000, "localhost", TCP_PORT, UDP_PORT);

            Kryo kryo = this.client.getKryo();
            kryo.register(NetworkMessage.class);

            this.networkType = NetworkType.Client;

            this.client.addListener(new Listener() {
                public void received (Connection connection, Object object) {

                    if(object instanceof NetworkMessage) {
                        NetworkMessage message = (NetworkMessage)object;

                        // first call
                        if(message.text.contains("size")) {
                            for (NetworkListener listener : listeners) {
                                listener.OnGameJoined(Integer.parseInt(message.text.split(" ")[1]));
                            }
                        }

                        if(message.text.contains("confirmed")) {
                            serverConfirmed = true;
                        }
                    }

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startServer() {
        try {
            this.server = new Server();
            this.server.start();
            this.server.bind(TCP_PORT, UDP_PORT);

            for (NetworkListener listener : this.listeners) {
                listener.OnServerStarted();
            }

            Kryo kryo = this.server.getKryo();
            kryo.register(NetworkMessage.class);


            this.networkType = NetworkType.Host;

            this.server.addListener(new Listener() {

                public void connected (Connection connection) {
                    for (NetworkListener listener : listeners) {
                        listener.OnPlayerConnected();
                    }
                }

                public void received (Connection connection, Object object) {

                    if(object instanceof NetworkMessage) {
                        NetworkMessage message = (NetworkMessage) object;
                        if (message.text.contains("confirmed")) {
                            clientConfirmed = true;
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        if(this.server != null) {
            this.server.stop();
            this.server = null;
        }
    }

    public void sendMessageToServer(NetworkMessage message) {
        if(this.client != null) {
            this.client.sendTCP(message);
        }
    }

    public void sendMessageToClient(NetworkMessage message) {
        if(this.server != null) {
            this.server.sendToAllTCP(message);
        }
    }
}
