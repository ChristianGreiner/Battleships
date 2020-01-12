package scenes;

import ai.AI;
import ai.AiDifficulty;
import core.*;
import game.*;
import game.ships.Battleship;
import game.ships.Ship;
import graphics.MapRenderer;
import graphics.MapRendererListener;
import ui.GamePanel;
import ui.GuiScene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SingePlayeScene extends Scene implements KeyListener, MapRendererListener, Updatable, Drawable, GuiScene {

    private Map playerMap;
    private Map enemyMap;
    private PlayerType playerTurn = PlayerType.Player;
    private GameState gameState = GameState.Started;
    private MapRenderer playerMapRenderer;
    private MapRenderer enemyMapRenderer;
    private GamePanel uiPanel;
    private AI ai;
    private AiDifficulty difficulty = AiDifficulty.Easy;
    private int counter;
    private Battleship battleship;

    public SingePlayeScene() {
        super("SinglePlayer");

        this.playerMapRenderer = new MapRenderer(null);
        this.enemyMapRenderer = new MapRenderer(null);

        this.playerMapRenderer.addMapRendererListener(this);
        this.enemyMapRenderer.addMapRendererListener(this);
    }

    @Override
    public void onAdded() {
        super.onAdded();
    }

    public void initializeGame(Map map, AiDifficulty difficulty) {
        this.difficulty = difficulty;

        MapGenerator generator = new MapGenerator();

        this.playerMap = map;

        this.enemyMap = generator.generate(map.getSize());

        this.ai = new AI(this.playerMap, difficulty);

        this.playerMapRenderer.setMap(this.playerMap);
        this.playerMapRenderer.setEditorMode(false);

        this.enemyMapRenderer.setMap(this.enemyMap);
        this.enemyMapRenderer.setEditorMode(false);
        this.enemyMapRenderer.setEnemyMap(true);

        Dimension size = new Dimension(512, 512);

        if(this.playerMap.getSize() > 10) {
            size = new Dimension(620, 620);
        }

        this.uiPanel.updateMapSize(size);
        DrawMap();
    }

    public void initializeSavegame(Savegame savegame) {
        this.playerMap = savegame.getPlayerMap();
        this.enemyMap = savegame.getEnemyMap();
        this.ai = savegame.getAi();
        this.playerMapRenderer.setMap(this.playerMap);
    }

    private float waitTimer = 0;

    @Override
    public void update(double deltaTime) {

        if (this.playerMap == null) return;

        if (this.playerMap.allShipsDestoryed() || this.enemyMap.allShipsDestoryed()) {
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
            JOptionPane.showMessageDialog(Game.getInstance().getWindow(), "Game Ended!");
            this.playerMapRenderer.setDisabled(true);
            this.enemyMapRenderer.setDisabled(true);
            this.setUpdatePaused(true);
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

        this.uiPanel = singlePlayerPanel;

        return singlePlayerPanel;
    }

    @Override
    public void sizeUpdated() {
        //this.uiPanel.getPlayerMapRenderer().setPreferredSize(new Dimension(64, 64));
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

        if (keyEvent.getKeyCode() == KeyEvent.VK_S) {
            Savegame savegame = new Savegame(this.playerMap, this.enemyMap, this.playerTurn, this.difficulty, this.ai);
            Game.getInstance().getFileHandler().saveSavegame(savegame);
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

        //DrawMap();
        //counter++;
        this.playerMapRenderer.playExplosion(hitData.getPosition());

        if(hitData.getHitType() == HitType.Ship || hitData.getHitType() == HitType.ShipDestroyed) {
            this.playerTurn = PlayerType.AI;
        } else if (hitData.getHitType() == HitType.Water) {
            this.playerTurn = PlayerType.Player;
        }
    }

    private void DrawMap() {
        for (int y = 0; y < this.playerMap.getSize(); y++) {
            for (int x = 0; x < this.playerMap.getSize(); x++) {
                MapTile tile = this.playerMap.getTile(new Point(x, y));

                if (tile.hasShip()) {
                    if (tile.isHit()) {
                        System.out.print(ANSIColors.RED + "X" + ANSIColors.RESET + "|");
                    } else {
                        System.out.print(ANSIColors.YELLOW + "X" + ANSIColors.RESET + "|");
                    }
                } else if (tile.isBlocked()) {
                    System.out.print(ANSIColors.GREEN + "X" + ANSIColors.RESET + "|");
                } else if (tile.isHit()) {
                    System.out.print(ANSIColors.BLUE + "X" + ANSIColors.RESET + "|");
                } else {
                    System.out.print(ANSIColors.BLUE + " " + ANSIColors.RESET + "|");
                }
            }
            System.out.print("\n" + ANSIColors.RESET);
        }
        System.out.println("-------------------------");
    }

    @Override
    public void OnShipDropped(Map map, Ship ship, Point pos, boolean rotated) {
        ship.setRotated(rotated);
        map.move(ship, pos);
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
