package network;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ServerThread extends Thread {
    private Socket serverClient;
    private boolean running = true;

    private BlockingQueue<String> commands = new ArrayBlockingQueue<String>(1024);

    ServerThread(Socket inSocket, int counter) {
        serverClient = inSocket;
    }

    public void sendMessage(String message) {
        this.commands.add(message);
    }

    public void stopThread() {
        this.running = false;
    }

    public void run() {
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(serverClient.getInputStream()));	//Socket input
            Writer out = new OutputStreamWriter(serverClient.getOutputStream());

            String message = null;

            while (running) {													//Programm loop

                while(true) {												//Idle Loop .. wartet auf Nachricht
                    message = in.readLine();								//liest socket aus
                    if (message == null) continue;							//Message leer? neuer versuch!
                    System.out.println("[SERVER]: " + message);				//Wenn hier  gelandet -> Nicht leer: Erhaltene Message ausgeben
                    break;													// --> Loop beenden da nachricht bekommen
                }

                while(true) {												//Idle Loop .. warten auf User Input
                    String command = this.commands.take();
                    if(command != null)
                        message = command;

                    if (message == null || message.equals("")) continue;	//Message leer? neuer versuch!
                    out.write(String.format("%s%n", message));				//Wenn hier gelandet -> Nicht leer: User Message per Socket ausgeben
                    out.flush();											//flushen damit alles ausgegeben wird
                    break;													// --> Loop beenden da nachricht erfolgreich verschickt
                }

            }

        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
        }
    }
}

