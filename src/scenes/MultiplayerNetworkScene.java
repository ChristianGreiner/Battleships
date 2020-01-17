package scenes;

import core.Game;
import core.GameWindow;
import core.Updatable;
import game.GameSessionData;
import game.HitType;
import game.Map;
import network.NetworkListener;
import network.commands.ShotCommand;
import ui.GuiScene;
import ui.MultiplayerNetworkPanel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MultiplayerNetworkScene extends Scene implements Updatable, GuiScene, KeyListener, NetworkListener {

    public MultiplayerNetworkScene() {
        super("MultiplayerNetworkScene");
    }

    @Override
    public void onAdded() {
        super.onAdded();
        Game.getInstance().getNetworkManager().addNetworkListener(this);
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
            Game.getInstance().getNetworkManager().joinServer(multiplayerPanel.getHostnameField().getText(), 5555);
        });

        multiplayerPanel.getHostBtn().addActionListener((e) -> {
            //Game.getInstance().getNetworkManager().startServer(5555);
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

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Game.getInstance().getSoundManager().stopBackgroundMusic();
            Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
        }

        if (keyEvent.getKeyCode() == KeyEvent.VK_J) {
            Game.getInstance().getNetworkManager().joinServer("localhost", 5555);
        }

        if (keyEvent.getKeyCode() == KeyEvent.VK_S) {
            Game.getInstance().getNetworkManager().startServer(5555);
        }

        if (keyEvent.getKeyCode() == KeyEvent.VK_B) {
            Game.getInstance().getNetworkManager().sendClientMessage(new ShotCommand(1, 2));
        }

        if (keyEvent.getKeyCode() == KeyEvent.VK_N) {
            Game.getInstance().getNetworkManager().sendServerMessage(new ShotCommand(2, 2));
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void OnPlayerConnected() {

    }

    @Override
    public void OnGameJoined(int mapSize, int[] ships) {

        System.out.println("Game joined");

        Game.getInstance().getSceneManager().setActiveScene(ShipsSelectionScene.class);
        ShipsSelectionScene scene = (ShipsSelectionScene) Game.getInstance().getSceneManager().setActiveScene(ShipsSelectionScene.class);

        scene.initializeGameSession(new GameSessionData(new Map(10), 10, null));
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