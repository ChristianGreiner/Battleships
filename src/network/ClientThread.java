package network;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingDeque;

public class ClientThread implements Runnable{

    public static int counter = 0;

    private int port;
    private String hostname;

    private Queue<String> commands;
    private Socket socket;
    private Scanner input;
    private PrintWriter output;

    public ClientThread(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        this.commands = new LinkedBlockingDeque<>(99);
        this.counter++;
        System.out.println("CLIENT THREAD CREATED " + counter);
    }

    public void sendCommand(String commands) {
        this.commands.add(commands);
    }

    @Override
    public void run() {
        try {
            this.socket = new Socket(this.hostname, this.port);
            this.input = new Scanner(this.socket.getInputStream());
            this.output = new PrintWriter(this.socket.getOutputStream(), true);

            while (true) {
                //System.out.println(counter + ": CLIENT LOOP");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
