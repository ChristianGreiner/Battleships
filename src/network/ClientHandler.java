package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private DataInputStream dis;
    private DataOutputStream dos;
    private Socket socket;

    public ClientHandler(Socket socket, DataInputStream dis, DataOutputStream dos) {
        this.socket = socket;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {
        String received;
        while (true)
        {
            try
            {
                // receive the string
                received = dis.readUTF();

                System.out.println(received);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
}
