package ui;

import game.Assets;

import javax.swing.*;
import java.awt.*;

public class MultiplayerPanel extends JPanel {

    public MultiplayerPanel() {
    }

    public MultiplayerPanel create() {
        MultiplayerPanel panel = this;

        return panel;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(Assets.Images.BACKGROUND, 0, 0, getWidth(), getHeight(), this);
    }
}
