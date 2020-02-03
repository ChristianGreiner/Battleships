package game.ships;

import game.Map;

/**
 * The submarine ship.
 */
public class Submarine extends Ship {

    /**
     * Constructor for the submarine.
     * @param map The {@link Map} the ship will be placed in.
     */
    public Submarine(Map map) {
        super(map, 2);
    }
}
