package scenes;

import ai.AI;
import ai.AiDifficulty;
import core.*;
import game.*;
import graphics.MapRenderer;
import ui.GuiScene;
import ui.SinglePlayerPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SinglePlayerScene extends Scene implements KeyListener, Updatable, Drawable, GuiScene {

    private Map playerMap;
    private Map enemyMap;
    private PlayerType playerTurn = PlayerType.Player;
    private GameState gameState = GameState.Started;
    private MapRenderer playerMapRenderer;
    private MapRenderer enemyMapRenderer;
    private SinglePlayerPanel uiPanel;
    private AI ai;
    private AiDifficulty difficulty = AiDifficulty.Easy;

    public SinglePlayerScene() {
        super("SinglePlayer");

        this.playerMapRenderer = new MapRenderer(null);
        this.enemyMapRenderer = new MapRenderer(null);
    }

    @Override
    void onAdded() {
        super.onAdded();
        Game.getInstance().getSoundManager().playBackgroundMusic(Assets.Sounds.PLAYING_MUSIC, true);
    }

    public void initializeGame(int mapSize, AiDifficulty difficulty) {
        this.difficulty = difficulty;
        MapGenerator generator = new MapGenerator();

        this.playerMap = generator.generate(mapSize);
        this.enemyMap = new Map(mapSize);

        this.ai = new AI(this.playerMap, difficulty);

        this.playerMapRenderer.setMap(this.playerMap);
        this.enemyMapRenderer.setMap(this.enemyMap);

        DrawMap();
    }

    public void initializeSavegame(Savegame savegame) {
        this.playerMap = savegame.getPlayerMap();
        this.enemyMap = savegame.getEnemyMap();
        this.ai = savegame.getAi();
        this.playerMapRenderer.setMap(this.playerMap);
    }

    @Override
    public void update(double deltaTime) {
        if(this.playerMap == null) return;

        if(this.playerMap.getNumberOfShips() == this.playerMap.getNumberOfDestoryedShips()) {
            this.gameState = GameState.Finished;
        }

        if(this.gameState == GameState.Finished) {
            this.setUpdatePaused(true);
            JOptionPane.showMessageDialog(Game.getInstance().getWindow(), "Game Ended!");
        }
    }

    @Override
    public void draw()
    {
       if(this.enemyMapRenderer != null && this.playerMapRenderer != null) {
           this.playerMapRenderer.draw();
           this.enemyMapRenderer.draw();
       }
    }

    @Override
    public JPanel buildGui(GameWindow gameWindow) {
        SinglePlayerPanel singlePlayerPanel = new SinglePlayerPanel(this.playerMapRenderer, this.enemyMapRenderer);
        singlePlayerPanel = singlePlayerPanel.create(new Dimension(512, 512));

        this.uiPanel = singlePlayerPanel;

        return singlePlayerPanel;
    }

    @Override
    public void sizeUpdated() {
        this.uiPanel.getPlayerMapRenderer().setPreferredSize(new Dimension(64, 64));
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Game.getInstance().getSoundManager().stopBackgroundMusic();
            Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
        }

        if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
            handleAiShot();
        }

        if(keyEvent.getKeyCode() == KeyEvent.VK_S) {
            Savegame savegame = new Savegame(this.playerMap, this.enemyMap, this.playerTurn, AiDifficulty.Medium, this.ai);
            Game.getInstance().getFileHandler().saveSavegame(savegame);
        }

        if(keyEvent.getKeyCode() == KeyEvent.VK_D) {
            Map map = this.playerMap;
        }
    }

    private HitType lastHitType;

    private void handleAiShot() {

        Game.getInstance().getSoundManager().playSfx(Assets.Sounds.SHOT_SFX);

        Point point = this.ai.shot();

        HitData hitData = this.playerMap.shot2(point);

        lastHitType = hitData.getHitType();

        if(lastHitType != null)
            this.ai.receiveAnswer(lastHitType);

        DrawMap();

        this.playerMapRenderer.playExplosion(hitData.getPosition());

        if(point != null)
            System.out.println(point);
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
                } else if (tile.isHit()) {
                    System.out.print(ANSIColors.BLUE + "X" + ANSIColors.RESET + "|");
                } else {
                    System.out.print(ANSIColors.BLUE + " " + ANSIColors.RESET + "|");
                }
            }
            System.out.print("\n" + ANSIColors.RESET);
        }
        System.out.println("-------------------------");

        //this.playerMap.getShipsCounter().forEach((k, v) -> System.out.println("key: " + k + " value: " + v));
    }

}
