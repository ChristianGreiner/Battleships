package scenes;

import core.Drawable;
import core.Game;
import core.GameWindow;
import game.Assets;
import game.Credit;
import graphics.CreditsRenderer;
import ui.GuiScene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

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
    public JPanel buildGui(GameWindow gameWindow) {
        this.gameWindow = gameWindow;

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        ArrayList<Credit> credits = new ArrayList<>();
        credits.add(new Credit("BATTLESHIPS", Assets.Fonts.TITLE_BIG));
        credits.add(new Credit("", Assets.Fonts.TITLE_BIG));
        credits.add(new Credit("EIN SPIEL VON:", Assets.Fonts.DEFAULT_BOLD_24));
        credits.add(new Credit("JOSEPH DER ECHTE", Assets.Fonts.DEFAULT_18));
        credits.add(new Credit("GREINER DER WEBDESIGNER", Assets.Fonts.DEFAULT_18));
        credits.add(new Credit("SHADY DER INDER", Assets.Fonts.DEFAULT_18));
        credits.add(new Credit("FREDDY", Assets.Fonts.DEFAULT_18));

        this.creditsRenderer = new CreditsRenderer(credits, new Point(Game.getInstance().getWindow().getWidth(), Game.getInstance().getWindow().getHeight()));
        this.creditsRenderer.setBackground(Color.WHITE);
        this.creditsRenderer.setLocation(0, 0);
        this.creditsRenderer.setSize(gameWindow.getSize());
        panel.add(this.creditsRenderer, BorderLayout.CENTER);


        return panel;
    }

    @Override
    public void sizeUpdated() {

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
            Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class, null);
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

