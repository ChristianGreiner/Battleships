package scenes;

import ai.AiDifficulty;
import core.Game;
import core.GameWindow;
import core.Updatable;
import game.*;
import network.NetworkListener;
import ui.GuiScene;
import ui.MultiplayerNetworkPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Class for the Multiplayer network scene.
 */
public class MultiplayerNetworkScene extends Scene implements Updatable, GuiScene, KeyListener, NetworkListener {

    private SavegameType networkGame;

    /**
     * Constructor for the multiplayer network scene.
     */
    public MultiplayerNetworkScene() {
        super("MultiplayerNetworkScene");
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public void onSwitched() {
        super.onSwitched();
        Game.getInstance().getNetworkManager().addNetworkListener(this);

        Game.getInstance().getNetworkManager().stopServer();
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void lateUpdate(double deltaTime) {

    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public JPanel buildGui(GameWindow gameWindow) {
        MultiplayerNetworkPanel multiplayerPanel = new MultiplayerNetworkPanel().create();

        multiplayerPanel.getJoinBtn().addActionListener((e) -> {
            Game.getInstance().getNetworkManager().joinServer(multiplayerPanel.getHostnameField().getText());
            this.networkGame = SavegameType.Multiplayer;
        });

        multiplayerPanel.getJoinAiBtn().addActionListener((e) -> {
            Game.getInstance().getNetworkManager().joinServer(multiplayerPanel.getHostnameField().getText());
            this.networkGame = SavegameType.MultiplayerAi;
        });

        multiplayerPanel.getHostBtn().addActionListener((e) -> {
            Game.getInstance().getSceneManager().setActiveScene(MultiplayerHostSettingsScene.class);
        });

        multiplayerPanel.getBackBtn().addActionListener((e) -> {
            Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
        });

        return multiplayerPanel;
    }

    @Override
    public void sizeUpdated() {
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Game.getInstance().getSoundManager().stopBackgroundMusic();
            Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }

    @Override
    public void OnPlayerConnected() {
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public void OnGameJoined(int mapSize) {
        ShipsSelectionScene scene = (ShipsSelectionScene) Game.getInstance().getSceneManager().setActiveScene(ShipsSelectionScene.class);
        scene.initializeGameSession(new GameSessionData(new Map(mapSize), mapSize, AiDifficulty.Medium, this.networkGame));
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

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public void OnReceiveLoad(String id) {

        try {
            MultiplayerSavegame savegame = (MultiplayerSavegame) Game.getInstance().getGameFileHandler().loadSavegame(id);
            MultiplayerScene scene = (MultiplayerScene) Game.getInstance().getSceneManager().setActiveScene(MultiplayerScene.class);
            scene.initializeSavegame(savegame);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(Game.getInstance().getWindow(), "This file seems to be corrupted or doesn't exist.", "Can't load savegame.", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public void OnGameClosed() {
        Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
    }
}