package core;

import network.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class NetworkManager implements NetworkListener{

    public ArrayList<NetworkListener> getListeners() {
        return listeners;
    }

    public final static int TCP_PORT = 50000;
    public final static int UDP_PORT = 50001;
    private ArrayList<NetworkListener> listeners = new ArrayList<>();
    private NetworkType networkType;
    private boolean serverConfirmed, clientConfirmed;
    private ServerThread serverThread;

    public ClientThread getClientThread() {
        return clientThread;
    }

    public void setClientThread(ClientThread clientThread) {
        this.clientThread = clientThread;
    }

    private ClientThread clientThread;
    private int mapSize;

    public boolean isServerConfirmed() {
        return serverConfirmed;
    }

    public boolean isClientConfirmed() {
        return clientConfirmed;
    }

    public NetworkType getNetworkType() {
        return networkType;
    }

    public NetworkManager() {
        this.listeners.add(this);
    }

    public void addNetworkListener(NetworkListener listener) {
        this.listeners.add(listener);
    }

    public void joinServer(String host) {
        try {

            // start socket
            Socket clientSocket = new Socket("localhost", TCP_PORT);
            System.out.println("Verbunden!");
            this.clientThread = new ClientThread(clientSocket);
            this.clientThread.start();

            this.networkType = NetworkType.Client;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startServer(int mapSize) {
        try {

            // start socket
            ServerSocket ss = new ServerSocket(TCP_PORT);
            this.serverThread = new ServerThread(ss);
            this.serverThread.start();

            this.mapSize = mapSize;

            this.networkType = NetworkType.Host;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        if(this.serverThread != null) {
            this.serverThread = null;
        }
    }

    public void sendMessageToServer(NetworkMessage message) {

    }

    public void sendConfirmMessage() {
        if(this.networkType == NetworkType.Client)
            this.clientThread.setReadyToPlay(true);
        else
            this.serverThread.setReadyToPlay(true);
    }

    @Override
    public synchronized void OnPlayerConnected() {
       this.serverThread.queueMessage("SIZE " + this.mapSize);
    }

    @Override
    public void OnGameJoined(int mapSize) {
    }

    @Override
    public void OnOpponentConfirmed() {
    }

    @Override
    public void OnServerStarted() {
    }
}
