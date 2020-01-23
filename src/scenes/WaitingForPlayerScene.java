package scenes;

import core.Game;
import core.GameWindow;
import game.GameSession;
import game.GameSessionData;
import network.NetworkListener;
import network.NetworkMessage;
import ui.GuiScene;
import ui.WaitingForPlayerPanel;

import javax.swing.*;

public class WaitingForPlayerScene extends Scene implements GuiScene, NetworkListener, GameSession {

    public WaitingForPlayerScene() {
        super("WaitingForPlayerScene");
    }

    private GameSessionData sessionData;

    @Override
    public void onAdded() {
        super.onAdded();

        Game.getInstance().getNetworkManager().addNetworkListener(this);
    }

    @Override
    public JPanel buildGui(GameWindow gameWindow) {

        WaitingForPlayerPanel waitingPanel = new WaitingForPlayerPanel().create();

        waitingPanel.getBackBtn().addActionListener((e) -> {
            Game.getInstance().getNetworkManager().stopServer();
            Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
        });

        return waitingPanel;
    }

    @Override
    public void sizeUpdated() {

    }

    @Override
    public void OnPlayerConnected() {
        ShipsSelectionScene scene = (ShipsSelectionScene) Game.getInstance().getSceneManager().setActiveScene(ShipsSelectionScene.class);
        scene.setNetworkGame(true);
        scene.initializeGameSession(this.sessionData);
        NetworkMessage message = new NetworkMessage();
        message.text = "size " + sessionData.getMapSize();
    }

    @Override
    public void OnGameJoined(int mapSize) {
        // nothing to do
    }

    @Override
    public void OnOpponentConfirmed() {
    }

    @Override
    public void OnServerStarted() {
        // nothing to do
    }

    @Override
    public void initializeGameSession(GameSessionData data) {
        this.sessionData = data;

        Game.getInstance().getNetworkManager().startServer(data.getMapSize());
    }
}
