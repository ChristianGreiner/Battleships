package ui;

import game.Assets;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class MultiplayerPanel extends JPanel {

    public MultiplayerPanel() {
    }

    public MultiplayerPanel create() {
        MultiplayerPanel panel = this;

        MigLayout layout = new MigLayout("fillx", "[right]rel[grow,fill]", "[]10[]");

        panel.setLayout(layout);
        panel.add(new JLabel("Enter size:"), "");
        panel.add(new JTextField(""), "wrap");
        panel.add(new JLabel("Enter weight:"), "");
        panel.add(new JTextField(""), "");

        return panel;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(Assets.Images.BACKGROUND, 0, 0, getWidth(), getHeight(), this);
    }
}
