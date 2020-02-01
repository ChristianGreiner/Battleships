package game;

import game.ships.Ship;

import java.awt.*;

/**
 * A interface for maps.
 */
public interface MapInterface {

    /**
     * Gets the size of the map.
     *
     * @return
     */
    int getSize();

    /**
     * Inserts a ship into the map.
     *
     * @param ship     The ship.
     * @param position The position of the ship.
     * @param rotated  Whenever or not the ship should be rotated.
     * @return Returns true if the ship could be inserted successfully.
     */
    boolean insert(Ship ship, Point position, boolean rotated);

    /**
     * Shots on a tile of the map.
     *
     * @param pos The position of the tile (begins by X: 0, Y: 0).
     * @return The hit data.
     */
    HitData shot(Point pos);

    /**
     * Gets a ship at given position.
     *
     * @param position The position of the tile.
     * @return Returns the ship if on this position is a ship.
     */
    Ship getShip(Point position);

    /**
     * Removes a ship from the map.
     *
     * @param ship The ship.
     */
    void remove(Ship ship);

    /**
     * Rotates a ship by 90°.
     *
     * @param ship The ship.
     * @return Whenever or not the ship could be rotated.
     */
    boolean rotate(Ship ship);

    /**
     * Whenever or not the given point is in the map.
     *
     * @param position The position of the point.
     * @return Whenever or not the given point is in the map.
     */
    boolean isInMap(Point position);
}
