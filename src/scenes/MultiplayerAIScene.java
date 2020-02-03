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

/**
 * Scene for mutiplayer AI games.
 */
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
    private float waitTimer = 0;

    /**
     * Constructor for multiplayer AI scene.
     */
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

    /**
     * Handler for switch events.
     */
    @Override
    public void onSwitched() {
        super.onSwitched();

        this.winner = null;
        this.lastShot = null;
        this.playerTurn = NetworkType.Client;
        this.playerMapRenderer.setDisabled(false);
        this.enemyMapRenderer.setDisabled(false);
        this.setUpdatePaused(false);
    }

    /**
     * Builds the gui.
     * @param gameWindow The game window.
     * @return a ready-made JPanel.
     */
    @Override
    public JPanel buildGui(GameWindow gameWindow) {
        this.uiPanel = new GamePanel(this.playerMapRenderer, this.enemyMapRenderer);

        uiPanel = uiPanel.create(new Dimension(512, 512));

        this.uiPanel.getBtnLoad().setEnabled(false);
        this.uiPanel.getBtnSave().setEnabled(false);

        this.uiPanel.getBtnSave().addActionListener((e) -> {
            long id = System.currentTimeMillis();
            MultiplayerAiSavegame savegame = new MultiplayerAiSavegame(String.valueOf(id), this.playerMap, this.enemyMap);
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

    /**
     * Updates the scene.
     * @param deltaTime The delta time.
     */
    @Override
    public void update(double deltaTime) {
        this.enemyMapRenderer.setDisabled(!gameStarted);

        if (this.enemyMapRenderer != null && this.playerMapRenderer != null) {
            this.playerMapRenderer.update(deltaTime);
            this.enemyMapRenderer.update(deltaTime);
        }

        if (gameStarted && this.mapData != null) {
            if (this.playerMap.allShipsDestroyed() || this.enemyShipsDestroyed == this.mapData.ShipsCount) {
                this.gameState = GameState.Finished;
            }

            if (this.waitTimer >= Game.getInstance().getTargetFps() * 1.2f) {
                sendAiShot();
                this.waitTimer = 0;
            }

            this.waitTimer += deltaTime;
        }
    }

    /**
     * Initilizes the game over scene once the game is over.
     * @param deltaTime The delta time.
     */
    @Override
    public void lateUpdate(double deltaTime) {
        if (this.gameState == GameState.Finished) {
            this.winner = this.playerTurn;
            GameOverScene gameOverScene = (GameOverScene) Game.getInstance().getSceneManager().setActiveScene(GameOverScene.class);
            gameOverScene.setWinner(this.winner);
            gameOverScene.initializeGameSession(null);

            this.setUpdatePaused(true);
        }
    }

    /**
     * Draws everything.
     */
    @Override
    public void draw() {
        if (this.enemyMapRenderer != null && this.playerMapRenderer != null) {
            this.playerMapRenderer.draw();
            this.enemyMapRenderer.draw();
        }
    }

    /**
     * Changes whose turn it is.
     */
    public void setOtherTurn() {

        if (this.networkType == NetworkType.Host) {
            if (this.playerTurn == NetworkType.Host) {
                this.playerTurn = NetworkType.Client;
            } else {
                this.playerTurn = NetworkType.Host;
            }
        } else {
            if (this.playerTurn == NetworkType.Client) {
                this.playerTurn = NetworkType.Host;
            } else {
                this.playerTurn = NetworkType.Client;
            }
        }

        changeTurnColors();

        this.uiPanel.invalidate();
        this.uiPanel.revalidate();
    }

    /**
     * Changes the colors of whose turn it is.
     */
    private void changeTurnColors() {
        if (isMyTurn()) {
            this.uiPanel.getPlayerLabelContainer().setBackground(UiBuilder.TURN_GREEN);
            this.uiPanel.getEnemyLabelContainer().setBackground(UiBuilder.NOTURN_RED);

        } else {
            this.uiPanel.getPlayerLabelContainer().setBackground(UiBuilder.NOTURN_RED);
            this.uiPanel.getEnemyLabelContainer().setBackground(UiBuilder.TURN_GREEN);
        }
    }

    /**
     * Checks if it's my turn.
     * @return Boolean if it is your turn.
     */
    private boolean isMyTurn() {
        return this.playerTurn == this.networkType;
    }

    /**
     * handler for size update.
     */
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

    /**
     * Initilizes the game session
     * @param data The game session data.
     */
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

    /**
     * Initilizes the samegame.
     * @param savegame The savegame to be initilized.
     */
    public void initializeSavegame(MultiplayerAiSavegame savegame) {
        this.playerMap = savegame.getPlayerMap();
        this.enemyMap = savegame.getEnemyMap();
        this.playerMapRenderer.setMap(this.playerMap);
        this.enemyMapRenderer.setMap(this.enemyMap);

        if (this.networkType == NetworkType.Client) {
            Game.getInstance().getLogger().info(this.networkType.toString() + ": Initialized Savgame");
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

    /**
     * Event handler for game start.
     */
    @Override
    public void OnGameStarted() {
        this.gameStarted = true;
        this.networkType = Game.getInstance().getNetworkManager().getNetworkType();
        this.gameState = GameState.Running;
    }

    /**
     * Event handler for when a shot has been received.
     * @param pos The shot position.
     */
    @Override
    public void OnReceiveShot(Point pos) {
        if (this.playerMap != null) {
            if (this.playerMap.isInMap(pos)) {
                HitData hitData = this.playerMap.shot(pos);
                HitType hitType = hitData.getHitType();
                if (hitType == HitType.Water || hitType == HitType.NotPossible)
                    setOtherTurn();

                // fallback
                if (hitType == HitType.NotPossible) {
                    hitType = HitType.Water;
                }

                Game.getInstance().getNetworkManager().sendAnswer(hitType);

                this.playerMapRenderer.playExplosion(pos);

                Game.getInstance().getSoundManager().handleHitSoundFx(hitType);
            }
        }
    }

    /**
     * Event handler for when an answer was received.
     * @param type The hit type.
     */
    @Override
    public void OnReceiveAnswer(HitType type) {
        Game.getInstance().getLogger().info(this.networkType.toString() + ": Ai getting answer: " + type);

        if (this.lastShot != null) {
            this.playerAi.receiveAnswer(type);
            this.enemyMap.markTile(this.lastShot, type);
            if (type == HitType.Water) {
                Game.getInstance().getNetworkManager().sendPass();
                setOtherTurn();
            }

            if (type == HitType.ShipDestroyed) {
                this.enemyShipsDestroyed++;
                this.enemyMap.mergeDummyShips(lastShot);
            }

            this.enemyMapRenderer.playExplosion(this.lastShot);

            this.lastShot = null;

            Game.getInstance().getSoundManager().handleHitSoundFx(type);
        }
    }

    /**
     * Event handler for when the message for saving has been received.
     * @param id The id of the savegame.
     */
    @Override
    public void OnReceiveSave(String id) {
        MultiplayerAiSavegame savegame = new MultiplayerAiSavegame(id, this.playerMap, this.enemyMap);
        Game.getInstance().getGameFileHandler().saveSavegame(savegame);
        Game.getInstance().getNetworkManager().sendPass();
    }

    @Override
    public void OnReceiveLoad(String id) {
    }

    /**
     * Event handler for when the match gets ended.
     */
    @Override
    public void OnGameClosed() {
        Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
    }

    @Override
    public void OnShipDropped(Map map, Ship ship, Point pos, boolean rotated) {
    }

    @Override
    public void OnShotFired(Map map, Point pos) {
    }

    /**
     * Sends an AI shot.
     */
    public void sendAiShot() {
        if (isMyTurn() && gameStarted) {
            Point pos = this.playerAi.shot();
            if (this.enemyMap.isInMap(pos)) {
                if (this.enemyMap.getTile(pos).isFree()) {
                    Game.getInstance().getNetworkManager().sendShot(pos);
                    if (this.lastShot == null)
                        this.lastShot = pos;
                }
            }
        }
    }

    @Override
    public void OnRotated(Map map, Ship ship) {
    }
}