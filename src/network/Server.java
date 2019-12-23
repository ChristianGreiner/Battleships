package network;

import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    public ServerThread getServerThread() {
        return serverThread;
    }

    private ServerThread serverThread;

    @Override
    public void run() {
        super.run();

        try {
            ServerSocket server = new ServerSocket(5555);
            int counter = 0;
            System.out.println("Server Started ....");
            while (true) {
                counter++;
                Socket serverClient = server.accept();  //server accept the client connection request
                System.out.println(" >> " + "Client No:" + counter + " started!");
                this.serverThread = new ServerThread(serverClient, counter); //send  the request to a separate thread
                this.serverThread.start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
