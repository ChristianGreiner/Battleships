package core;

import network.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkManager{

    public ArrayList<NetworkListener> getListeners() {
        return listeners;
    }

    private ArrayList<NetworkListener> listeners = new ArrayList<>();
    private ExecutorService pool;
    private boolean clientCreated = false;
    private boolean serverCreated = false;
    private Client client;
    private Server server;

    public NetworkManager() {
        this.pool = Executors.newFixedThreadPool(2);
    }

    public void addNetworkListener(NetworkListener listener) {
        this.listeners.add(listener);
    }

    public void joinServer(String host, int port) {
        if(!clientCreated) {
            this.client = new Client(this, host, port);
            clientCreated = this.client.kickStart();
        }
    }

    public void startServer(int port) {
        if(!serverCreated) {
            this.server = new Server(this);
            this.server.start();
            serverCreated = true;
        }
    }

    public void stopServer() {
        if(serverCreated) {
            this.server.stopThread();
            this.server = null;
            this.serverCreated = false;
        }
    }

    public void sendServerMessage(NetworkMessage message) {
        if(this.serverCreated) {
            this.server.getServerThread().sendMessage("PONG");
        }
    }

    public void sendClientMessage(NetworkMessage message) {
        if(this.clientCreated) {
            this.client.sendMessage("PING");
        }
    }
}
