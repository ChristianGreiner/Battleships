package ui;

import com.sun.xml.internal.bind.WhiteSpaceProcessor;
import game.Assets;
import graphics.MapRenderer;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private JPanel playerMapContainer;
    private JPanel enemyMapContainer;
    private JPanel mapsContainer;
    private MapRenderer playerMapRenderer;
    private MapRenderer enemyMapRenderer;

    public GamePanel(MapRenderer playerMapRenderer, MapRenderer enemyMapRenderer) {
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

    public GamePanel create(Dimension mapsRendererSize) {
        GamePanel panel = this;

        panel.setLayout(new GridBagLayout());
        panel.setVisible(true);

        mapsContainer = new JPanel();
        mapsContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        mapsContainer.setBackground(UiBuilder.TRANSPARENT);
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(mapsContainer, gbc);

        playerMapContainer = new JPanel();
        playerMapContainer.setBackground(UiBuilder.TRANSPARENT);
        playerMapContainer.setLayout(new GridBagLayout());
        mapsContainer.add(playerMapContainer);

        final JLabel playerLabel = new JLabel();
        playerLabel.setHorizontalAlignment(0);
        playerLabel.setText("YOU");
        playerLabel.setFont(Assets.Fonts.DEFAULT_24);
        playerLabel.setForeground(Color.WHITE);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        playerMapContainer.add(playerLabel, gbc);

        final JPanel spacer1 = new JPanel();
        spacer1.setBackground(UiBuilder.TRANSPARENT);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        playerMapContainer.add(spacer1, gbc);

        playerMapRenderer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        playerMapRenderer.setPreferredSize(mapsRendererSize);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        playerMapContainer.add(playerMapRenderer, gbc);

        final JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(10, 1));
        spacer.setBackground(UiBuilder.TRANSPARENT);
        spacer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        playerMapContainer.add(spacer, gbc);

        enemyMapContainer = new JPanel();
        enemyMapContainer.setBackground(UiBuilder.TRANSPARENT);
        enemyMapContainer.setLayout(new GridBagLayout());
        mapsContainer.add(enemyMapContainer);

        final JLabel enemyLabel = new JLabel();
        enemyLabel.setHorizontalAlignment(0);
        enemyLabel.setHorizontalTextPosition(0);
        enemyLabel.setText("OPPONENT");
        enemyLabel.setForeground(Color.WHITE);
        enemyLabel.setFont(Assets.Fonts.DEFAULT_24);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        enemyMapContainer.add(enemyLabel, gbc);

        final JPanel spacer2 = new JPanel();
        spacer2.setBackground(UiBuilder.TRANSPARENT);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        enemyMapContainer.add(spacer2, gbc);

        enemyMapRenderer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        enemyMapRenderer.setPreferredSize(mapsRendererSize);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        enemyMapContainer.add(enemyMapRenderer, gbc);

        final JPanel spacer3 = new JPanel();
        spacer3.setBackground(UiBuilder.TRANSPARENT);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(spacer3, gbc);

        final JPanel spacer4 = new JPanel();
        spacer4.setBackground(UiBuilder.TRANSPARENT);
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
