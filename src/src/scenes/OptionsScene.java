package scenes;

import core.Game;
import core.GameWindow;
import io.OptionsHandler;
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
    public JPanel buildGui(GameWindow gameWindow, JPanel panel) {
        OptionsPanel options = new OptionsPanel();
        panel = options.create(panel);

        OptionsHandler optionsHandler = new OptionsHandler();

        options.getSaveBtn().addActionListener((e) -> {
            optionsHandler.writeOptions(Game.getInstance().getOptions());
            Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
        });

        return panel;
    }
}
