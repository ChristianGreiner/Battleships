package scenes;

import ai.AI;
import core.Drawable;
import core.Game;
import core.GameWindow;
import core.Updatable;
import game.*;
import game.ships.Ship;
import graphics.MapRenderer;
import graphics.MapRendererListener;
import network.NetworkListener;
import network.NetworkType;
import ui.GamePanel;
import ui.GuiScene;
import ui.UiBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MultiplayerAIScene extends Scene implements Updatable, GuiScene, Drawable, KeyListener, GameSession, NetworkListener, MapRendererListener {

    private Map playerMap;
    private Map enemyMap;
    private MapRenderer playerMapRenderer;
    private MapRenderer enemyMapRenderer;
    private GamePanel uiPanel;
    private GameSessionData gameSessionData;
    private boolean gameStarted = false;
    private Point lastShot = null;
    private NetworkType playerTurn = NetworkType.Client;
    private NetworkType networkType = NetworkType.Client;
    private NetworkType winner = NetworkType.Client;
    private AI playerAi;
    private int enemyShipsDestroyed = 0;
    private MapData mapData;
    private GameState gameState = null;

    public MultiplayerAIScene() {
        super("MultiplayerAIScene");

        this.playerMapRenderer = new MapRenderer(null);
        this.enemyMapRenderer = new MapRenderer(null);

        this.playerMapRenderer.setEditorMode(false);
        this.enemyMapRenderer.setEditorMode(false);
        this.enemyMapRenderer.setEnemyMap(true);
        this.enemyMapRenderer.addMapRendererListener(this);
        Game.getInstance().getNetworkManager().addNetworkListener(this);
    }

    public void reset() {
        this.winner = null;
        this.playerMapRenderer.setDisabled(false);
        this.enemyMapRenderer.setDisabled(false);
        this.setUpdatePaused(false);
    }

    @Override
    public void onAdded() {
        super.onAdded();
    }

    @Override
    public JPanel buildGui(GameWindow gameWindow) {
        this.uiPanel = new GamePanel(this.playerMapRenderer, this.enemyMapRenderer);

        uiPanel = uiPanel.create(new Dimension(512, 512));

        this.uiPanel.getBtnLoad().setEnabled(false);

        this.uiPanel.getBtnSave().addActionListener((e) -> {
            long id = System.currentTimeMillis();
            Savegame savegame = new Savegame(this.playerMap, this.enemyMap, this.playerTurn, String.valueOf(id));
            Game.getInstance().getGameFileHandler().saveSavegame(savegame);
            Game.getInstance().getNetworkManager().sendSave(id);
        });

        this.uiPanel.getBtnExit().addActionListener((e) -> {
            Game.getInstance().getNetworkManager().stopServer();
            Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
        });

        changeTurnColors();

        return uiPanel;
    }

    private float waitTimer = 0;

    @Override
    public void update(double deltaTime) {
        this.enemyMapRenderer.setDisabled(!gameStarted);

        if(gameStarted && this.mapData != null) {
            if (this.playerMap.allShipsDestroyed() || this.enemyShipsDestroyed == this.mapData.ShipsCount) {
                this.gameState = GameState.Finished;
            }

            if(this.waitTimer >= Game.getInstance().getTargetFps() * 1.2) {
                sendAiShot();
                this.waitTimer = 0;
            }

            this.waitTimer += deltaTime;
        }
    }

    @Override
    public void lateUpdate(double deltaTime) {
        if(this.gameState == GameState.Finished) {
            this.winner = this.playerTurn;
            GameOverScene gameOverScene = (GameOverScene)Game.getInstance().getSceneManager().setActiveScene(GameOverScene.class);
            gameOverScene.setWinner(this.winner);
            gameOverScene.initializeGameSession(null);

            this.setUpdatePaused(true);
            reset();
        }
    }

    @Override
    public void draw() {
        if (this.enemyMapRenderer != null && this.playerMapRenderer != null) {
            this.playerMapRenderer.draw();
            this.enemyMapRenderer.draw();
        }
    }

    public void setOtherTurn() {

        if(this.networkType == NetworkType.Host) {
            if(this.playerTurn == NetworkType.Host) {
                this.playerTurn = NetworkType.Client;
            }
            else  {
                this.playerTurn = NetworkType.Host;
            }
        } else {
            if(this.playerTurn == NetworkType.Client){
                this.playerTurn = NetworkType.Host;
            }
            else  {
                this.playerTurn = NetworkType.Client;
            }
        }

        changeTurnColors();

        this.uiPanel.invalidate();
        this.uiPanel.revalidate();
    }

    private void changeTurnColors() {
        if(isMyTurn()) {
            this.uiPanel.getPlayerLabelContainer().setBackground(UiBuilder.TURN_GREEN);
            this.uiPanel.getEnemyLabelContainer().setBackground(UiBuilder.NOTURN_RED);
        } else {
            this.uiPanel.getPlayerLabelContainer().setBackground(UiBuilder.NOTURN_RED);
            this.uiPanel.getEnemyLabelContainer().setBackground(UiBuilder.TURN_GREEN);
        }
    }

    private boolean isMyTurn() {
        return this.playerTurn == this.networkType;
    }

    @Override
    public void sizeUpdated() {
        this.uiPanel.updateMapSize(Game.getInstance().getWindow().getMapRenderPanelSize());

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
    public void keyReleased(KeyEvent keyEvent) {
    }

    @Override
    public void initializeGameSession(GameSessionData data) {
        this.gameSessionData = data;

        this.playerMapRenderer.setMap(data.getMap());
        this.playerMap = data.getMap();
        this.mapData = data.getMap().getMapData();

        this.enemyMap = new Map(data.getMapSize());
        this.enemyMapRenderer.setMap(this.enemyMap);

        this.playerAi = new AI(this.enemyMap, gameSessionData.getAiDifficulty());

        Game.getInstance().getWindow().repaint();
        Game.getInstance().getWindow().revalidate();
    }

    public void initializeSavegame(Savegame savegame) {
        this.playerMap = savegame.getPlayerMap();
        this.enemyMap = savegame.getEnemyMap();
        this.playerMapRenderer.setMap(this.playerMap);
        this.enemyMapRenderer.setMap(this.enemyMap);

        if(this.networkType == NetworkType.Client) {
            Game.getInstance().getLogger().info(this.networkType.toString() +  ": Initialized Savgame");
            Game.getInstance().getNetworkManager().confirmSession();
        }

        Game.getInstance().getWindow().repaint();
        Game.getInstance().getWindow().revalidate();
    }

    @Override
    public void OnPlayerConnected() {
    }

    @Override
    public void OnGameJoined(int mapSize) {
    }

    @Override
    public void OnGameStarted() {
        this.gameStarted = true;
        this.networkType = Game.getInstance().getNetworkManager().getNetworkType();
        this.gameState = GameState.Running;
    }

    @Override
    public void OnReceiveShot(Point pos) {
        if(this.playerMap != null) {
            if(this.playerMap.isInMap(pos)) {
                HitData hitData = this.playerMap.shot(pos);
                HitType hitType = hitData.getHitType();
                if(hitType == HitType.Water || hitType == HitType.NotPossible)
                    setOtherTurn();

                // fallback
                if(hitType == HitType.NotPossible)
                    hitType = HitType.Water;

                Game.getInstance().getNetworkManager().sendAnswer(hitType);
            }
        }
    }

    @Override
    public void OnReceiveAnswer(HitType type) {
        Game.getInstance().getLogger().info(this.networkType.toString() +  ": Ai getting answer: " + type);

        if(this.lastShot != null) {
            this.playerAi.receiveAnswer(type);
            this.enemyMap.markTile(this.lastShot, type);
            if(type == HitType.Water) {
                Game.getInstance().getNetworkManager().sendPass();
                setOtherTurn();
            }

            if(type == HitType.ShipDestroyed)
                this.enemyShipsDestroyed++;

            this.lastShot = null;
        }
    }

    @Override
    public void OnReceiveSave(String id) {
        Savegame savegame = new Savegame(this.playerMap, this.enemyMap, this.playerTurn, id);
        Game.getInstance().getGameFileHandler().saveSavegame(savegame);
        Game.getInstance().getNetworkManager().sendPass();
    }

    @Override
    public void OnReceiveLoad(String id) {
    }

    @Override
    public void OnReceivePass() {

    }

    @Override
    public void OnShipDropped(Map map, Ship ship, Point pos, boolean rotated) {
    }

    @Override
    public void OnShotFired(Map map, Point pos) {
    }

    public void sendAiShot() {
        if(isMyTurn() && gameStarted) {
            Point pos = this.playerAi.shot();
            if(this.enemyMap.isInMap(pos)) {
                if(this.enemyMap.getTile(pos).isFree()) {
                    Game.getInstance().getNetworkManager().sendShot(pos);
                    if(this.lastShot == null)
                        this.lastShot = pos;
                }
            }
        }
    }

    @Override
    public void OnRotated(Map map, Ship ship) {
    }
}