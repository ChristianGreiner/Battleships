package scenes;

import core.Drawable;
import core.Game;
import core.GameWindow;
import game.Assets;
import game.Credit;
import graphics.CreditsRenderer;
import ui.CreditsPanel;
import ui.GuiScene;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class CreditsScene extends Scene implements KeyListener, GuiScene, Drawable, MouseListener {

    private CreditsRenderer creditsRenderer;
    private GameWindow gameWindow;
    private CreditsPanel rootPanel;

    public CreditsScene() {
        super("CreditsScene");
    }

    @Override
    public void onSwitched() {
        super.onSwitched();
    }

    @Override
    public JPanel buildGui(GameWindow gameWindow) {
        this.gameWindow = gameWindow;

        ArrayList<Credit> credits = new ArrayList<>();
        credits.add(new Credit("BATTLESHIPS", Assets.Fonts.TITLE_BIG));
        credits.add(new Credit("", Assets.Fonts.TITLE_BIG));
        credits.add(new Credit("CREDITS", Assets.Fonts.DEFAULT_BOLD_24));
        credits.add(new Credit("Jospeh Rieger (User Interface)", Assets.Fonts.DEFAULT));
        credits.add(new Credit("Christian Greiner (Game Engine und Speiellogik)", Assets.Fonts.DEFAULT));
        credits.add(new Credit("Shadrach Patrick (Artificial Intelligence)", Assets.Fonts.DEFAULT));
        credits.add(new Credit("", Assets.Fonts.TITLE_BIG));
        credits.add(new Credit("ASSETS", Assets.Fonts.DEFAULT_BOLD_24));
        credits.add(new Credit("Tiles by Kenny Assets (kenney.nl)", Assets.Fonts.DEFAULT));
        credits.add(new Credit("Main Menu Music by FootageCrate (footagecrate.com)", Assets.Fonts.DEFAULT));
        credits.add(new Credit("The War by Ender Güney", Assets.Fonts.DEFAULT));

        this.rootPanel = new CreditsPanel().create(credits);
        this.creditsRenderer = this.rootPanel.getCreditsRenderer();
        this.creditsRenderer.addMouseListener(this);

        return this.rootPanel;
    }

    @Override
    public void sizeUpdated() {
        this.rootPanel.setPreferredSize(Game.getInstance().getWindow().getSize());
        this.rootPanel.getCreditsRenderer().setPreferredSize(Game.getInstance().getWindow().getSize());
        this.rootPanel.getCreditsRenderer().invalidateBuffer();
        this.gameWindow.revalidate();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
        }
    }

    @Override
    public void draw() {
        if (this.creditsRenderer != null) {
            this.creditsRenderer.draw();
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}

