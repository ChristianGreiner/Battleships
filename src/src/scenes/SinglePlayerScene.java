package scenes;

import core.Drawable;
import core.Game;
import core.GameWindow;
import core.Updatable;
import game.Assets;
import game.GameState;
import game.Map;
import game.PlayerType;
import graphics.MapRenderer;
import ui.GuiScene;
import ui.SinglePlayerPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SinglePlayerScene extends Scene implements KeyListener, Updatable, Drawable, GuiScene, PassableDataScene {

    private Map playerMap;
    private Map aiMap;
    private PlayerType playerTurn = PlayerType.Player;
    private GameState gameState = GameState.Started;
    private MapRenderer playerMapRenderer;
    private SinglePlayerPanel uiPanel;

    public SinglePlayerScene() {
        super("SinglePlayer");
        this.playerMapRenderer = new MapRenderer(null);
    }

    @Override
    void onAdded() {
        super.onAdded();
        Game.getInstance().getSoundManager().playBackgroundMusic(Assets.Sounds.PLAYING_MUSIC);
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void draw() {
        this.playerMapRenderer.draw();
    }

    @Override
    public JPanel buildGui(GameWindow gameWindow) {
        SinglePlayerPanel singlePlayerPanel = new SinglePlayerPanel(this.playerMapRenderer);
        singlePlayerPanel = singlePlayerPanel.create(new Dimension(512, 512));

        this.uiPanel = singlePlayerPanel;

        return singlePlayerPanel;
    }

    @Override
    public void sizeUpdated() {
        this.uiPanel.getPlayerMapRenderer().setPreferredSize(new Dimension(64, 64));
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
    public void onDataPassed(Object data) {
        Map map = (Map)data;
        this.playerMap = map;
    }
}
