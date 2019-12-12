package ui;

import game.Assets;
import graphics.MapRenderer;

import javax.swing.*;
import java.awt.*;

public class SinglePlayerPanel extends JPanel {

    private JPanel playerMapContainer;
    private JPanel enemyMapContainer;
    private JPanel mapsContainer;
    private MapRenderer playerMapRenderer;
    private MapRenderer enemyMapRenderer;

    public SinglePlayerPanel(MapRenderer playerMapRenderer, MapRenderer enemyMapRenderer) {
        this.playerMapRenderer = playerMapRenderer;
        this.enemyMapRenderer = enemyMapRenderer;
    }

    public MapRenderer getPlayerMapRenderer() {
        return playerMapRenderer;
    }

    public void updateMapSize(Dimension size) {
        this.playerMapRenderer.setPreferredSize(size);
        this.enemyMapRenderer.setPreferredSize(size);
    }

    public SinglePlayerPanel create(Dimension mapsRendererSize) {
        SinglePlayerPanel panel = this;

        panel.setLayout(new GridBagLayout());

        mapsContainer = new JPanel();
        mapsContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(mapsContainer, gbc);

        playerMapContainer = new JPanel();
        playerMapContainer.setLayout(new GridBagLayout());
        mapsContainer.add(playerMapContainer);

        final JLabel playerLabel = new JLabel();
        playerLabel.setHorizontalAlignment(0);
        playerLabel.setText("Player 1");
        playerLabel.setFont(Assets.Fonts.DEFAULT_24);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        playerMapContainer.add(playerLabel, gbc);

        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        playerMapContainer.add(spacer1, gbc);

        playerMapRenderer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        playerMapRenderer.setPreferredSize(mapsRendererSize);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        playerMapContainer.add(playerMapRenderer, gbc);

        final JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(40, 1));
        spacer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        playerMapContainer.add(spacer, gbc);

        enemyMapContainer = new JPanel();
        enemyMapContainer.setLayout(new GridBagLayout());
        mapsContainer.add(enemyMapContainer);

        final JLabel enemyLabel = new JLabel();
        enemyLabel.setHorizontalAlignment(0);
        enemyLabel.setHorizontalTextPosition(0);
        enemyLabel.setText("Player 2");
        enemyLabel.setFont(Assets.Fonts.DEFAULT_24);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        enemyMapContainer.add(enemyLabel, gbc);

        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        enemyMapContainer.add(spacer2, gbc);

        enemyMapRenderer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        enemyMapRenderer.setPreferredSize(mapsRendererSize);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        enemyMapContainer.add(enemyMapRenderer, gbc);

        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(spacer3, gbc);

        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel.add(spacer4, gbc);

        return panel;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(Assets.Images.BACKGROUND, 0, 0, getWidth(), getHeight(), this);
    }
}
