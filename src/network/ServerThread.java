package network;

import core.Game;
import jsock.net.MessageSocket;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

public class ServerThread extends Thread {

    private ServerSocket server;
    private MessageSocket messageSocket;
    private ArrayBlockingQueue<String> messages;

    public void setReadyToPlay(boolean readyToPlay) {
        this.readyToPlay = readyToPlay;
    }

    private boolean readyToPlay = false;

    public ServerThread (ServerSocket server) {
        this.server = server;
        this.messages = new ArrayBlockingQueue<String>(1024);
    }

    public synchronized void queueMessage(String message) {
        this.messages.add(message);
    }

    @Override
    public void run() {
        super.run();

        try {
            System.out.println("Warte auf Client");
            Socket s = this.server.accept();											//Wartet das auf den Socket connected wird

            this.messageSocket = new MessageSocket(s);
            System.out.println("Verbunden!");

            for (NetworkListener listener : Game.getInstance().getNetworkManager().getListeners()) {
                listener.OnPlayerConnected();
            }

            // 1. WAIT FOR MAP SIZE
            while (true) {
                String mapSizeMessage = this.messages.remove();
                if (mapSizeMessage != null) {
                    // send size message
                    if(mapSizeMessage.contains("SIZE")) {
                        this.messageSocket.send_msg(mapSizeMessage);
                    }
                    this.messages.clear();
                    break;
                }
            }

            boolean serverConfirmed = false;
            boolean playerConfirmed = false;

            // 2. WAIT FOR CONFIRM
            while(!(playerConfirmed && serverConfirmed)) {
                String confirmMessage = this.messageSocket.recv_msg();								//liest socket aus
                if (confirmMessage != null) {                            //Message leer? neuer versuch!
                    if (confirmMessage.contains("CONFIRMED")) {
                        for (NetworkListener listener : Game.getInstance().getNetworkManager().getListeners()) {
                            listener.OnOpponentConfirmed();
                        }
                        playerConfirmed = true;
                    }
                }

                while (!readyToPlay) {
                    this.messageSocket.send_msg("CONFIRMED");
                    serverConfirmed = false;
                }
            }

            while (true) {

                while(true) {												//Idle Loop .. wartet auf Nachricht
                    String receivedMessage = this.messageSocket.recv_msg();								//liest socket aus
                    if (receivedMessage == null) continue;							//Message leer? neuer versuch!
                    System.out.println("Client: " + receivedMessage);				//Wenn hier  gelandet -> Nicht leer: Erhaltene Message ausgeben
                    break;													// --> Loop beenden da nachricht bekommen
                }

                while(true) {												//Idle Loop .. warten auf User Input
                    System.out.print("Server: ");							//Fürs schön aussehen
                    String sendMessage = "PONG";								//Commandline input auslesen
                    if (sendMessage == null || sendMessage.equals("")) continue;	//Message leer? neuer versuch!
                    this.messageSocket.send_msg(sendMessage);				//Wenn hier gelandet -> Nicht leer: User Message per Socket ausgeben
                    break;													// --> Loop beenden da nachricht erfolgreich verschickt
                }

            }
        } catch (Exception ex) {
        }
    }
}
