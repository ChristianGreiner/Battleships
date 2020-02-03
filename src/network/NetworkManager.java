package network;

import core.Game;
import game.HitType;

import javax.swing.*;
import java.awt.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Manages the network.
 */
public class NetworkManager {

    public final static int TCP_PORT = 50000;
    private ArrayList<NetworkListener> listeners;
    private NetworkType networkType;
    private NetworkThread networkThread;

    public NetworkManager() {
        this.listeners = new ArrayList<>();
    }

    /**
     * Gets all listener of the network.
     *
     * @return The listeners.
     */
    public ArrayList<NetworkListener> getListeners() {
        return listeners;
    }

    /**
     * Gets the network type (client or host).
     *
     * @return The type.
     */
    public NetworkType getNetworkType() {
        return networkType;
    }

    /**
     * Adds a new network listener to the manager.
     *
     * @param listener The listener.
     */
    public void addNetworkListener(NetworkListener listener) {
        this.listeners.add(listener);
    }

    /**
     * Joins the a server.
     *
     * @param host The host ip address.
     */
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

    /**
     * Starts a new server.
     *
     * @param mapSize The map size of the game session.
     */
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

    /**
     * Starts a new game with a save game.
     *
     * @param id The id of the savegame.
     */
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

    /**
     * Confirms the current session.
     */
    public synchronized void confirmSession() {
        if (this.networkType == NetworkType.Host)
            this.networkThread.setServerConfirmed();
        else
            this.networkThread.setClientConfirmed();
    }

    /**
     * Sends a shot message to the opponent.
     *
     * @param location The shot position.
     */
    public synchronized void sendShot(Point location) {
        this.networkThread.addMessage("SHOT " + location.x + " " + location.y);
    }

    /**
     * Sends pass to the opponent.
     */
    public synchronized void sendPass() {
        this.networkThread.addMessage("PASS");
    }

    /**
     * Sends a answer whenever or not the opponent hit the player.
     *
     * @param hitType The hit type.
     */
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

    /**
     * Sends a save message to the opponent.
     *
     * @param id The id of the savegame.
     */
    public synchronized void sendSave(long id) {
        this.networkThread.addMessage("SAVE " + id);
    }

    /**
     * Stops the server.
     */
    public void stopServer() {
        if (this.networkThread != null) {
            this.networkThread.stopNetwork();
            this.networkThread.interrupt();
        }
    }
}
