package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThread implements Runnable{

    private Socket socket;
    private int port;
    private String hostname;

    public ClientThread(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    @Override
    public void run() {
        try
        {
            this.socket = null;

            while (true)
            {
                this.socket = new Socket(this.hostname, this.port);

                DataInputStream input = new DataInputStream(this.socket.getInputStream());
                DataOutputStream output = new DataOutputStream(this.socket.getOutputStream());

                output.writeUTF(" Hey from Client!");
                System.out.println("[CLIENT] " + input.readUTF());

                Thread.sleep(100);
            }

        }catch(Exception e){
            try {
                this.socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
