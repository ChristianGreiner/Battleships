package ui;

import core.GameWindow;

import javax.swing.*;

public interface GuiScene {
    JPanel buildGui(GameWindow gameWindow);
}
