package core;

import network.Client;
import network.Server;

import java.io.IOException;

public class NetworkManager {

    private Client client;
    private Server server;

    public void joinSession(String ip, int port) {
        this.client = new Client(ip, port);
        try {
            this.client.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startSession(int port) {
        this.server = new Server(port);
        try {
            this.server.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
