package network;


import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkThread extends Thread {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private NetworkType networkType;
    private NetworkManager networkManager;
    private int mapSize;
    private volatile boolean confirmed = false;
    private volatile boolean opponentConfirmed = false;

    public synchronized boolean isOpponentConfirmed() {
        return opponentConfirmed;
    }

    public synchronized boolean isConfirmed() {
        return confirmed;
    }

    public synchronized void setConfirmed() {
        this.confirmed = true;
    }

    public NetworkThread(NetworkManager networkManager, ServerSocket serverSocket, int mapSize) {
        this.networkManager = networkManager;
        this.serverSocket = serverSocket;
        this.networkType = NetworkType.Host;
        this.mapSize = mapSize;
    }

    public NetworkThread(NetworkManager networkManager, Socket socket) {
        this.networkManager = networkManager;
        this.clientSocket = socket;
        this.networkType = NetworkType.Client;
    }

    private Point parseShotMessage(String message) {
        String[] size = message.split(" ");
        if(size.length > 0) {
            if (size[1] != null && size[2] != null) {
                if (!size[1].isEmpty() && !size[2].isEmpty()) {
                    int x = Integer.parseInt(size[1]);
                    int y = Integer.parseInt(size[2]);
                    return new Point(x, y);
                }
            }
        }
        return null;
    }

    private int parseSizeMessage(String message) {
        String[] size = message.split(" ");
        if(size.length > 0) {
            if(size[1] != null) {
                if(!size[1].isEmpty()) {
                    return Integer.parseInt(size[1]);
                }
            }
        }
        return 0;
    }

    @Override
    public void run() {
        super.run();

        try {
            while (true) {
                BufferedReader in = null;
                Writer out =  null;

                if(this.networkType == NetworkType.Host) {

                    // Wait for client connects to the game session
                    Socket socket = this.serverSocket.accept();

                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out = new OutputStreamWriter(socket.getOutputStream());

                    for (NetworkListener listener : this.networkManager.getListeners()) {
                        listener.OnPlayerConnected();
                    }

                    // 1. Sends the map size
                    System.out.println("[SERVER] Sending Map Size");
                    out.write(String.format("%s%n", "SIZE " + mapSize));
                    out.flush();

                    // wait till client sends confirmed
                    while (true) {
                        String message = in.readLine();
                        if (message == null) continue;
                        if(message.contains("CONFIRMED")) {
                            System.out.println("[Server] Client CONFIRMED");
                        }
                        break;
                    }

                    // sends confirmed when server is ready
                    while (true) {
                        if(!this.confirmed) continue;
                        System.out.println("[SERVER] Sending confirmed");
                        out.write(String.format("%s%n", "CONFIRMED"));
                        out.flush();

                        for (NetworkListener listener : this.networkManager.getListeners()) {
                            listener.OnGameStarted();
                        }
                        break;
                    }

                } else {
                    in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
                    out = new OutputStreamWriter(this.clientSocket.getOutputStream());

                    // Waiting for getting the map size
                    while(true) {
                        String message = in.readLine();
                        if (message == null) continue;
                        if(message.contains("SIZE")) {
                            int mapSize = parseSizeMessage(message);
                            if(mapSize >= 5 && mapSize <= 30) {
                                for (NetworkListener listener : this.networkManager.getListeners()) {
                                    listener.OnGameJoined(mapSize);
                                }
                            }
                        }
                        break;
                    }

                    // waiting for sending the confirmed message
                    while (true) {
                        if(!this.confirmed) continue;
                        System.out.println("[CLIENT] Sending confirmed");
                        out.write(String.format("%s%n", "CONFIRMED"));
                        out.flush();
                        break;
                    }

                    //  waiting for getting the confirmed message by the server
                    while (true) {
                        String message = in.readLine();
                        if (message == null) continue;
                        if(message.contains("CONFIRMED")) {
                            System.out.println("Server confirmed");

                            for (NetworkListener listener : this.networkManager.getListeners()) {
                                listener.OnGameStarted();
                            }
                        }
                        break;
                    }
                }

                // do ping pong stuff
                if(this.networkType == NetworkType.Host) {
                    while (true) {
                        String message = in.readLine();
                        if (message == null) continue;
                        System.out.println(message);
                        break;
                    }
                }

                while (true) {
                    while (true) {
                        String message = "PONG";
                        if (message == null) continue;
                        out.write(String.format("%s%n", message));
                        out.flush();
                        break;
                    }

                    while (true) {
                        String message = in.readLine();
                        if (message == null) continue;
                        System.out.println(message);
                        break;
                    }
                }

            }
        } catch (Exception ex)  {
            ex.printStackTrace();
        }
    }
}
