package ui;

import core.Game;
import game.Assets;
import graphics.MapRenderer;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
        private MapRenderer playerMapRenderer;
    private MapRenderer enemyMapRenderer;
    private JButton btnExit;
    private JButton btnLoad;
    private JButton btnSave;

    public JButton getBtnExit() {
        return btnExit;
    }

    public JButton getBtnLoad() {
        return btnLoad;
    }

    public JButton getBtnSave() {
        return btnSave;
    }

    public MapRenderer getPlayerMapRenderer() {
        return playerMapRenderer;
    }

    public MapRenderer getEnemyMapRenderer() {
        return enemyMapRenderer;
    }

    public GamePanel(MapRenderer playerMapRenderer, MapRenderer enemyMapRenderer) {
        this.playerMapRenderer = playerMapRenderer;
        this.enemyMapRenderer = enemyMapRenderer;
    }

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

        JPanel playerLabelContainer = new JPanel();
        playerLabelContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
        playerLabelContainer.setBackground(UiBuilder.TRANSPARENT);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        leftContainer.add(playerLabelContainer, gbc);
        JLabel playerLabel = new JLabel();
        playerLabel.setText("YOU");
        playerLabel.setForeground(Color.WHITE);
        playerLabel.setFont(Assets.Fonts.DEFAULT);
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

        JPanel enemyLabelContainer = new JPanel();
        enemyLabelContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
        enemyLabelContainer.setBackground(UiBuilder.TRANSPARENT);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        rightContainer.add(enemyLabelContainer, gbc);

        JLabel enemyLabel = new JLabel();
        enemyLabel.setText("OPPONENT");
        enemyLabel.setForeground(Color.WHITE);
        enemyLabel.setFont(Assets.Fonts.DEFAULT);
        enemyLabelContainer.add(enemyLabel);

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

    public void updateMapSize(Dimension size) {
        this.playerMapRenderer.setPreferredSize(size);
        this.playerMapRenderer.resizeMapSize();
        this.enemyMapRenderer.setPreferredSize(size);
        this.enemyMapRenderer.resizeMapSize();
        Game.getInstance().getWindow().revalidate();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(Assets.Images.BACKGROUND, 0, 0, getWidth(), getHeight(), this);
    }
}
