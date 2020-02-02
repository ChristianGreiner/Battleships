package network;

import core.Game;
import game.HitType;

import javax.swing.*;
import java.awt.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class NetworkManager {

    public final static int TCP_PORT = 50000;
    private ArrayList<NetworkListener> listeners;
    private NetworkType networkType;
    private NetworkThread networkThread;

    public NetworkManager() {
        this.listeners = new ArrayList<>();
    }

    public ArrayList<NetworkListener> getListeners() {
        return listeners;
    }

    public NetworkType getNetworkType() {
        return networkType;
    }

    public void addNetworkListener(NetworkListener listener) {
        this.listeners.add(listener);
    }

    public void joinServer(String host) {
        try {

            this.networkThread = null;

            // start socket
            Socket clientSocket = new Socket(host, TCP_PORT);
            System.out.println("[CLIENT] Client connected to Server!");
            this.networkThread = new NetworkThread(this, clientSocket);
            this.networkThread.start();

            Game.getInstance().getWindow().setTitle("CLIENT");

            this.networkType = NetworkType.Client;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(Game.getInstance().getWindow(), "Can`t connect to server " + host, "Connection refused", JOptionPane.ERROR_MESSAGE);
            System.out.println("NO CONNECTION");
        }
    }

    public void startServer(int mapSize) {
        try {

            this.networkThread = null;

            ServerSocket serverSocket = new ServerSocket(TCP_PORT);
            this.networkThread = new NetworkThread(this, serverSocket, mapSize);
            this.networkThread.start();

            Game.getInstance().getWindow().setTitle("SERVER");

            this.networkType = NetworkType.Host;


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startServer(String id) {
        try {

            ServerSocket serverSocket = new ServerSocket(TCP_PORT);
            this.networkThread = new NetworkThread(this, serverSocket, id);
            this.networkThread.start();

            Game.getInstance().getWindow().setTitle("SERVER");

            this.networkType = NetworkType.Host;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void confirmSession() {
        if (this.networkType == NetworkType.Host)
            this.networkThread.setServerConfirmed();
        else
            this.networkThread.setClientConfirmed();
    }

    public synchronized void sendShot(Point location) {
        this.networkThread.addMessage("SHOT " + location.x + " " + location.y);
    }

    public synchronized void sendPass() {
        this.networkThread.addMessage("PASS");
    }

    public synchronized void sendAnswer(HitType hitType) {

        int value = 0;
        if (hitType == HitType.Water)
            value = 0;
        else if (hitType == HitType.Ship)
            value = 1;
        else if (hitType == HitType.ShipDestroyed)
            value = 2;

        this.networkThread.addMessage("ANSWER " + value);
    }

    public synchronized void sendSave(long id) {
        this.networkThread.addMessage("SAVE " + id);
    }

    public void stopServer() {
        if (this.networkThread != null) {
            this.networkThread.stopNetwork();
            this.networkThread.interrupt();
        }
    }
}
