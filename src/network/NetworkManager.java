package network;

import core.Game;
import game.HitType;

import java.awt.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class NetworkManager implements NetworkListener{

    public ArrayList<NetworkListener> getListeners() {
        return listeners;
    }

    public final static int TCP_PORT = 50000;
    private ArrayList<NetworkListener> listeners = new ArrayList<>();
    private NetworkType networkType;
    private NetworkThread networkThread;

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
            System.out.println("[CLIENT] Client connected to Server!");
            this.networkThread = new NetworkThread(this, clientSocket);
            this.networkThread.start();

            Game.getInstance().getWindow().setTitle("CLIENT");

            this.networkType = NetworkType.Client;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startServer(int mapSize) {
        try {

            ServerSocket serverSocket = new ServerSocket(TCP_PORT);
            this.networkThread = new NetworkThread(this, serverSocket, mapSize);
            this.networkThread.start();

            Game.getInstance().getWindow().setTitle("SERVER");

            this.networkType = NetworkType.Host;


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void confirmSession() {
        this.networkThread.setConfirmed();
    }

    public void stopServer() {

    }

    @Override
    public void OnPlayerConnected() {
    }

    @Override
    public void OnGameJoined(int mapSize) {
    }

    @Override
    public void OnOpponentConfirmed() {
    }

    @Override
    public void OnGameStarted() {
    }

    @Override
    public void OnReceiveShot(Point pos) {
    }

    @Override
    public void OnReceiveAnswer(HitType type) {
    }
}
