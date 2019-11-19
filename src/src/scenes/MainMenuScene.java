package scenes;

import core.Game;
import core.GameWindow;
import core.Updatable;
import game.Assets;
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

        Game.getInstance().getSoundManager().playBackgroundMusic(Assets.Sounds.BACKGROUND_MUSIC);
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public JPanel buildGui(GameWindow gameWindow) {
        MainMenuPanel menu = new MainMenuPanel().create();

        menu.getSingleplayerBtn().addActionListener((e) -> {
            Game.getInstance().getSceneManager().setActiveScene(MapSelectionScene.class);
        });

        menu.getOptionsBtn().addActionListener((e) -> {
            Game.getInstance().getSceneManager().setActiveScene(OptionsScene.class);
        });

        menu.getCreditsBtn().addActionListener((e) -> {
            Game.getInstance().getSceneManager().setActiveScene(CreditsScene.class);
        });

        menu.getExitBtn().addActionListener((e) -> { System.exit(0); });

        return menu;
    }

    @Override
    public void sizeUpdated() {

    }
}