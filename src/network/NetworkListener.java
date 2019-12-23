package network;

import game.HitType;

public interface NetworkListener {
    void OnGameJoined(int mapSize, int[] ships);

    void OnHitReceived(HitType type);

    void OnGameSaved(int id);

    void OnGameLoaded(int id);
}
