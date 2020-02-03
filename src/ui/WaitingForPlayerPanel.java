package ui;

import core.Game;
import game.Assets;

import javax.swing.*;
import java.awt.*;

/**
 * Class for the waiting for player panel.
 */
public class WaitingForPlayerPanel extends JPanel {

    private JButton backBtn;

    /**
     * Gets the back button
     *
     * @return The button.
     */
    public JButton getBackBtn() {
        return backBtn;
    }

    /**
     * Creates a new panel.
     *
     * @return The panel.
     */
    public WaitingForPlayerPanel create() {
        WaitingForPlayerPanel panel = this;

        panel.setOpaque(true);

        panel.setSize(Game.getInstance().getWindow().getWidth(), Game.getInstance().getWindow().getHeight());
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc;

        final JLabel title = new JLabel("WAITING FOR PLAYER TO JOIN...");
        title.setFont(Assets.Fonts.DEFAULT_24);
        title.setHorizontalAlignment(0);
        title.setHorizontalTextPosition(0);
        title.setForeground(Color.white);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0);
        panel.add(title, gbc);

        addSpace(panel, 2, null);

        this.backBtn = addButton("BACK", panel, 8);

        return panel;
    }

    /**
     * Adds button to the panel.
     * @param title Title of the button.
     * @param container Container the button should be in.
     * @param y Y component.
     * @return Returns the ready-made button.
     */
    private JButton addButton(String title, JPanel container, int y) {
        JButton btn = UiBuilder.createButton(title, new Dimension(320, UiBuilder.BUTTON_HEIGHT));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = y;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        container.add(btn, gbc);

        return btn;
    }

    /**
     * Adds space.
     * @param container Container where space should be added.
     * @param y The y component.
     * @param insets Insets.
     */
    private void addSpace(JPanel container, int y, Insets insets) {
        final JPanel spacer = new JPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = y;
        gbc.fill = GridBagConstraints.VERTICAL;
        if (insets != null)
            gbc.insets = insets;
        spacer.setOpaque(false);
        container.add(spacer, gbc);
    }

    /**
     * Draws the backround.
     * @param graphics The graphics component.
     */
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(Assets.Images.BACKGROUND, 0, 0, getWidth(), getHeight(), this);
    }
}
