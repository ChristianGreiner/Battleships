package game.ships;

import game.Map;

/**
 * The battleship
 */
public class Battleship extends Ship {

    /**
     * Constructor for the battleship
     * @param map {@link Map} the ship will be placed in.
     */
    public Battleship(Map map) {
        super(map, 4);
    }
}
