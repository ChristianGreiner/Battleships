package scenes;

import ai.SinglePlayerAI;
import core.*;
import game.Map;
import game.MapData;
import game.MapGenerator;
import game.MapTile;
import game.gamestates.SinglePlayerStates;
import graphics.MapRenderer;
import ui.GuiScene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.HashMap;

public class GameScene extends Scene implements Updatable, Drawable, KeyListener, GuiScene {



    private Map playerMap;
    private Map enemyMap;

    private MapRenderer playerMapRenderer;
    private SinglePlayerStates gameState = SinglePlayerStates.ShipsSelection;

    public GameScene() {
        super("GameScene");

        this.playerMap = new Map(10);
        this.enemyMap = new Map(10);
        this.playerMapRenderer = new MapRenderer(this.playerMap);
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

        this.playerMap.getShipsCounter().forEach((k,v) -> System.out.println("key: "+k+" value:"+v));
    }

    @Override
    void onAdded() {
        super.onAdded();

        // exmaple reading json file
        File file = new File(getClass().getClassLoader().getResource("mapdata.json").getFile());
        HashMap<Integer, MapData> configMap = new HashMap<>();
        try {
            MapData[] dat = Game.getInstance().getFileHandler().readMapConfig(file.getAbsolutePath());
            for (int i = 0; i < dat.length; i++) {
                configMap.put(dat[i].MapSize, dat[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        MapGenerator generator = new MapGenerator();
        this.playerMap = generator.generate(20, configMap.get(20));

        System.out.println(this.playerMap.getNumberOfShips());

        SinglePlayerAI ai = new SinglePlayerAI(1, this.playerMap);

        DrawMap();
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void draw() {

        // draw player map
        playerMapRenderer.draw();

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
        }
    }

    @Override
    public JPanel buildGui(GameWindow gameWindow) {

        JPanel panel = new JPanel();

        this.playerMapRenderer.setBackground(Color.black);
        this.playerMapRenderer.setLocation(0, 0);
        this.playerMapRenderer.setSize(360, 360);
        panel.add(this.playerMapRenderer);
        panel.setLayout(null);

        return panel;
    }
}
