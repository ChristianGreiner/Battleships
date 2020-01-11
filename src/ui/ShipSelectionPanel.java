package ui;

import game.Assets;
import graphics.MapBuilderRenderer;

import javax.swing.*;
import java.awt.*;

public class ShipSelectionPanel extends JPanel {

    private MapBuilderRenderer mapRenderer;

    public ShipSelectionPanel(MapBuilderRenderer mapRenderer) {
        this.mapRenderer = mapRenderer;
    }

    public MapBuilderRenderer getMapRenderer() {
        return mapRenderer;
    }

    public void updateMapSize(Dimension size) {
        this.mapRenderer.setPreferredSize(size);
    }

    public ShipSelectionPanel create(Dimension mapsRendererSize) {
        ShipSelectionPanel panel = this;

        panel.setLayout(new GridBagLayout());
        panel.setVisible(true);

        JPanel mapContainer = new JPanel();
        mapContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(mapContainer, gbc);

        JPanel playerMapContainer = new JPanel();
        playerMapContainer.setLayout(new GridBagLayout());
        mapContainer.add(playerMapContainer);

        final JLabel playerLabel = new JLabel();
        playerLabel.setHorizontalAlignment(0);
        playerLabel.setText("BUILD MAP");
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

        mapRenderer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        mapRenderer.setPreferredSize(mapsRendererSize);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        playerMapContainer.add(mapRenderer, gbc);

        return panel;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(Assets.Images.BACKGROUND, 0, 0, getWidth(), getHeight(), this);
    }
}