package scenes;

import ai.AI;
import ai.AiDifficulty;
import core.Drawable;
import core.Game;
import core.GameWindow;
import core.Updatable;
import game.*;
import game.ships.Ship;
import graphics.MapRenderer;
import graphics.MapRendererListener;
import ui.GamePanel;
import ui.GuiScene;
import ui.UiBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SinglePlayerAIScene extends Scene implements KeyListener, MapRendererListener, Updatable, Drawable, GuiScene, GameSession {

    private Map playerMap;
    private Map enemyMap;
    private PlayerType playerTurn = PlayerType.Player;
    private GameState gameState = GameState.Started;
    private MapRenderer playerMapRenderer;
    private MapRenderer enemyMapRenderer;
    private GamePanel uiPanel;
    private AI enemyAi;
    private AI playerAi;
    private AiDifficulty difficulty = AiDifficulty.Easy;
    private boolean paused = false;
    private PlayerType winner;
    private GameSessionData gameSessionData;
    private float waitTimer = 0;

    public SinglePlayerAIScene() {
        super("SinglePlayerAIScene");

        this.playerMapRenderer = new MapRenderer(null);
        this.enemyMapRenderer = new MapRenderer(null);
    }

    public void reset() {
        this.winner = null;
        this.gameState = GameState.Started;
        this.playerMapRenderer.setDisabled(false);
        this.enemyMapRenderer.setDisabled(false);
        this.setUpdatePaused(false);
    }

    @Override
    public void onSwitched() {
        super.onSwitched();
    }

    public void initializeGameSession(GameSessionData data) {
        this.gameSessionData = data;
        this.difficulty = data.getAiDifficulty();

        MapGenerator generator = new MapGenerator();

        this.playerMap = data.getMap();

        this.enemyMap = generator.generate(data.getMapSize());

        this.enemyAi = new AI(this.playerMap, difficulty);
        this.playerAi = new AI(this.enemyMap, difficulty);

        this.playerMapRenderer.setMap(this.playerMap);
        this.playerMapRenderer.setEditorMode(false);

        this.enemyMapRenderer.setMap(this.enemyMap);
        this.enemyMapRenderer.setEditorMode(false);
        this.enemyMapRenderer.setEnemyMap(true);
        this.enemyMapRenderer.setShipsVisable(false);

        sizeUpdated();
    }

    public void initializeSavegame(SingleplayerAiSavegame savegame) {

        Game.getInstance().getSoundManager().playBackgroundMusic(Assets.Sounds.PLAYING_MUSIC, true);

        this.playerMap = savegame.getPlayerMap();
        this.enemyMap = savegame.getEnemyMap();
        this.enemyAi = savegame.getEnemyAi();
        this.playerAi = savegame.getPlayerAi();
        this.playerMapRenderer.setMap(this.playerMap);
        this.enemyMapRenderer.setMap(this.enemyMap);
        this.playerTurn = savegame.getCurrentTurn();
    }

    @Override
    public void update(double deltaTime) {

        if (this.playerMap == null || this.enemyMap == null || this.paused) return;

        if (this.enemyMapRenderer != null && this.playerMapRenderer != null) {
            this.playerMapRenderer.update(deltaTime);
            this.enemyMapRenderer.update(deltaTime);
        }

        if (this.playerMap.allShipsDestroyed() || this.enemyMap.allShipsDestroyed()) {

            if (this.playerMap.allShipsDestroyed()) {
                this.winner = PlayerType.AI;
            } else if (this.enemyMap.allShipsDestroyed()) {
                this.winner = PlayerType.Player;
            }

            this.gameState = GameState.Finished;
        } else {
            if (this.waitTimer >= Game.getInstance().getTargetFps() * Game.getInstance().getOptions().getAiSpeedValue()) {
                if (this.playerTurn == PlayerType.AI) {
                    handleAiShot(this.enemyAi, this.playerMap);
                } else {
                    handleAiShot(this.playerAi, this.enemyMap);
                }

                this.waitTimer = 0;
            }
            this.waitTimer += deltaTime;
        }
    }

    @Override
    public void lateUpdate(double deltaTime) {

        if (this.gameState == GameState.Finished) {
            GameOverScene gameOverScene = (GameOverScene) Game.getInstance().getSceneManager().setActiveScene(GameOverScene.class);
            gameOverScene.setWinner(this.winner);
            gameOverScene.initializeGameSession(this.gameSessionData);
            this.setUpdatePaused(true);
            this.reset();
        }
    }

    @Override
    public void draw() {
        if (this.enemyMapRenderer != null && this.playerMapRenderer != null) {
            this.playerMapRenderer.draw();
            this.enemyMapRenderer.draw();
        }
    }

    @Override
    public JPanel buildGui(GameWindow gameWindow) {
        GamePanel singlePlayerPanel = new GamePanel(this.playerMapRenderer, this.enemyMapRenderer);

        singlePlayerPanel = singlePlayerPanel.create(new Dimension(512, 512));

        singlePlayerPanel.getBtnExit().addActionListener((e) -> {
            Game.getInstance().getSoundManager().stopBackgroundMusic();
            Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
        });

        singlePlayerPanel.getBtnLoad().addActionListener((e) -> {
            try {
                SingleplayerAiSavegame savegame = (SingleplayerAiSavegame) Game.getInstance().getGameFileHandler().loadSavegame();
                if (savegame.getSavegameType() == SavegameType.SingeplayerAi) {
                    SinglePlayerAIScene scene = (SinglePlayerAIScene) Game.getInstance().getSceneManager().setActiveScene(SinglePlayerAIScene.class);
                    scene.initializeSavegame(savegame);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(Game.getInstance().getWindow(), "This save game isn't a valid singeplayer ai savegame.", "Can't load savegame.", JOptionPane.ERROR_MESSAGE);

            }
        });

        singlePlayerPanel.getBtnSave().addActionListener((e) -> {
            SingleplayerAiSavegame savegame = new SingleplayerAiSavegame(this.playerMap, this.enemyMap, this.playerTurn, this.enemyAi, this.playerAi, this.difficulty);
            Game.getInstance().getGameFileHandler().saveSavegameFileChooser(savegame);
        });

        this.uiPanel = singlePlayerPanel;

        this.uiPanel.getPlayerLabelContainer().setBackground(UiBuilder.TURN_GREEN);
        this.uiPanel.getEnemyLabelContainer().setBackground(UiBuilder.NOTURN_RED);

        return singlePlayerPanel;
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
        if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Game.getInstance().getSoundManager().stopBackgroundMusic();
            Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
        }
    }

    private void handleAiShot(AI ai, Map map) {
        Point point = ai.shot();

        HitData hitData = map.shot(point);

        HitType lastHitType = hitData.getHitType();

        if (lastHitType != null)
            ai.receiveAnswer(lastHitType);

        Game.getInstance().getSoundManager().handleHitSoundFx(hitData.getHitType());

        if (this.playerTurn == PlayerType.AI) {

            this.playerMapRenderer.playExplosion(hitData.getPosition());

            if (hitData.getHitType() == HitType.Ship || hitData.getHitType() == HitType.ShipDestroyed) {
                this.playerTurn = PlayerType.AI;
            } else if (hitData.getHitType() == HitType.Water) {
                this.playerTurn = PlayerType.Player;
            }

            this.uiPanel.getPlayerLabelContainer().setBackground(UiBuilder.TURN_GREEN);
            this.uiPanel.getEnemyLabelContainer().setBackground(UiBuilder.NOTURN_RED);

        } else {


            this.enemyMapRenderer.playExplosion(hitData.getPosition());

            if (hitData.getHitType() == HitType.Ship || hitData.getHitType() == HitType.ShipDestroyed) {
                this.playerTurn = PlayerType.Player;
            } else if (hitData.getHitType() == HitType.Water) {
                this.playerTurn = PlayerType.AI;
            }

            this.uiPanel.getPlayerLabelContainer().setBackground(UiBuilder.NOTURN_RED);
            this.uiPanel.getEnemyLabelContainer().setBackground(UiBuilder.TURN_GREEN);
        }
    }

    @Override
    public void OnShipDropped(Map map, Ship ship, Point pos, boolean rotated) {
    }

    @Override
    public void OnShotFired(Map map, Point pos) {
    }

    @Override
    public void OnRotated(Map map, Ship ship) {
        map.rotate(ship);
    }
}
