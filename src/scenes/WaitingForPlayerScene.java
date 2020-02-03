package scenes;

import core.Game;
import core.GameWindow;
import game.*;
import network.NetworkListener;
import ui.GuiScene;
import ui.WaitingForPlayerPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Class for the waiting for player scene.
 */
public class WaitingForPlayerScene extends Scene implements GuiScene, NetworkListener, GameSession {

    private GameSessionData sessionData;
    private Savegame savegame;

    /**
     * Constructor for the scene.
     */
    public WaitingForPlayerScene() {
        super("WaitingForPlayerScene");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSwitched() {
        super.onSwitched();
        Game.getInstance().getNetworkManager().addNetworkListener(this);
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * Event handler for when a player connects.
     */
    @Override
    public void OnPlayerConnected() {
        if (this.savegame == null) {
            ShipsSelectionScene scene = (ShipsSelectionScene) Game.getInstance().getSceneManager().setActiveScene(ShipsSelectionScene.class);
            scene.initializeGameSession(this.sessionData);
        } else {
            if (this.sessionData.getSavegame().getSavegameType() == SavegameType.Multiplayer) {
                MultiplayerScene scene = (MultiplayerScene) Game.getInstance().getSceneManager().setActiveScene(MultiplayerScene.class);
                scene.initializeSavegame((MultiplayerSavegame) this.sessionData.getSavegame());
            } else if (this.sessionData.getSavegame().getSavegameType() == SavegameType.MultiplayerAi) {
                MultiplayerAIScene scene = (MultiplayerAIScene) Game.getInstance().getSceneManager().setActiveScene(MultiplayerAIScene.class);
                scene.initializeSavegame((MultiplayerAiSavegame) this.sessionData.getSavegame());
            }
        }
    }

    @Override
    public void OnGameJoined(int mapSize) {
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

    @Override
    public void OnReceiveSave(String id) {
    }

    @Override
    public void OnReceiveLoad(String id) {
    }

    @Override
    public void OnGameClosed() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeGameSession(GameSessionData data) {
        this.sessionData = data;
        this.savegame = data.getSavegame();

        if (data.getSavegame() != null) {
            Game.getInstance().getNetworkManager().startServer(data.getSavegame().getId());
            Game.getInstance().getNetworkManager().confirmSession();
        } else {
            Game.getInstance().getNetworkManager().startServer(data.getMapSize());
        }
    }
}
