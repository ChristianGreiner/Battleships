package core;

import network.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkManager{

    private ArrayList<NetworkListener> listeners = new ArrayList<>();
    private Client client;
    private Server server;
    private boolean clientCreated = false;
    private boolean serverCreated = false;

    public NetworkManager() {
    }

    public void addNetworkListener(NetworkListener listener) {
        this.listeners.add(listener);
    }

    public void joinSession(String ip, int port) {
        if(!clientCreated) {
            this.client = new Client(ip, port);
            Thread clientThread = new Thread(this.client);
            clientThread.start();
            clientCreated = true;
        }
    }

    public void startSession(int port) {
        if(!serverCreated) {
            this.server = new Server(port);
            Thread server = new Thread(this.server);
            server.start();
            serverCreated = true;
        }
    }

    public void sendServerMessage(NetworkMessage message) {
        if(this.server != null) {
            if(this.server.getServerThread() != null) {
                this.server.getServerThread().sendCommand(message.create());
            }
        }
    }

    public void sendClientMessage(NetworkMessage message) {
        if(this.client != null) {
            if(this.client.getClientThread() != null) {
                this.client.getClientThread().sendCommand(message.create());
            }
        }
    }
}
