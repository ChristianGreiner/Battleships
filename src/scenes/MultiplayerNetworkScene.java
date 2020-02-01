package scenes;

import ai.AiDifficulty;
import core.Game;
import core.GameWindow;
import core.Updatable;
import game.GameSessionData;
import game.HitType;
import game.Map;
import game.Savegame;
import network.NetworkListener;
import ui.GuiScene;
import ui.MultiplayerNetworkPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MultiplayerNetworkScene extends Scene implements Updatable, GuiScene, KeyListener, NetworkListener {

    private boolean aiGame = false;

    public MultiplayerNetworkScene() {
        super("MultiplayerNetworkScene");
    }

    @Override
    public void onSwitched() {
        super.onSwitched();
        Game.getInstance().getNetworkManager().addNetworkListener(this);
        aiGame = false;
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void lateUpdate(double deltaTime) {

    }

    @Override
    public JPanel buildGui(GameWindow gameWindow) {
        MultiplayerNetworkPanel multiplayerPanel = new MultiplayerNetworkPanel().create();

        multiplayerPanel.getJoinBtn().addActionListener((e) -> {
            Game.getInstance().getNetworkManager().joinServer(multiplayerPanel.getHostnameField().getText());
            aiGame = false;
        });

        multiplayerPanel.getJoinAiBtn().addActionListener((e) -> {
            Game.getInstance().getNetworkManager().joinServer(multiplayerPanel.getHostnameField().getText());
            aiGame = true;
        });

        multiplayerPanel.getHostBtn().addActionListener((e) -> {
            Game.getInstance().getSceneManager().setActiveScene(MultiplayerHostSettingsScene.class);
            aiGame = false;
        });

        multiplayerPanel.getBackBtn().addActionListener((e) -> {
            Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
            aiGame = false;
        });

        return multiplayerPanel;
    }

    @Override
    public void sizeUpdated() {
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

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

    @Override
    public void OnGameJoined(int mapSize) {
        ShipsSelectionScene scene = (ShipsSelectionScene) Game.getInstance().getSceneManager().setActiveScene(ShipsSelectionScene.class);
        scene.setNetworkGame(true);
        scene.initializeGameSession(new GameSessionData(new Map(mapSize), mapSize, AiDifficulty.Medium, aiGame));
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
        Savegame savegame = Game.getInstance().getGameFileHandler().loadSavegame(id);

        if (savegame != null) {
            if (savegame.isNetworkGame()) {
                if (savegame.getAi() == null) {
                    MultiplayerScene scene = (MultiplayerScene) Game.getInstance().getSceneManager().setActiveScene(MultiplayerScene.class);
                    scene.initializeSavegame(savegame);
                } else {
                    MultiplayerAIScene scene = (MultiplayerAIScene) Game.getInstance().getSceneManager().setActiveScene(MultiplayerAIScene.class);
                    scene.initializeSavegame(savegame);
                }
            }
        } else
            JOptionPane.showMessageDialog(Game.getInstance().getWindow(), "Cant find a Savegame with the id: " + id, "Savegame loading...", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void OnGameClosed() {
        Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
    }
}