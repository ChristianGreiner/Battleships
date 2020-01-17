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

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SingePlayerScene extends Scene implements KeyListener, MapRendererListener, Updatable, Drawable, GuiScene, GameSession {

    private Map playerMap;
    private Map enemyMap;
    private PlayerType playerTurn = PlayerType.Player;
    private GameState gameState = GameState.Started;
    private MapRenderer playerMapRenderer;
    private MapRenderer enemyMapRenderer;
    private GamePanel uiPanel;
    private AI ai;
    private AiDifficulty difficulty = AiDifficulty.Easy;
    private boolean paused = false;
    private final float startSize = 512;
    private final Dimension windowStartSize;
    private PlayerType winner;

    public SingePlayerScene() {
        super("SinglePlayer");

        this.playerMapRenderer = new MapRenderer(null);
        this.enemyMapRenderer = new MapRenderer(null);

        this.playerMapRenderer.addMapRendererListener(this);
        this.enemyMapRenderer.addMapRendererListener(this);

        this.windowStartSize = Game.getInstance().getWindow().getSize();
    }

    public void reset() {
        this.winner = null;
        this.gameState = GameState.Started;
        this.playerMapRenderer.setDisabled(false);
        this.enemyMapRenderer.setDisabled(false);
        this.setUpdatePaused(false);
    }

    @Override
    public void onAdded() {
        super.onAdded();
    }

    public void initializeGameSession(GameSessionData data) {
        this.difficulty = data.getAiDifficulty();

        MapGenerator generator = new MapGenerator();

        this.playerMap = data.getMap();

        this.enemyMap = generator.generate(data.getMapSize());

        this.ai = new AI(this.playerMap, difficulty);

        this.playerMapRenderer.setMap(this.playerMap);
        this.playerMapRenderer.setEditorMode(false);

        this.enemyMapRenderer.setMap(this.enemyMap);
        this.enemyMapRenderer.setEditorMode(false);
        this.enemyMapRenderer.setEnemyMap(true);
        this.enemyMapRenderer.setShipsVisable(false);

        Dimension size = new Dimension(512, 512);

        if(this.playerMap.getSize() > 10) {
            size = new Dimension(620, 620);
        }

        //this.uiPanel.updateMapSize(size);
    }

    public void initializeSavegame(Savegame savegame) {
        this.playerMap = savegame.getPlayerMap();
        this.enemyMap = savegame.getEnemyMap();
        this.ai = savegame.getAi();
        this.playerMapRenderer.setMap(this.playerMap);
        this.enemyMapRenderer.setMap(this.enemyMap);
        this.playerTurn = savegame.getCurrentTurn();
    }

    private float waitTimer = 0;

    @Override
    public void update(double deltaTime) {

        if (this.playerMap == null || this.paused) return;

        if (this.playerMap.allShipsDestroyed() || this.enemyMap.allShipsDestroyed()) {

            if(this.playerMap.allShipsDestroyed())
                this.winner = PlayerType.AI;

            if(this.enemyMap.allShipsDestroyed())
                this.winner = PlayerType.Player;

            this.gameState = GameState.Finished;
        }
        else {
            if(this.playerTurn == PlayerType.AI) {
                if(this.waitTimer >= Game.getInstance().TARGET_FPS) {
                    handleAiShot();
                    this.waitTimer = 0;
                }
                this.waitTimer += deltaTime;
            }
        }
    }

    @Override
    public void lateUpdate(double deltaTime) {

        if (this.gameState == GameState.Finished) {
            //this.playerMapRenderer.setDisabled(true);
            //this.enemyMapRenderer.setDisabled(true);
            //this.setUpdatePaused(true);
            GameOverScene gameOverScene = (GameOverScene)Game.getInstance().getSceneManager().setActiveScene(GameOverScene.class);
            gameOverScene.setWinner(this.winner);
            this.setUpdatePaused(true);
            this.gameState = GameState.Started;
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
            Savegame savegame = Game.getInstance().getFileHandler().loadSavegame();
            if(savegame != null) {
                SingePlayerScene scene = (SingePlayerScene) Game.getInstance().getSceneManager().setActiveScene(SingePlayerScene.class);
                scene.initializeSavegame(savegame);
            }
        });

        singlePlayerPanel.getBtnSave().addActionListener((e) -> {
            Savegame savegame = new Savegame(this.playerMap, this.enemyMap, this.playerTurn, this.difficulty, this.ai);
            Game.getInstance().getFileHandler().saveSavegame(savegame);
        });

        this.uiPanel = singlePlayerPanel;

        return singlePlayerPanel;
    }

    @Override
    public void sizeUpdated() {
        Dimension currentSize = Game.getInstance().getWindow().getSize();

        float pixelPerSacel = 10;

        float w = (float)(this.windowStartSize.getWidth() / currentSize.getWidth());
        float h = (float)(this.windowStartSize.getHeight() / currentSize.getHeight());

        System.out.println("W: " + w + " H: " + h);

        Dimension currentMapSize = this.uiPanel.getPlayerMapRenderer().getSize();
        Dimension newMapSize = new Dimension((int)(512 + w * 100), (int)(512  + h * 100));

        System.out.println(newMapSize);

        this.uiPanel.updateMapSize(newMapSize);

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

    private void handleAiShot() {
        Point point = this.ai.shot();
        System.out.println(point);

        HitData hitData = this.playerMap.shot(point);

        HitType lastHitType = hitData.getHitType();

        if (lastHitType != null)
            this.ai.receiveAnswer(lastHitType);


        System.out.println(lastHitType);

        if (lastHitType == HitType.Water) {
            //Game.getInstance().getSoundManager().playSfx(Assets.Sounds.SHOT_WATER);
        } else if (lastHitType == HitType.Ship) {
            //Game.getInstance().getSoundManager().playSfx(Assets.Sounds.SHOT_SFX);
        }

        Game.getInstance().getSoundManager().playSfx(Assets.Sounds.SHOT_SFX);

        this.playerMapRenderer.playExplosion(hitData.getPosition());

        if(hitData.getHitType() == HitType.Ship || hitData.getHitType() == HitType.ShipDestroyed) {
            this.playerTurn = PlayerType.AI;
        } else if (hitData.getHitType() == HitType.Water) {
            this.playerTurn = PlayerType.Player;
        }
    }

    @Override
    public void OnShipDropped(Map map, Ship ship, Point pos, boolean rotated) {
    }

    @Override
    public void OnShotFired(Map map, Point pos) {
        if(this.playerTurn == PlayerType.Player) {
            HitData hitData = map.shot(pos);
            this.enemyMapRenderer.playExplosion(hitData.getPosition());
            Game.getInstance().getSoundManager().playSfx(Assets.Sounds.SHOT_SFX);
            System.out.println("Fired at" + pos);

            // set playerturn
            if(hitData.getHitType() == HitType.Ship || hitData.getHitType() == HitType.ShipDestroyed) {
                this.playerTurn = PlayerType.Player;
            } else if (hitData.getHitType() == HitType.Water) {
                this.playerTurn = PlayerType.AI;
                //this.handleAiShot();
            }
        }
    }

    @Override
    public void OnRotated(Map map, Ship ship) {
        map.rotate(ship);
    }
}
