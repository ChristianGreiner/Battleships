package game;

import game.ships.Ship;

import java.awt.*;

/**
 * A interface for maps.
 */
public interface MapInterface {

    int getSize();

    boolean insert(Ship ship, Point position, boolean rotated);

    HitData shot(Point pos);

    Ship getShip(Point position);

    void remove(Ship ship);

    boolean rotate(Ship ship);

    boolean isInMap(Point position);
}
