package network;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private Socket socket;
    private String ip;
    private int port;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void run() throws IOException {
        this.socket = new Socket(this.ip, this.port);
        System.out.println("Client successfully connected to server!");
    }
}
