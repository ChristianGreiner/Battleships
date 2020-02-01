package ui;

import core.Game;
import game.Credit;
import graphics.CreditsRenderer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CreditsPanel extends JPanel {

    private CreditsRenderer creditsRenderer;

    public CreditsRenderer getCreditsRenderer() {
        return creditsRenderer;
    }

    public CreditsPanel create(ArrayList<Credit> credits) {
        CreditsPanel rootPanel = this;
        rootPanel.setBackground(Color.WHITE);
        rootPanel.setLayout(new BorderLayout());

        creditsRenderer = new CreditsRenderer(credits, new Point(Game.getInstance().getWindow().getWidth(), Game.getInstance().getWindow().getHeight()));
        creditsRenderer.setLocation(0, 0);
        creditsRenderer.setBackground(Color.WHITE);
        rootPanel.add(creditsRenderer, BorderLayout.CENTER);

        return rootPanel;
    }
}
