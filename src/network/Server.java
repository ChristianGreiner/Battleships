package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket socket;
    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void run() throws IOException {
        socket = new ServerSocket(this.port) {
            protected void finalize() throws IOException {
                this.close();
            }
        };
        System.out.println("Server is running");

        while (true) {
            // accepts a new client
            Socket client = socket.accept();

            System.out.println("New Client: \"\n\t     Host:" + client.getInetAddress().getHostAddress());

            //read from socket to ObjectInputStream object
            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            //convert ObjectInputStream object to String
            String message = null;
            try {
                message = (String) ois.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("Message Received: " + message);
            //create ObjectOutputStream object
            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
            //write object to Socket
            oos.writeObject("Hi Client "+message);
            //close resources
            ois.close();
            oos.close();
            socket.close();
        }
    }
}
