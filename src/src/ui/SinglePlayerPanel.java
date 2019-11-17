package ui;

import core.Fonts;
import graphics.MapRenderer;

import javax.swing.*;
import java.awt.*;

public class SinglePlayerPanel extends JPanel {

    private JPanel playerMapContainer;
    private JPanel enemyMapContainer;
    private JPanel mapsContainer;
    private JPanel enemyMapRenderer;
    private MapRenderer playerMapRenderer;

    public SinglePlayerPanel(MapRenderer playerMapRenderer) {
        this.playerMapRenderer = playerMapRenderer;
    }

    public SinglePlayerPanel create() {
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
        playerLabel.setFont(Fonts.DEFAULT_24);
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
        playerMapRenderer.setPreferredSize(new Dimension(320, 320));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        playerMapContainer.add(playerMapRenderer, gbc);

        enemyMapContainer = new JPanel();
        enemyMapContainer.setLayout(new GridBagLayout());
        mapsContainer.add(enemyMapContainer);

        final JLabel enemyLabel = new JLabel();
        enemyLabel.setHorizontalAlignment(0);
        enemyLabel.setHorizontalTextPosition(0);
        enemyLabel.setText("Player 2");
        enemyLabel.setFont(Fonts.DEFAULT_24);
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

        enemyMapRenderer = new JPanel();
        enemyMapRenderer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        enemyMapRenderer.setPreferredSize(new Dimension(320, 320));
        enemyMapRenderer.setBackground(Color.GREEN);
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
}
