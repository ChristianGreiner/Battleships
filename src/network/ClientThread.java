package network;

import core.Game;
import jsock.net.MessageSocket;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

public class ClientThread extends Thread {

    private Socket client;
    private MessageSocket messageSocket;
    private ArrayBlockingQueue<String> messages = new ArrayBlockingQueue<String>(1024);

    public void setReadyToPlay(boolean readyToPlay) {
        this.readyToPlay = readyToPlay;
    }

    private boolean readyToPlay = false;

    public ClientThread (Socket client) {
        this.client = client;
        try {
            this.messageSocket = new MessageSocket(this.client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void queueMessage(String message) {
        this.messages.add(message);
    }

    @Override
    public void run() {
        super.run();

       try {

           System.out.println("CLIENT: WARTEN AUF MAP SIZE");

           // 1. WAITING FOR MAP SIZE
           while(true) {
               String receivedMessage = this.messageSocket.recv_msg();								//liest socket aus
               if (receivedMessage == null) continue;							//Message leer? neuer versuch!
               if(receivedMessage.contains("SIZE")) {
                   String[] size = receivedMessage.split(" ");
                   if(size.length > 0) {
                       if(size[1] != null) {
                           if(!size[1].isEmpty()) {
                               int mapSize = Integer.parseInt(size[1]);
                               for (NetworkListener listener : Game.getInstance().getNetworkManager().getListeners()) {
                                   listener.OnGameJoined(mapSize);
                               }
                           }
                       }
                   }
               }
               break;
           }

           boolean serverConfirmed = false;
           boolean playerConfirmed = false;


           while (true) {
               System.out.println("LOL");
               String confirmMessage = this.messageSocket.recvSanitizedMsg();								//liest socket aus
               if (confirmMessage != null) {                            //Message leer? neuer versuch!
                   if (confirmMessage.contains("CONFIRMED")) {
                       System.out.println("SERVER CONFIRMED");
                       serverConfirmed = true;
                       break;
                   }
               }
           }

           // 2. WAIT FOR CONFIRM
           while(!(playerConfirmed && serverConfirmed)) {


               while (!readyToPlay) {
                   this.messageSocket.send_msg("CONFIRMED");
                   playerConfirmed = false;
               }
           }

           String message;
           while (true) {													//Programm loop

               while(true) {												//Idle Loop .. warten auf User Input
                   System.out.print("Server: ");							//Fürs schön aussehen
                   String sendMessage = "PONG";								//Commandline input auslesen
                   if (sendMessage == null || sendMessage.equals("")) continue;	//Message leer? neuer versuch!
                   this.messageSocket.send_msg(sendMessage);				//Wenn hier gelandet -> Nicht leer: User Message per Socket ausgeben
                   break;													// --> Loop beenden da nachricht erfolgreich verschickt
               }

               while(true) {												//Idle Loop .. wartet auf Nachricht
                   String receivedMessage = this.messageSocket.recv_msg();								//liest socket aus
                   if (receivedMessage == null) continue;							//Message leer? neuer versuch!
                   System.out.println("Client: " + receivedMessage);				//Wenn hier  gelandet -> Nicht leer: Erhaltene Message ausgeben
                   break;													// --> Loop beenden da nachricht bekommen
               }
           }
       } catch (Exception ex) {

       }
    }
}
