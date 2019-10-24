package scenes;

import core.Drawable;
import core.Renderer;
import core.Updatable;
import game.Map;
import game.MapTile;
import game.gamestates.SinglePlayerStates;
import game.ships.Carrier;
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
        Carrier ship = new Carrier();
        //this.playerMap.insert(ship, new Point(0, 1), false);

        // create second ship
        Destroyer ship2 = new Destroyer();
        //this.playerMap.insert(ship2, new Point(4, 9), true);

        Carrier ship3 = new Carrier();
        this.playerMap.insert(ship3, new Point(0, 0), false);

        // create second ship
        Destroyer ship4 = new Destroyer();
        this.playerMap.insert(ship4, new Point(2, 5), true);

        Destroyer ship5 = new Destroyer();
        this.playerMap.insert(ship5, new Point(6, 3), false);

        /*this.playerMap.shot(new Point(0, 1));
        this.playerMap.shot(new Point(0, 2));
        this.playerMap.shot(new Point(0, 3));
        this.playerMap.shot(new Point(0, 4));
        this.playerMap.shot(new Point(0, 5));*/


        System.out.println("Ship destoryed: " + ship.isDestroyed());

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

        // exmaple reading json file
        /*File file = new File(getClass().getClassLoader().getResource("mapdata.json").getFile());
        try {
            HashMap<Integer, MapData> configMap = new HashMap<>();
            MapData[] dat = JsonReader.readJson(file.getAbsolutePath());
            for (int i = 0; i < dat.length; i++) {
                configMap.put(dat[i].MapSize, dat[i]);
            }
            System.out.print(configMap.get(10).Battleships);
        } catch (Exception e) {
            e.printStackTrace();
        }*/


        // exmaple playing audio
        /*File file = new File(getClass().getClassLoader().getResource("hit.wav").getFile());
        Clip clip;
        try (AudioInputStream audioIn = AudioSystem.getAudioInputStream(file.getAbsoluteFile())) {
            clip = null;
            try {
                clip = AudioSystem.getClip();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
            try {
                clip.open(audioIn);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void update(double deltaTime) {

        if (gameState == SinglePlayerStates.ShipsSelection) {
            this.gameState = SinglePlayerStates.BattleStart;
        }

    }

    @Override
    public void draw() {
    }
}
