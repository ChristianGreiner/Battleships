package network;

import game.HitType;

import java.awt.*;

public interface NetworkListener {

    /**
     * Gets called when the client connected.
     */
    void OnPlayerConnected();

    /**
     * Gets called when the player joined.
     *
     * @param mapSize The map size.
     */
    void OnGameJoined(int mapSize);

    /**
     * Gets called when the game started. Client and Host confirms the session.
     */
    void OnGameStarted();

    /**
     * Gets called whenever the client or the host receives a shot.
     *
     * @param pos The shot position.
     */
    void OnReceiveShot(Point pos);

    /**
     * Gets called whenever the client or the host receives an answer.
     * @param type The hit type.
     */
    void OnReceiveAnswer(HitType type);

    /**
     * Gets called whenever the client or the host receives a save.
     * @param id The id of the savegame.
     */
    void OnReceiveSave(String id);

    /**
     * Gets called whenever the client or the host receives a load.
     * @param id The id of the save game.
     */
    void OnReceiveLoad(String id);

    /**
     * Gets called when the game was closed by the server.
     */
    void OnGameClosed();
}
