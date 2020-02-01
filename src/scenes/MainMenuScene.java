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
    public void onSwitched() {
        super.onSwitched();
        Game.getInstance().getSoundManager().playBackgroundMusic(Assets.Sounds.MAINMENU_MUSIC, false);
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void lateUpdate(double deltaTime) {

    }

    @Override
    public JPanel buildGui(GameWindow gameWindow) {
        MainMenuPanel menu = new MainMenuPanel().create();

        menu.getSingleplayerBtn().addActionListener((e) -> {
            Game.getInstance().getSceneManager().setActiveScene(SinglePlayerSettingsScene.class);
        });

        menu.getMultiplayerBtn().addActionListener((e) -> {
            Game.getInstance().getSceneManager().setActiveScene(MultiplayerNetworkScene.class);
        });

        menu.getOptionsBtn().addActionListener((e) -> {
            Game.getInstance().getSceneManager().setActiveScene(OptionsScene.class);
        });

        menu.getCreditsBtn().addActionListener((e) -> {
            Game.getInstance().getSceneManager().setActiveScene(CreditsScene.class);
        });

        menu.getExitBtn().addActionListener((e) -> {
            System.exit(0);
        });

        return menu;
    }

    @Override
    public void sizeUpdated() {

    }
}