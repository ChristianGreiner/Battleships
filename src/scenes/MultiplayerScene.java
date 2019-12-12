package scenes;

import core.Game;
import core.GameWindow;
import core.Updatable;
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
    void onAdded() {
        super.onAdded();
    }

    @Override
    public void update(double deltaTime) {
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
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}