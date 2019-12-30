package ui;

import game.Assets;
import graphics.MapBuilderRenderer;
import graphics.MapRenderer;

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
        mapContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(mapContainer, gbc);

        final JLabel playerLabel = new JLabel();
        playerLabel.setHorizontalAlignment(0);
        playerLabel.setText("BUILD SHIP LAYOUT");
        playerLabel.setFont(Assets.Fonts.DEFAULT_24);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(playerLabel, gbc);

        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel.add(spacer1, gbc);

        mapRenderer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        mapRenderer.setPreferredSize(mapsRendererSize);
        mapRenderer.setVisible(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(mapRenderer, gbc);
        return panel;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(Assets.Images.BACKGROUND, 0, 0, getWidth(), getHeight(), this);
    }
}
