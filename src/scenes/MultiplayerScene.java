package scenes;

import core.Game;
import core.GameWindow;
import core.Updatable;
import game.GameSession;
import game.GameSessionData;
import game.Map;
import graphics.MapRenderer;
import ui.GamePanel;
import ui.GuiScene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MultiplayerScene extends Scene implements Updatable, GuiScene, KeyListener, GameSession {

    private MapRenderer playerMapRenderer;
    private MapRenderer enemyMapRenderer;

    private GameSessionData gameSessionData;

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
        if(Game.getInstance().getNetworkManager().isClientConfirmed() && Game.getInstance().getNetworkManager().isServerConfirmed()) {
            this.playerMapRenderer = new MapRenderer(new Map(gameSessionData.getMapSize()));
            this.enemyMapRenderer = new MapRenderer(new Map(gameSessionData.getMapSize()));
        }
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

    @Override
    public void initializeGameSession(GameSessionData data) {
        this.gameSessionData = data;
    }
}