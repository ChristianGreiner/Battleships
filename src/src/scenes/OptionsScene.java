package scenes;

import core.Game;
import core.GameWindow;
import game.Assets;
import ui.GuiScene;
import ui.OptionsPanel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class OptionsScene extends Scene implements GuiScene, KeyListener {

    public OptionsScene() {
        super("OptionsScene");
    }

    @Override
    void onAdded() {
        super.onAdded();
    }


    @Override
    public JPanel buildGui(GameWindow gameWindow) {
        OptionsPanel options = new OptionsPanel().create();

        options.getSaveBtn().addActionListener((e) -> {
            Game.getInstance().getFileHandler().writeObject(Game.getInstance().getOptions(), Assets.Paths.OPTIONS);
            Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
        });

        return options;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Game.getInstance().getFileHandler().writeObject(Game.getInstance().getOptions(), Assets.Paths.OPTIONS);
            Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
        }
    }
}
