package game.ships;

import game.Map;

/**
 * Placeholder ship part used in multiplayer games.
 */
public class DummyShip extends Ship {

    /**
     * Constructor for the dummy ship
     * @param map The {@link Map} the dummy ship will be placed in.
     */
    public DummyShip(Map map) {
        super(map, 1);
    }
}
