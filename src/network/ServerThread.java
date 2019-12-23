package network;

import java.io.*;
import java.net.Socket;

/**

 */
public class ServerThread extends Thread{

    protected Socket clientSocket = null;
    protected String serverText   = null;

    public ServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.serverText   = serverText;
    }

    @Override
    public void run() {
        try {
            DataInputStream input = new DataInputStream(this.clientSocket.getInputStream());
            DataOutputStream output = new DataOutputStream(this.clientSocket.getOutputStream());

            String in = input.readUTF();
            System.out.println("[SERVER]: " + in);

            output.writeUTF("Hey from Server!");


            output.close();
            input.close();
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
}