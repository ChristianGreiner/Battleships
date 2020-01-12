package scenes;

import ai.AiDifficulty;
import core.Drawable;
import core.Game;
import core.GameWindow;
import game.Assets;
import game.Map;
import game.MapGenerator;
import game.MapListener;
import game.ships.Ship;
import graphics.MapBuilderRenderer;
import graphics.MapRendererListener;
import ui.GuiScene;
import ui.ShipSelectionPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ShipsSelectionScene extends Scene implements Drawable, GuiScene, KeyListener, MapRendererListener, MapListener {

    private int mapSize;
    private AiDifficulty aiDifficulty;
    private MapBuilderRenderer buildRenderer;
    private Map playerMap;
    private ShipSelectionPanel uiPanel;
    private MapGenerator mapGenerator;

    public ShipsSelectionScene() {
        super("ShipsSelectionScene");

        this.buildRenderer = new MapBuilderRenderer(new Map(10));
        this.buildRenderer.addMapRendererListener(this);
    }

    @Override
    public void onAdded() {
        super.onAdded();
        this.mapGenerator = new MapGenerator();
        Game.getInstance().getSoundManager().playBackgroundMusic(Assets.Sounds.PLAYING_MUSIC, true);
        this.buildRenderer.setEditorMode(true);
    }

    public void initializeGame(int mapSize, AiDifficulty difficulty) {
        this.mapSize = mapSize;
        this.aiDifficulty = difficulty;
        this.playerMap = new Map(this.mapSize);
        this.buildRenderer.setMap(this.playerMap);
        this.playerMap.addListener(this);
    }

    @Override
    public JPanel buildGui(GameWindow gameWindow) {
        ShipSelectionPanel shipSelectionPanel = new ShipSelectionPanel(this.buildRenderer);

        shipSelectionPanel.create(new Dimension(800, 512));

        this.uiPanel = shipSelectionPanel;

        shipSelectionPanel.getBtnCancel().addActionListener((e) -> {
            Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
        });

        shipSelectionPanel.getBtnRandomMap().addActionListener((e) -> {
            this.playerMap = this.mapGenerator.generate(this.mapSize);
            this.buildRenderer.setMap(this.playerMap);
            this.OnMapUpdated();
        });

        shipSelectionPanel.getBtnStartGame().addActionListener((e) -> {
            SingePlayeScene scene = (SingePlayeScene) Game.getInstance().getSceneManager().setActiveScene(SingePlayeScene.class);

            scene.initializeGame(this.playerMap, this.aiDifficulty);
        });

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
        this.uiPanel.invalidate();
        if (this.buildRenderer != null) {
            this.buildRenderer.draw();
        }
    }

    @Override
    public void OnShipDropped(Map map, Ship ship, Point pos, boolean rotated) {
        ship.setRotated(rotated);
        map.move(ship, pos);
    }

    @Override
    public void OnShotFired(Map map, Point pos) {
        return;
    }

    @Override
    public void OnRotated(Map map, Ship ship) {
        map.rotate(ship);
    }

    @Override
    public void OnMapUpdated() {
        if(this.playerMap != null) {
            this.uiPanel.getBtnStartGame().setEnabled(this.playerMap.isCorrectFilled());
        }
    }
}