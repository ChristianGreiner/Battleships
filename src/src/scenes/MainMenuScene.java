package scenes;

import core.Game;
import core.GameWindow;
import core.Updatable;
import ui.GuiScene;
import ui.MainMenuPanel;

import javax.swing.*;
import java.awt.*;

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
    public JPanel buildGui(GameWindow gameWindow) {
        MainMenuPanel menu = new MainMenuPanel();
        JPanel pnl = menu.create();
        pnl.setBackground(Color.WHITE);

        menu.getSingleplayerBtn().addActionListener((e) -> {
            Game.getInstance().getSceneManager().setActiveScene("GameScene");
        });

        menu.getCreditsBtn().addActionListener((e) -> {
            Game.getInstance().getSceneManager().setActiveScene("CreditsScene");
        });

        menu.getExitBtn().addActionListener((e) -> { System.exit(0); });

        return pnl;
    }
}