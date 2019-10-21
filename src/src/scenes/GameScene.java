package scenes;

import core.Drawable;
import core.Renderer;
import core.Updatable;
import game.Map;
import game.ships.Carrier;

import java.awt.*;

public class GameScene extends Scene implements Updatable, Drawable {

    private Map playerMap;
    private Map enemyMap;

    private Renderer renderer = new Renderer();

    public GameScene() {
        super("GameScene");

        this.playerMap = new Map(10);
        this.enemyMap = new Map(10);

        // Create ship
        Carrier ship = new Carrier(new Point(0, 0));
        this.playerMap.insert(ship, new Point(0, 0), false);

        for (int i = 0; i < ship.getSpace(); i++) {
            System.out.println(ship.getTiles().get(i));
        }

        System.out.println("------");

        // rotate
        this.playerMap.rotateShip(ship);

        for (int i = 0; i < ship.getSpace(); i++) {
            System.out.println(ship.getTiles().get(i));
        }

    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void draw() {
    }
}
