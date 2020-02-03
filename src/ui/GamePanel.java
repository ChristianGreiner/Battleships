package ui;

import core.Game;
import game.Assets;
import graphics.MapRenderer;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Class for the JPanel for the game itself.
 */
public class GamePanel extends JPanel {
    private MapRenderer playerMapRenderer;
    private MapRenderer enemyMapRenderer;
    private JButton btnExit;
    private JButton btnLoad;
    private JButton btnSave;
    private JPanel playerLabelContainer;
    private JPanel enemyLabelContainer;
    private JLabel playerLabel;
    private JLabel opponentLabel;

    /**
     * Contructor for the game panel.
     * @param playerMapRenderer The Renderer for the player's map.
     * @param enemyMapRenderer The Renderer for the opponent's map.
     */
    public GamePanel(MapRenderer playerMapRenderer, MapRenderer enemyMapRenderer) {
        this.playerMapRenderer = playerMapRenderer;
        this.enemyMapRenderer = enemyMapRenderer;
    }

    /**
     * Gets the player label container.
     *
     * @return The container.
     */
    public JPanel getPlayerLabelContainer() {
        return playerLabelContainer;
    }

    /**
     * Gets the enemy label container.
     *
     * @return The container.
     */
    public JPanel getEnemyLabelContainer() {
        return enemyLabelContainer;
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
     * Gets the load button.
     *
     * @return The button.
     */
    public JButton getBtnLoad() {
        return btnLoad;
    }

    /**
     * Gets the save button.
     *
     * @return The button.
     */
    public JButton getBtnSave() {
        return btnSave;
    }

    /**
     * Creates a new panel.
     *
     * @param mapsRendererSize The render size of the map.
     * @return The panel.
     */
    public GamePanel create(Dimension mapsRendererSize) {
        GamePanel mainContainer = this;

        mainContainer.setLayout(new GridBagLayout());
        mainContainer.setBackground(UiBuilder.TRANSPARENT);

        JPanel gameFieldsContainer = new JPanel();
        gameFieldsContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        gameFieldsContainer.setBackground(UiBuilder.TRANSPARENT);

        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        mainContainer.add(gameFieldsContainer, gbc);

        JPanel leftContainer = new JPanel();
        leftContainer.setLayout(new GridBagLayout());
        leftContainer.setBackground(UiBuilder.TRANSPARENT);
        gameFieldsContainer.add(leftContainer);

        playerLabelContainer = new JPanel();
        playerLabelContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
        playerLabelContainer.setBackground(UiBuilder.TRANSPARENT);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        leftContainer.add(playerLabelContainer, gbc);

        playerLabel = new JLabel();
        playerLabel.setText("YOU");
        playerLabel.setBackground(UiBuilder.TRANSPARENT);
        playerLabel.setForeground(Color.WHITE);
        playerLabel.setFont(Assets.Fonts.DEFAULT_24);
        Border border = playerLabel.getBorder();
        Border margin = new EmptyBorder(0, 10, 0, 10);
        playerLabel.setBorder(new CompoundBorder(border, margin));
        playerLabelContainer.add(playerLabel);

        playerMapRenderer.setLayout(new GridBagLayout());
        playerMapRenderer.setPreferredSize(mapsRendererSize);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        leftContainer.add(playerMapRenderer, gbc);

        JPanel rightContainer = new JPanel();
        rightContainer.setLayout(new GridBagLayout());
        rightContainer.setBackground(UiBuilder.TRANSPARENT);
        gameFieldsContainer.add(rightContainer);

        enemyLabelContainer = new JPanel();
        enemyLabelContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
        enemyLabelContainer.setBackground(Color.BLACK);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        rightContainer.add(enemyLabelContainer, gbc);

        opponentLabel = new JLabel();
        opponentLabel.setText("OPPONENT");
        border = opponentLabel.getBorder();
        margin = new EmptyBorder(0, 10, 0, 10);
        opponentLabel.setBorder(new CompoundBorder(border, margin));
        opponentLabel.setForeground(Color.WHITE);
        opponentLabel.setFont(Assets.Fonts.DEFAULT_24);
        opponentLabel.setBackground(Color.BLACK);
        enemyLabelContainer.add(opponentLabel);

        enemyMapRenderer.setLayout(new GridBagLayout());
        enemyMapRenderer.setPreferredSize(mapsRendererSize);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        rightContainer.add(enemyMapRenderer, gbc);

        JPanel btnContainer = new JPanel();
        btnContainer.setLayout(new BorderLayout(0, 0));
        btnContainer.setBackground(UiBuilder.TRANSPARENT);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        mainContainer.add(btnContainer, gbc);

        JPanel exitBtnContainer = new JPanel();
        exitBtnContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
        exitBtnContainer.setBackground(UiBuilder.TRANSPARENT);
        btnContainer.add(exitBtnContainer, BorderLayout.WEST);

        btnExit = UiBuilder.createButton("EXIT", new Dimension(120, 32));
        exitBtnContainer.add(btnExit);

        JPanel saveGameContainer = new JPanel();
        saveGameContainer.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        saveGameContainer.setBackground(UiBuilder.TRANSPARENT);
        btnContainer.add(saveGameContainer, BorderLayout.CENTER);

        btnLoad = UiBuilder.createButton("LOAD", new Dimension(120, 32));
        btnLoad.setHorizontalAlignment(0);
        saveGameContainer.add(btnLoad);

        btnSave = UiBuilder.createButton("SAVE", new Dimension(120, 32));
        saveGameContainer.add(btnSave);

        return mainContainer;
    }

    /**
     * Forces to update the map size.
     *
     * @param size The size of the map.
     */
    public void updateMapSize(Dimension size) {
        this.playerMapRenderer.setPreferredSize(size);
        this.playerMapRenderer.setSize(size);
        this.playerMapRenderer.resizeMapSize();
        this.playerMapRenderer.invalidate();

        this.enemyMapRenderer.setPreferredSize(size);
        this.enemyMapRenderer.setSize(size);
        this.enemyMapRenderer.resizeMapSize();
        this.enemyMapRenderer.invalidate();

        Game.getInstance().getWindow().revalidate();
    }

    /**
     * Paints the backround.
     * @param graphics The graphics component.
     */
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(Assets.Images.BACKGROUND, 0, 0, getWidth(), getHeight(), this);
    }
}
