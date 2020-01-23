package scenes;

import ai.AiDifficulty;
import core.Drawable;
import core.Game;
import core.GameWindow;
import game.*;
import game.ships.Ship;
import graphics.MapBuilderRenderer;
import graphics.MapRendererListener;
import ui.GuiScene;
import ui.ShipSelectionPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ShipsSelectionScene extends Scene implements Drawable, GuiScene, KeyListener, MapRendererListener, MapListener, GameSession {

    private int mapSize;
    private AiDifficulty aiDifficulty;
    private MapBuilderRenderer buildRenderer;
    private Map playerMap;
    private ShipSelectionPanel uiPanel;
    private MapGenerator mapGenerator;
    private boolean networkGame = false;

    public boolean isNetworkGame() {
        return networkGame;
    }

    public void setNetworkGame(boolean networkGame) {
        this.networkGame = networkGame;
    }

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
        this.uiPanel.getBtnStartGame().setEnabled(false);
    }

    @Override
    public void initializeGameSession(GameSessionData data) {
        this.mapSize = data.getMapSize();
        this.aiDifficulty = data.getAiDifficulty();
        this.playerMap = new Map(this.mapSize);
        this.buildRenderer.setMap(this.playerMap);
        this.playerMap.addListener(this);
    }

    @Override
    public JPanel buildGui(GameWindow gameWindow) {
        ShipSelectionPanel shipSelectionPanel = new ShipSelectionPanel(this.buildRenderer);

        shipSelectionPanel.create(new Dimension(800, 450));

        this.uiPanel = shipSelectionPanel;

        shipSelectionPanel.getBtnCancel().addActionListener((e) -> {
            Game.getInstance().getSceneManager().setActiveScene(SinglePlayerSettingsScene.class);
        });

        shipSelectionPanel.getBtnRandomMap().addActionListener((e) -> {
            this.playerMap = this.mapGenerator.generate(this.mapSize);
            this.buildRenderer.setMap(this.playerMap);
            this.OnMapUpdated();
        });

        shipSelectionPanel.getBtnStartGame().addActionListener((e) -> {
            if(this.isNetworkGame()) {
                MultiplayerScene scene = (MultiplayerScene) Game.getInstance().getSceneManager().setActiveScene(MultiplayerScene.class);
                scene.initializeGameSession(new GameSessionData(this.playerMap, this.playerMap.getSize(), null));

                Game.getInstance().getNetworkManager().sendConfirmMessage();

            } else {
                SinglePlayerScene scene = (SinglePlayerScene) Game.getInstance().getSceneManager().setActiveScene(SinglePlayerScene.class);
                scene.reset();
                scene.initializeGameSession(new GameSessionData(this.playerMap, this.playerMap.getSize(), this.aiDifficulty));
            }
        });

        shipSelectionPanel.invalidate();

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

        if (e.getKeyCode() == KeyEvent.VK_R) {

        }

    }

    @Override
    public void draw() {
        if(this.uiPanel != null && this.buildRenderer != null) {
            if (this.uiPanel.isVisible() && this.uiPanel.isValid()) {
                this.buildRenderer.draw();
            }
        }
    }

    @Override
    public void OnShipDropped(Map map, Ship ship, Point pos, boolean rotated) {
        // ship not inserted  yet
        if(ship.getPosition() == null){
            map.insert(ship, pos, rotated);
        }
        else {
            // ship already inserted
            if((ship.isRotated() && !rotated) || (!ship.isRotated() && rotated)){
                map.rotate(ship);	                //map.move(ship, pos);

                map.testRotate(ship, pos);
                map.move(ship, pos);
            }
            else
                map.move(ship, pos);
        }
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