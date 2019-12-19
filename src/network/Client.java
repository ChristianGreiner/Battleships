package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client{
    private Socket server;
    private String hostname;
    private int port;

    public Client(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void run() {
        try {
            this.server = new Socket(this.hostname, this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pool() {

        if(this.server == null)
            return;

        System.out.println("POOLING CLIENT");
        try {
          if(!this.server.isClosed()) {
              InputStream is = null;
              is = this.server.getInputStream();
              InputStreamReader isr = new InputStreamReader(is);
              BufferedReader br = new BufferedReader(isr);
              String message =  br.readLine();
              System.out.println("Message received from the server : " + message);
          }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
