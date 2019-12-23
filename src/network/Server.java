package network;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{
    protected int port;
    protected boolean isConnected = false;
    private ServerSocket serverSocket;

    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            this.serverSocket = new ServerSocket(this.port);

            InetAddress adresse = InetAddress.getLocalHost();
            System.out.println("Server gestartet \n" + "Server auf IP:  " +  adresse.getHostAddress() + adresse.getHostName());

            while (!isConnected){
                Socket client = serverSocket.accept();
                new ServerThread(client).start();
                Thread.sleep(100);
            }
        } catch (Exception e) {

        }
    }
}
