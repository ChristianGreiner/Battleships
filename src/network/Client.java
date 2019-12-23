package network;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client extends Thread{
    private Socket server;
    private String hostname;
    private int port;
    private ExecutorService clientPool;

    public ClientThread getClientThread() {
        return clientThread;
    }

    private ClientThread clientThread;

    public Client(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        this.clientPool = Executors.newFixedThreadPool(1);
    }

    public void run() {
        try {
            this.server = new Socket(this.hostname, this.port);
            this.clientPool.execute(new ClientThread(this.hostname, this.port));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pool() {

    }
}
