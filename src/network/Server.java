package network;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void run() {
        try {
            this.serverSocket = new ServerSocket(this.port);
            InetAddress adresse = InetAddress.getLocalHost();
            System.out.println("Server gestartet \n" +
                    "Server auf IP:  " +  adresse.getHostAddress() + adresse.getHostName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pool() {
        System.out.println("LOL");
        try {
            System.out.println("POOLING SERVER");
            Socket client = this.serverSocket.accept();

            String clientAdresse;
            clientAdresse = client.getInetAddress().getHostAddress() + client.getInetAddress().getHostName();
            System.out.println(clientAdresse);

            InputStream is = client.getInputStream();

            OutputStream os = client.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write("Hello World");
            System.out.println("SERVER: Message sent to the client is "+ "HELLO WORLD");
            bw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
