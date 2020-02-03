package ui;

import game.Assets;
import game.PlayerType;
import network.NetworkType;

import javax.swing.*;
import java.awt.*;

/**
 * Class for the JPanel used in the {@link scenes.GameOverScene}
 */
public class GameOverPanel extends JPanel {

    private JButton btnRestart;
    private JButton btnExit;
    private JLabel gameStatusLabel;

    /**
     * Gets the restart button.
     *
     * @return The button.
     */
    public JButton getBtnRestart() {
        return btnRestart;
    }

    /**
     * Gets the exit button.
     *
     * @return The button.
     */
    public JButton getBtnExit() {
        return btnExit;
    }

    /**
     * Updates the winner text.
     *
     * @param winner The winner.
     */
    public void updateWinner(PlayerType winner) {
        String message;

        if (winner == PlayerType.Player)
            message = "YOU WIN";
        else
            message = "YOU LOSE";

        this.gameStatusLabel.setText(message);
    }

    /**
     * Updates the network winner.
     *
     * @param winner The winner.
     */
    public void updateWinner(NetworkType winner) {
        String message = winner.toString() + " WON!";
        this.gameStatusLabel.setText(message);
    }

    /**
     * Creates a new panel.
     *
     * @return The panel.
     */
    public GameOverPanel create() {
        GameOverPanel mainContainer = this;
        mainContainer.setLayout(new GridBagLayout());
        mainContainer.setBackground(Color.BLACK);

        JPanel btnContainer = new JPanel();
        btnContainer.setLayout(new GridBagLayout());
        btnContainer.setBackground(UiBuilder.TRANSPARENT);
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        mainContainer.add(btnContainer, gbc);

        btnRestart = UiBuilder.createButton("RESTART", new Dimension(240, 36));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        btnContainer.add(btnRestart, gbc);

        btnExit = UiBuilder.createButton("BACK TO MAIN MENU", new Dimension(240, 36));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        btnContainer.add(btnExit, gbc);

        final JPanel spacer1 = new JPanel();
        spacer1.setBackground(UiBuilder.TRANSPARENT);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        btnContainer.add(spacer1, gbc);

        JPanel titleContainer = new JPanel();
        titleContainer.setLayout(new GridBagLayout());
        titleContainer.setBackground(UiBuilder.TRANSPARENT);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        mainContainer.add(titleContainer, gbc);

        gameStatusLabel = new JLabel();
        gameStatusLabel.setHorizontalAlignment(0);
        gameStatusLabel.setHorizontalTextPosition(0);
        gameStatusLabel.setText("GAME OVER");
        gameStatusLabel.setForeground(Color.WHITE);
        gameStatusLabel.setFont(Assets.Fonts.TITLE);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        titleContainer.add(gameStatusLabel, gbc);

        JLabel gameOverLabel = new JLabel();
        gameOverLabel.setText("GAME OVER");
        gameOverLabel.setHorizontalAlignment(0);
        gameOverLabel.setHorizontalTextPosition(0);
        gameOverLabel.setForeground(Color.WHITE);
        gameOverLabel.setFont(Assets.Fonts.TITLE_36);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 30, 10);
        titleContainer.add(gameOverLabel, gbc);

        return mainContainer;
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
