package game.ships;

import game.Map;

/**
 * The destroyer ship.
 */
public class Destroyer extends Ship {

    /**
     * Constructor for the destroyer.
     * @param map  The {@link Map} the ship will be placed in.
     */
    public Destroyer(Map map) {
        super(map, 3);
    }
}
