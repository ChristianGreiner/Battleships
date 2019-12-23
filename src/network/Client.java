package network;

import java.io.*;
import java.net.Socket;

public class Client extends Thread{
    private Socket server;
    private String hostname;
    private int port;
    private ClientThread clientThread;

    public Client(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void run() {
        try {
            this.server = new Socket(this.hostname, this.port);

            // create a new thread object
            Thread t = new Thread(new ClientThread(this.hostname, this.port));

            // Invoking the start() method
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pool() {

    }
}
