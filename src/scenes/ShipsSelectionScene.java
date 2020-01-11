package scenes;

import ai.AiDifficulty;
import core.Drawable;
import core.Game;
import core.GameWindow;
import game.Assets;
import game.Map;
import game.ships.Ship;
import graphics.MapBuilderRenderer;
import graphics.MapRendererListener;
import ui.GuiScene;
import ui.ShipSelectionPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ShipsSelectionScene extends Scene implements Drawable, GuiScene, KeyListener, MapRendererListener {

    private int mapSize;
    private AiDifficulty difficulty;
    private MapBuilderRenderer buildRenderer;
    private Map playerMap;
    private ShipSelectionPanel uiPanel;

    public ShipsSelectionScene() {
        super("ShipsSelectionScene");

        this.buildRenderer = new MapBuilderRenderer(null);
        this.buildRenderer.addMapRendererListener(this);
    }

    @Override
    public void onAdded() {
        super.onAdded();
        Game.getInstance().getSoundManager().playBackgroundMusic(Assets.Sounds.PLAYING_MUSIC, true);
        this.buildRenderer.setEditorMode(true);
    }

    public void initializeGame(int mapSize, AiDifficulty difficulty) {
        this.mapSize = mapSize;
        this.difficulty = difficulty;
        this.playerMap = new Map(this.mapSize);
        this.buildRenderer.setMap(this.playerMap);
    }

    @Override
    public JPanel buildGui(GameWindow gameWindow) {
        ShipSelectionPanel shipSelectionPanel = new ShipSelectionPanel(this.buildRenderer);

        shipSelectionPanel.create(new Dimension(800, 512));

        this.uiPanel = shipSelectionPanel;

        return shipSelectionPanel;
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

    @Override
    public void draw() {
        if (this.buildRenderer != null) {
            this.buildRenderer.draw();
        }
    }

    @Override
    public void OnShipDropped(Map map, Ship ship, Point pos, boolean rotated) {
        ship.setRotated(rotated);
        map.move(ship, pos);
        System.out.println("DROPPED AT " + pos);
    }

    @Override
    public void OnShotFired(Map map, Point pos) {
        return;
    }

    @Override
    public void OnRotated(Map map, Ship ship) {
        map.rotate(ship);
    }

}