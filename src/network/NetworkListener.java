package network;

import game.HitType;

import java.awt.*;

public interface NetworkListener {

    void OnPlayerConnected();

    void OnGameJoined(int mapSize);

    void OnGameStarted();

    void OnReceiveShot(Point pos);

    void OnReceiveAnswer(HitType type);

    void OnReceiveSave(String id);

    void OnReceiveLoad(String id);
}
