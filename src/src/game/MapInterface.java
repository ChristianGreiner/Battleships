package game;

import game.ships.Ship;

import java.awt.*;

public interface MapInterface {

    boolean insert(Ship ship, Point position, boolean rotated);

    Ship getShip(Point position);

    void remove(Ship ship);

    boolean rotate(Ship ship);

    boolean isInMap(Point position);
}
