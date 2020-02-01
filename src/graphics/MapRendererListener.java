package graphics;

import game.Map;
import game.ships.Ship;

import java.awt.*;

public interface MapRendererListener {

    /**
     * Triggers when a ship was dropped  on the map.
     * rotated = false if it wasn't rotated after being picked up otherwise true
     *
     * @param map     The map.
     * @param ship    The ship, that was dropped.
     * @param pos     The drop position.
     * @param rotated Whenever or not the ship is rotated.
     */
    void OnShipDropped(Map map, Ship ship, Point pos, boolean rotated);

    /**
     * Triggers when the map triggers a shot.
     *
     * @param map The map.
     * @param pos The position
     */
    void OnShotFired(Map map, Point pos);

    /**
     * Triggers when a ship was rotated.
     *
     * @param map  The map.
     * @param ship The ship that was rotated.
     */
    void OnRotated(Map map, Ship ship);
}