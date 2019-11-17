package scenes;

import core.Game;
import core.GameWindow;
import game.Assets;
import ui.GuiScene;
import ui.OptionsPanel;

import javax.swing.*;

public class OptionsScene extends Scene implements GuiScene {

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
            Game.getInstance().getFileHandler().writeObject(Game.getInstance().getOptions(), Assets.OPTIONS);
            Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
        });

        return options;
    }
}
