package network;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server implements Runnable{
    private int port;
    private ServerSocket serverSocket;
    private ExecutorService serverPool;

    public ServerThread getServerThread() {
        return serverThread;
    }

    private ServerThread serverThread;

    public Server(int port) {
        this.port = port;

        // only accept one thread
        this.serverPool = Executors.newFixedThreadPool(1);
    }

    @Override
    public void run() {
        try {
            this.serverSocket = new ServerSocket(this.port, 1);

            InetAddress adresse = InetAddress.getLocalHost();
            System.out.println("Server gestartet \n" + "Server auf IP:  " +  adresse.getHostAddress() + adresse.getHostName());

            // waiting for connections
            while (true){
                this.serverPool.execute(new ServerThread(serverSocket.accept()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
