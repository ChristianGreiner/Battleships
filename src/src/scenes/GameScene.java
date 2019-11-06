package scenes;

import ai.SinglePlayerAI;
import core.*;
import game.Map;
import game.MapData;
import game.MapGenerator;
import game.MapTile;
import game.gamestates.SinglePlayerStates;
import io.JsonFileHandler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.HashMap;

public class GameScene extends Scene implements Updatable, Drawable, KeyListener {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private Map playerMap;
    private Map enemyMap;

    private Renderer renderer = new Renderer();
    private SinglePlayerStates gameState = SinglePlayerStates.ShipsSelection;

    public GameScene() {
        super("GameScene");

        this.playerMap = new Map(10);
        this.enemyMap = new Map(10);
    }

    private void DrawMap() {
        for (int y = 0; y < this.playerMap.getSize(); y++) {
            for (int x = 0; x < this.playerMap.getSize(); x++) {
                MapTile tile = this.playerMap.getTile(new Point(x, y));

                if (tile.hasShip()) {
                    if (tile.isHit()) {
                        System.out.print(ANSI_RED + "X" + ANSI_RESET + "|");
                    } else {
                        System.out.print(ANSI_YELLOW + "X" + ANSI_RESET + "|");
                    }
                } else if (tile.isHit()) {
                    System.out.print(ANSI_BLUE + "X" + ANSI_RESET + "|");
                } else {
                    System.out.print(ANSI_BLUE + " " + ANSI_RESET + "|");
                }
            }
            System.out.print("\n" + ANSI_RESET);
        }
        System.out.println("-------------------------");
    }

    @Override
    void onAdded() {
        super.onAdded();
        Game.getInstance().getWindow().addKeyListener(this);

        // exmaple reading json file
        File file = new File(getClass().getClassLoader().getResource("mapdata.json").getFile());
        HashMap<Integer, MapData> configMap = new HashMap<>();
        JsonFileHandler jsonFileHandler = new JsonFileHandler();
        try {
            MapData[] dat = jsonFileHandler.readMapConfig(file.getAbsolutePath());
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

        do {
            ai.shot();
        } while (this.playerMap.getNumberOfDestoryedShips() <= this.playerMap.getNumberOfShips());

        DrawMap();
    }

    private double wait = 0;
    private boolean played = false;

    private void shotEnemy(double delaTime) {
        SoundPlayer hitsound = new SoundPlayer("hit.wav");
        //hitsound.play();

        boolean hitSomething = this.playerMap.shot(new Point(1, 9));
        if (hitSomething)
            System.out.println("TREFFER! TRY AGAIN");
    }

    @Override
    public void update(double deltaTime) {

        if (!played) {
            shotEnemy(deltaTime);
            played = true;
        }


        if (gameState == SinglePlayerStates.ShipsSelection) {
            this.gameState = SinglePlayerStates.BattleStart;
        }

    }

    @Override
    public void draw() {
    }

    @Override
    public void keyTyped(KeyEvent e) {


    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        /*Point pos = this.ship2.getPosition();

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            this.playerMap.move(this.ship2, new Point(pos.x + 1, pos.y));
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            this.playerMap.move(this.ship2, new Point(pos.x - 1, pos.y));
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            this.playerMap.move(this.ship2, new Point(pos.x, pos.y - 1));
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            this.playerMap.move(this.ship2, new Point(pos.x, pos.y + 1));
        } else if (e.getKeyCode() == KeyEvent.VK_R) {
            this.playerMap.rotate(this.ship2);
        } else if (e.getKeyCode() == KeyEvent.VK_G) {
            this.playerMap.remove(this.ship2);
        }


        DrawMap();*/
    }
}
