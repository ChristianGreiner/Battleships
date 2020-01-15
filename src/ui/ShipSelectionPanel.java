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

    private JButton btnRandomMap;
    private JButton btnCancel;

    public JButton getBtnRandomMap() {
        return btnRandomMap;
    }

    public JButton getBtnCancel() {
        return btnCancel;
    }

    public JButton getBtnStartGame() {
        return btnStartGame;
    }

    private JButton btnStartGame;

    public ShipSelectionPanel create(Dimension mapsRendererSize) {
        ShipSelectionPanel panel = this;

        panel.setLayout(new GridBagLayout());
        panel.setVisible(true);

        JPanel mapContainer = new JPanel();
        mapContainer.setBackground(UiBuilder.TRANSPARENT);
        mapContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(mapContainer, gbc);

        JPanel playerMapContainer = new JPanel();
        playerMapContainer.setBackground(UiBuilder.TRANSPARENT);
        playerMapContainer.setLayout(new GridBagLayout());
        mapContainer.add(playerMapContainer);

        final JLabel title = new JLabel();
        title.setHorizontalAlignment(0);
        title.setText("BUILD MAP");
        title.setFont(Assets.Fonts.TITLE_36);
        title.setForeground(Color.WHITE);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        playerMapContainer.add(title, gbc);

        final JPanel spacer1 = new JPanel();
        spacer1.setBackground(UiBuilder.TRANSPARENT);
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

        final JPanel spacer2 = new JPanel();
        spacer2.setBackground(UiBuilder.TRANSPARENT);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        playerMapContainer.add(spacer2, gbc);

        JPanel btnContainer = new JPanel();
        btnContainer.setLayout(new BorderLayout(0, 0));
        btnContainer.setBackground(UiBuilder.TRANSPARENT);

        JPanel randomBtnContainer = new JPanel();
        randomBtnContainer.setLayout(new GridBagLayout());
        btnContainer.add(randomBtnContainer, BorderLayout.WEST);

        btnRandomMap = UiBuilder.createButton("RANDOM", new Dimension(120, 32));
        GridBagConstraints gbc2;
        gbc2 = new GridBagConstraints();
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        randomBtnContainer.add(btnRandomMap, gbc2);

        JPanel btnStartContainer = new JPanel();
        btnStartContainer.setBackground(UiBuilder.TRANSPARENT);
        btnStartContainer.setLayout(new GridBagLayout());
        btnContainer.add(btnStartContainer, BorderLayout.EAST);

        btnCancel = UiBuilder.createButton("CANCEL", new Dimension(120, 32));
        gbc2 = new GridBagConstraints();
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        btnStartContainer.add(btnCancel, gbc2);

        btnStartGame = UiBuilder.createButton("START", new Dimension(120, 32));
        btnStartGame.setEnabled(false);
        gbc2 = new GridBagConstraints();
        gbc2.gridx = 2;
        gbc2.gridy = 0;
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        btnStartContainer.add(btnStartGame, gbc2);

        final JPanel spacerBtn = new JPanel();
        spacerBtn.setBackground(UiBuilder.TRANSPARENT);
        gbc2 = new GridBagConstraints();
        gbc2.gridx = 1;
        gbc2.gridy = 0;
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        btnStartContainer.add(spacerBtn, gbc2);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        playerMapContainer.add(btnContainer, gbc);

        return panel;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(Assets.Images.BACKGROUND, 0, 0, getWidth(), getHeight(), this);
    }
}