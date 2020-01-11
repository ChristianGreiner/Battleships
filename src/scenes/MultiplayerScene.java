package scenes;

import core.GameWindow;
import core.Updatable;
import graphics.MapRenderer;
import ui.GamePanel;
import ui.GuiScene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MultiplayerScene extends Scene implements Updatable, GuiScene, KeyListener {

    private MapRenderer playerMapRenderer;
    private MapRenderer enemyMapRenderer;

    public MultiplayerScene() {
        super("MultiplayerScene");

        this.playerMapRenderer = new MapRenderer(null);
        this.enemyMapRenderer = new MapRenderer(null);
    }

    @Override
    public void onAdded() {
        super.onAdded();
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void lateUpdate(double deltaTime) {

    }

    @Override
    public JPanel buildGui(GameWindow gameWindow) {
        GamePanel gamePanel = new GamePanel(this.playerMapRenderer, this.enemyMapRenderer);

        gamePanel = gamePanel.create(new Dimension(512, 512));

        return gamePanel;
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

    }
}