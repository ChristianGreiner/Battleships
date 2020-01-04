package network;

import core.NetworkManager;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Client extends Thread {

    private BlockingQueue<String> commands = new ArrayBlockingQueue<String>(1024);
    private Socket socket;
    private NetworkManager manager;

    public Client(NetworkManager manager, String host, int port) {
        this.manager = manager;
        try {
            this.socket = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        this.commands.add(message);
    }

    @Override
    public void run() {
        super.run();

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));	//Socket Input
            Writer out = new OutputStreamWriter(socket.getOutputStream());

            String message = null;

            while (true) {													//Programm loop

                while(true) {												//Idle Loop .. warten auf User Input
                    String command = this.commands.take();
                    if(command != null)
                        message = command;

                    if (message == null || message.equals("")) continue;	//Message leer? neuer versuch!
                    out.write(String.format("%s%n", message));				//Wenn hier gelandet -> Nicht leer: User Message per Socket ausgeben
                    out.flush();											//flushen damit alles ausgegeben wird
                    break;													// --> Loop beenden da nachricht erfolgreich verschickt
                }

                while(true) {												//Idle Loop .. wartet auf Nachricht
                    message = in.readLine();								//liest socket aus
                    if (message == null) continue;							//Message leer? neuer versuch!
                    System.out.println("[CLIENT]: " + message);				//Wenn hier  gelandet -> Nicht leer: Erhaltene Message ausgeben
                    break;													// --> Loop beenden da nachricht bekommen
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
