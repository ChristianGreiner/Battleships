package scenes;

import core.Updatable;
import ui.GuiScene;

import javax.swing.*;

public class MainMenuScene extends Scene implements Updatable, GuiScene {

    public MainMenuScene() {
        super("TestMenuScene");
    }

    @Override
    public void update(double deltaTime) {
        System.out.println(deltaTime);
    }

    @Override
    public JPanel buildGui() {

        return null;
    }
}