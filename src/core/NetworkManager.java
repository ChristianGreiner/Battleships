package core;

import network.Client;
import network.NetworkListener;
import network.Server;

import java.util.ArrayList;

public class NetworkManager implements Updatable{

    private ArrayList<NetworkListener> listeners = new ArrayList<>();
    private Client client;
    private Server server;

    public void addNetworkListener(NetworkListener listener) {
        this.listeners.add(listener);
    }

    public void joinSession(String ip, int port) {
        this.client = new Client(ip, port);
        Thread client = new Thread(this.client);
        client.start();
    }

    public void startSession(int port) {
        this.server = new Server(port);
        Thread server = new Thread(this.server);
        server.start();
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void lateUpdate(double deltaTime) {
    }
}
