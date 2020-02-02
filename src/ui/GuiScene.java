package ui;

import core.GameWindow;

import javax.swing.*;

/**
 * Used to create interface in scenes.
 */
public interface GuiScene {

    /**
     * Builds the gui.
     *
     * @param gameWindow The game window.
     * @return A new gui panel.
     */
    JPanel buildGui(GameWindow gameWindow);

    /**
     * Called, when the window size gets updated.
     */
    void sizeUpdated();
}
