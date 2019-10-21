package scenes;

import core.Drawable;
import core.Renderer;
import core.Updatable;
import game.Map;
import game.gamestates.SinglePlayerStates;
import game.ships.Carrier;

import java.awt.*;

public class GameScene extends Scene implements Updatable, Drawable {

    private Map playerMap;
    private Map enemyMap;

    private Renderer renderer = new Renderer();
    private SinglePlayerStates gameState = SinglePlayerStates.ShipsSelection;

    public GameScene() {
        super("GameScene");

        this.playerMap = new Map(20);
        this.enemyMap = new Map(20);

        // Create ship
        Carrier ship = new Carrier(new Point(0, 10));
        this.playerMap.insert(ship, new Point(0, 0), false);

        for (int i = 0; i < ship.getSpace(); i++) {
            System.out.println(ship.getTiles().get(i));
        }

        System.out.println("------");

        Carrier ship2 = new Carrier(new Point(0, 0));
        boolean ok = this.playerMap.insert(ship2, new Point(0, 0), false);

        boolean lol = this.playerMap.checkNeighborTiles(ship2);

        System.out.println("Insert 2: " + lol);

        if (ok) {
            for (int i = 0; i < ship2.getSpace(); i++) {
                System.out.println(ship2.getTiles().get(i));
            }
        }

    }

    @Override
    public void update(double deltaTime) {

        if (gameState == SinglePlayerStates.ShipsSelection) {
            // logik

            this.gameState = SinglePlayerStates.BattleStart;
        }

    }

    @Override
    public void draw() {
    }
}
