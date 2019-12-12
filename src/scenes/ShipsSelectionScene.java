package scenes;

import ai.AiDifficulty;
import core.Game;
import core.GameWindow;
import game.Assets;
import game.Map;
import graphics.MapRenderer;
import ui.GuiScene;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ShipsSelectionScene extends Scene implements GuiScene, KeyListener {

    private int mapSize;
    private AiDifficulty difficulty;
    private MapRenderer mapRenderer;
    private Map playerMap;

    public ShipsSelectionScene() {
        super("ShipsSelectionScene");
    }

    @Override
    public void onAdded() {
        super.onAdded();
        Game.getInstance().getSoundManager().playBackgroundMusic(Assets.Sounds.PLAYING_MUSIC, true);
    }

    public void initializeMap(int mapSize, AiDifficulty difficulty) {
        this.mapSize = mapSize;
        this.difficulty = difficulty;

        this.playerMap = new Map(this.mapSize);
        this.mapRenderer = new MapRenderer(this.playerMap);
    }

    @Override
    public JPanel buildGui(GameWindow gameWindow) {
        return new JPanel();
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
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
        }
    }
}
