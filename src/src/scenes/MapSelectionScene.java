package scenes;

import core.Game;
import core.GameWindow;
import ui.GuiScene;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MapSelectionScene extends Scene implements GuiScene, KeyListener {

    public MapSelectionScene() {
        super("MapSelectionScene");
    }

    @Override
    void onAdded() {
        super.onAdded();
    }


    @Override
    public JPanel buildGui(GameWindow gameWindow) {

        return new JPanel();
    }

    @Override
    public void sizeUpdated() {

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
        }
    }
}
