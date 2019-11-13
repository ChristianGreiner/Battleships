package scenes;

import core.Drawable;
import core.Game;
import core.GameWindow;
import graphics.CreditsRenderer;
import ui.GuiScene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CreditsScene extends Scene implements KeyListener, GuiScene, Drawable {

    private CreditsRenderer creditsRenderer;
    private GameWindow gameWindow;

    public CreditsScene() {
        super("CreditsScene");
    }

    @Override
    void onAdded() {
        super.onAdded();
    }

    @Override
    public JPanel buildGui(GameWindow gameWindow, JPanel panel) {
        this.gameWindow = gameWindow;

        panel.setLayout(new BorderLayout());

        String[] lines = new String[3];
        lines[0] = "BATTLESHIPS";
        lines[1] = "";
        lines[2] = "EIN SPIEL VON TEAM XYZ";

        this.creditsRenderer = new CreditsRenderer(lines, new Point(Game.getInstance().getWindow().getWidth(), Game.getInstance().getWindow().getHeight()));
        this.creditsRenderer.setBackground(Color.WHITE);
        this.creditsRenderer.setLocation(0, 0);
        this.creditsRenderer.setSize(gameWindow.getSize());
        panel.add(this.creditsRenderer, BorderLayout.CENTER);


        return panel;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
        }
    }

    @Override
    public void draw() {
        if(this.creditsRenderer != null) {
            this.creditsRenderer.setSize(this.gameWindow.getSize());
            this.creditsRenderer.draw();
        }
    }
}

