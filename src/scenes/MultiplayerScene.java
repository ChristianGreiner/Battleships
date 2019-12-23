package scenes;

import core.Game;
import core.GameWindow;
import core.Updatable;
import network.commands.ShotCommand;
import ui.GuiScene;
import ui.MultiplayerPanel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MultiplayerScene extends Scene implements Updatable, GuiScene, KeyListener {

    public MultiplayerScene() {
        super("MultiplayerScene");
    }

    @Override
    public void onAdded() {
        super.onAdded();
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void lateUpdate(double deltaTime) {

    }

    @Override
    public JPanel buildGui(GameWindow gameWindow) {
        MultiplayerPanel panel = new MultiplayerPanel();

        return panel.create();
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
            Game.getInstance().getNetworkManager().sendServerMessage(new ShotCommand(1, 2));
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}