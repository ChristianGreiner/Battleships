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

public class SinglePlayerScene extends Scene implements KeyListener, Updatable, Drawable, GuiScene, PassableDataScene {

    private Map playerMap;
    private Map aiMap;
    private PlayerType playerTurn = PlayerType.Player;
    private GameState gameState = GameState.Started;
    private MapRenderer playerMapRenderer;
    private SinglePlayerPanel uiPanel;
    private AI ai;

    public SinglePlayerScene() {
        super("SinglePlayer");
        this.playerMapRenderer = new MapRenderer(null);
    }

    @Override
    void onAdded() {
        super.onAdded();
        Game.getInstance().getSoundManager().playBackgroundMusic(Assets.Sounds.PLAYING_MUSIC);
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void draw() {
        this.playerMapRenderer.draw();
    }

    @Override
    public JPanel buildGui(GameWindow gameWindow) {
        SinglePlayerPanel singlePlayerPanel = new SinglePlayerPanel(this.playerMapRenderer);
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
            Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class, null);
        }

        if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                handleAiShot();
        }
    }

    private HitType lastHitType;

    private void handleAiShot() {

        //if(lastHitType != null)
          //  this.ai.receiveAnswer(lastHitType);

        Point point = this.ai.shot();

        HitData hitData = this.playerMap.shot2(point);

        lastHitType = hitData.getHitType();

        if(lastHitType != null)
            this.ai.receiveAnswer(lastHitType);

        DrawMap();

        if(point != null)
            System.out.println(point);
    }

    @Override
    public void onDataPassed(Object data) {
        Map map = (Map)data;

        MapGenerator generator = new MapGenerator();
        this.playerMap = generator.generate(map.getSize());

        this.playerMapRenderer.setMap(this.playerMap);

        this.ai = new AI(this.playerMap, AiDifficulty.Medium);


        DrawMap();
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
