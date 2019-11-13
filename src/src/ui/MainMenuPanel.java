package ui;

import core.Fonts;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel implements UiPanel {

    public JButton getSingleplayerBtn() {
        return singleplayerBtn;
    }

    public JButton getMultiplayerBtn() {
        return multiplayerBtn;
    }

    public JButton getCreditsBtn() {
        return creditsBtn;
    }

    public JButton getOptionsBtn() {
        return optionsBtn;
    }

    public JButton getExitBtn() {
        return exitBtn;
    }

    private JButton singleplayerBtn, multiplayerBtn, creditsBtn, optionsBtn, exitBtn;

    private final int paddingSize = 12;

    public JPanel create(JPanel panel) {

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc;

        final JLabel title = new JLabel("BATTLESHIPS");
        title.setFont(Fonts.TITLE);
        title.setHorizontalAlignment(0);
        title.setHorizontalTextPosition(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0);
        panel.add(title, gbc);

        addSpace(panel, 2, null);

        this.singleplayerBtn = addButton("SINGLEPLAYER", panel, 3);

        addSpace(panel, 4, null);

        this.multiplayerBtn = addButton("MULTIPLAYER", panel, 5);

        addSpace(panel, 6, null);

        this.optionsBtn = addButton("OPTIONS", panel, 7);

        addSpace(panel, 8, null);

        this.creditsBtn = addButton("CREDITS", panel, 9);

        addSpace(panel, 10, null);

        this.exitBtn = addButton("EXIT", panel, 11);

        panel.validate();

        return panel;
    }

    private JButton addButton(String title, JPanel container, int y) {
        JButton btn = UiBuilder.createButton(title, new Dimension(320, 32));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = y;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        container.add(btn, gbc);

        return btn;
    }

    private void addSpace(JPanel container, int y, Insets insets) {
        final JPanel spacer = new JPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = y;
        gbc.fill = GridBagConstraints.VERTICAL;
        if (insets != null)
            gbc.insets = insets;
        spacer.setBackground(null);
        container.add(spacer, gbc);
    }
}
