package scenes;

import core.Game;
import core.GameWindow;
import core.Updatable;
import ui.GuiScene;
import ui.MainMenuPanel;

import javax.swing.*;

public class MainMenuScene extends Scene implements Updatable, GuiScene {

    public MainMenuScene() {
        super("MainMenuScene");
    }

    @Override
    void onAdded() {
        super.onAdded();

        Game.getInstance().getSoundManager().playBackgroundMusic("we-will-win-sc1.wav");
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public JPanel buildGui(GameWindow gameWindow, JPanel panel) {
        MainMenuPanel menu = new MainMenuPanel();
        panel = menu.create(panel);

        menu.getSingleplayerBtn().addActionListener((e) -> {
            Game.getInstance().getSceneManager().setActiveScene("GameScene");
        });

        menu.getCreditsBtn().addActionListener((e) -> {
            Game.getInstance().getSceneManager().setActiveScene("CreditsScene");
        });

        menu.getExitBtn().addActionListener((e) -> { System.exit(0); });

        return panel;
    }
}