package scenes;

import core.Updatable;
import ui.GuiScene;

import javax.swing.*;

public class TestMenu extends Scene implements Updatable, GuiScene {

    public TestMenu() {
        super("TestMenu");
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
