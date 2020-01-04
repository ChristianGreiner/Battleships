package network;

import core.NetworkManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    public ServerThread getServerThread() {
        return serverThread;
    }

    private ServerThread serverThread;
    private NetworkManager manager;
    private boolean running = true;
    private ServerSocket serverSocket;

    public Server(NetworkManager manager) {
        this.manager = manager;
    }

    public void stopThread() {
        this.running = false;

        if(this.serverSocket != null) {
            try {
                this.serverSocket.close();
            } catch (IOException e) {
            }
        }

        if(this.serverThread != null)
            this.serverThread.stopThread();
    }

    @Override
    public void run() {
        super.run();

        try {
            this.serverSocket = new ServerSocket(5555);
            int counter = 0;
            System.out.println("Server Started ....");
            while (running) {
                counter++;
                Socket serverClient = serverSocket.accept();  //server accept the client connection request
                System.out.println(" >> " + "Client No:" + counter + " started!");

                // trigger event
                for (NetworkListener listener : this.manager.getListeners()) {
                    listener.OnPlayerConnected();
                }

                this.serverThread = new ServerThread(serverClient, counter); //send  the request to a separate thread
                this.serverThread.start();
            }
        } catch (Exception e) {
            this.running = false;
        }
    }
}
