package scenes;

import core.Game;
import core.GameWindow;
import game.HitType;
import network.NetworkListener;
import ui.GuiScene;
import ui.WaitingForPlayerPanel;

import javax.swing.*;

public class WaitingForPlayerScene extends Scene implements GuiScene, NetworkListener {

    public WaitingForPlayerScene() {
        super("WaitingForPlayerScene");
    }

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
        Game.getInstance().getSceneManager().setActiveScene(MultiplayerScene.class);
    }

    @Override
    public void OnGameJoined(int mapSize, int[] ships) {

    }

    @Override
    public void OnHitReceived(HitType type) {

    }

    @Override
    public void OnGameSaved(int id) {

    }

    @Override
    public void OnGameLoaded(int id) {

    }
}
