package network;

public interface NetworkListener {

    void OnPlayerConnected();

    void OnGameJoined(int mapSize);

    void OnServerStarted();
}
