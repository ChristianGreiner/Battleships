package scenes;

import core.GameWindow;
import core.Updatable;
import ui.GuiScene;
import ui.MultiplayerPanel;

import javax.swing.*;

public class MultiplayerScene extends Scene implements Updatable, GuiScene {

    public MultiplayerScene() {
        super("MultiplayerScene");
    }

    @Override
    void onAdded() {
        super.onAdded();
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public JPanel buildGui(GameWindow gameWindow) {
        MultiplayerPanel panel = new MultiplayerPanel();

        return panel.create();
    }

    @Override
    public void sizeUpdated() {
    }
}