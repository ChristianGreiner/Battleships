package network;

import java.io.*;
import java.net.Socket;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingDeque;

public class ServerThread extends Thread{

    public static int counter = 0;

    private Queue<String> commands;
    private Socket clientSocket = null;
    private Scanner input;
    private PrintWriter output;

    public ServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.commands = new LinkedBlockingDeque<>(99);

        this.counter++;
        System.out.println("SERVER THREAD CREATED " + counter);
    }

    public void sendCommand(String command) {
        this.commands.add(command);
    }

    @Override
    public void run() {

        try {
            this.input = new  Scanner(this.clientSocket.getInputStream());
            this.output = new PrintWriter(this.clientSocket.getOutputStream(), true);

            // waiting for messages
           while (true) {
               //System.out.println(counter + ": SERVER loop");
           }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try { this.clientSocket.close(); } catch (IOException e) {}
        }
    }
}