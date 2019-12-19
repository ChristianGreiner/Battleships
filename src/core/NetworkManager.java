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
        this.client.run();
    }

    public void startSession(int port) {
        this.server = new Server(port);
        this.server.run();
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void lateUpdate(double deltaTime) {

    }
}
