package game.ships;

import game.Map;

/**
 * The carrier ship.
 */
public class Carrier extends Ship {

    /**
     * Constructor for the carrier ship
     * @param map The {@link Map} the carrier will be placed in.
     */
    public Carrier(Map map) {
        super(map, 5);
    }
}
