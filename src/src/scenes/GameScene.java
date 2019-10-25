package scenes;

import core.Drawable;
import core.Renderer;
import core.SoundPlayer;
import core.Updatable;
import game.Map;
import game.MapTile;
import game.PlayerType;
import game.gamestates.SinglePlayerStates;
import game.ships.Destroyer;

import java.awt.*;

public class GameScene extends Scene implements Updatable, Drawable {

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

        // Create ship
        Destroyer ship2 = new Destroyer();
        this.playerMap.insert(ship2, new Point(0, 5), false);

        Destroyer ship = new Destroyer();
        this.playerMap.insert(ship, new Point(1, 9), true);


        PlayerType playerTurn = PlayerType.Player;

        for (int y = 0; y < this.playerMap.getSize(); y++) {
            for (int x = 0; x < this.playerMap.getSize(); x++) {
                MapTile tile = this.playerMap.getTile(new Point(x, y));

                if (tile.isHit()) {
                    System.out.print(ANSI_RED + "X");
                } else if (tile.hasShip()) {
                    if (tile.isHit()) {
                        System.out.print(ANSI_RED + "X");
                    } else {
                        System.out.print(ANSI_YELLOW + "X");
                    }
                } else {
                    System.out.print(ANSI_BLUE + "O");
                }
            }
            System.out.print("\n" + ANSI_RESET);
        }

        /*
        // exmaple reading json file
        File file = new File(getClass().getClassLoader().getResource("mapdata.json").getFile());
        HashMap<Integer, MapData> configMap = new HashMap<>();
        try {
            MapData[] dat = JsonReader.readJson(file.getAbsolutePath());
            for (int i = 0; i < dat.length; i++) {
                configMap.put(dat[i].MapSize, dat[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 20; i++) {
            System.out.println("-----------------------------------");
            MapGenerator generator = new MapGenerator();
            this.playerMap = generator.generate(20, configMap.get(20));


            for (int y = 0; y < this.playerMap.getSize(); y++) {
                for (int x = 0; x < this.playerMap.getSize(); x++) {
                    MapTile tile = this.playerMap.getTile(new Point(x, y));

                    if (tile.isHit()) {
                        System.out.print(ANSI_RED + "X");
                    } else if (tile.hasShip()) {
                        if (tile.isHit()) {
                            System.out.print(ANSI_RED + "X");
                        } else {
                            System.out.print(ANSI_YELLOW + "X");
                        }
                    } else {
                        System.out.print(ANSI_BLUE + "O");
                    }
                }
                System.out.print("\n" + ANSI_RESET);
            }
        }*/
    }

    private double wait = 0;
    private boolean played = false;

    private void shotEnemy(double delaTime) {
        SoundPlayer hitsound = new SoundPlayer("hit.wav");
        hitsound.play();

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
}
