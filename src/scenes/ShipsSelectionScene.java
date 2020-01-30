package scenes;

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

    private MapBuilderRenderer buildRenderer;
    private Map playerMap;
    private ShipSelectionPanel uiPanel;
    private MapGenerator mapGenerator;
    private boolean networkGame = false;
    private GameSessionData gameSessionData;

    public boolean isNetworkGame() {
        return networkGame;
    }

    public void setNetworkGame(boolean networkGame) {
        this.networkGame = networkGame;
    }

    public ShipsSelectionScene() {
        super("ShipsSelectionScene");

        this.buildRenderer = new MapBuilderRenderer(null);
        this.buildRenderer.addMapRendererListener(this);
    }

    @Override
    public void onAdded() {
        super.onAdded();
        this.mapGenerator = new MapGenerator();
        Game.getInstance().getSoundManager().playBackgroundMusic(Assets.Sounds.PLAYING_MUSIC, true);
        this.buildRenderer.setEditorMode(true);
        this.uiPanel.getBtnStartGame().setEnabled(false);

        this.sizeUpdated();
    }

    @Override
    public void initializeGameSession(GameSessionData data) {
        this.gameSessionData = data;
        this.playerMap = new Map(data.getMapSize());
        this.buildRenderer.init(this.playerMap);
        this.buildRenderer.setMap(this.playerMap);
        this.buildRenderer.addKeyListener(this);
        this.playerMap.addListener(this);

        Game.getInstance().getWindow().repaint();
        Game.getInstance().getWindow().revalidate();
    }

    @Override
    public JPanel buildGui(GameWindow gameWindow) {
        ShipSelectionPanel shipSelectionPanel = new ShipSelectionPanel(this.buildRenderer);

        shipSelectionPanel.create(new Dimension(1024, 512));

        this.uiPanel = shipSelectionPanel;

        shipSelectionPanel.getBtnCancel().addActionListener((e) -> {
            Game.getInstance().getSceneManager().setActiveScene(SinglePlayerSettingsScene.class);
        });

        shipSelectionPanel.getBtnRandomMap().addActionListener((e) -> {
            this.playerMap = this.mapGenerator.generate(this.gameSessionData.getMapSize());
            this.buildRenderer.setMap(this.playerMap);
            this.OnMapUpdated();
            this.buildRenderer.requestFocus();
        });

        shipSelectionPanel.getBtnStartGame().addActionListener((e) -> {
            if(this.isNetworkGame()) {
                // Multiplayer with AI
                if(this.gameSessionData.isAiGame()) {
                    MultiplayerAIScene scene = (MultiplayerAIScene) Game.getInstance().getSceneManager().setActiveScene(MultiplayerAIScene.class);
                    scene.initializeGameSession(new GameSessionData(this.playerMap, this.gameSessionData.getMapSize(), this.gameSessionData.getAiDifficulty(), true));
                } else {
                    // Multiplayer without Ai
                    MultiplayerScene scene = (MultiplayerScene) Game.getInstance().getSceneManager().setActiveScene(MultiplayerScene.class);
                    scene.initializeGameSession(new GameSessionData(this.playerMap, this.gameSessionData.getMapSize(), null));
                }
                Game.getInstance().getNetworkManager().confirmSession();
            } else {

                // local singleplayer with ai
                if(this.gameSessionData.isAiGame()) {
                    SinglePlayerAIScene scene = (SinglePlayerAIScene) Game.getInstance().getSceneManager().setActiveScene(SinglePlayerAIScene.class);
                    scene.reset();
                    scene.initializeGameSession(new GameSessionData(this.playerMap, this.gameSessionData.getMapSize(), this.gameSessionData.getAiDifficulty(), this.gameSessionData.isAiGame()));
                } else {
                    SinglePlayerScene scene = (SinglePlayerScene) Game.getInstance().getSceneManager().setActiveScene(SinglePlayerScene.class);
                    scene.reset();
                    scene.initializeGameSession(new GameSessionData(this.playerMap, this.gameSessionData.getMapSize(), this.gameSessionData.getAiDifficulty()));
                }
            }
        });

        return shipSelectionPanel;
    }

    @Override
    public void sizeUpdated() {
        this.uiPanel.updateMapSize(Game.getInstance().getWindow().getBuildMapRenderPanelSize());

        Game.getInstance().getWindow().repaint();
        Game.getInstance().getWindow().revalidate();
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
            this.buildRenderer.rotate();
        }
    }

    @Override
    public void draw() {
        if(this.uiPanel != null && this.buildRenderer != null) {
            if (this.uiPanel.isVisible() && this.uiPanel.isValid()) {
                this.buildRenderer.draw();
            }

            if(this.playerMap != null)
                this.uiPanel.getBtnStartGame().setEnabled(this.playerMap.isCorrectFilled());
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
                map.moveAndRotate(ship, pos);
            }
            else
                map.move(ship, pos);
        }

        this.OnMapUpdated();
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
    }


}