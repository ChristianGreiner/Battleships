package game;

import game.ships.Ship;

import java.awt.*;

/**
 * Container for storing data for hits.
 */
public class HitData {

    private Point position;
    private HitType type;
    private Ship ship;

    public HitData(Point position, Ship ship, HitType type) {
        this.position = position;
        this.ship = ship;
        this.type = type;
    }

    /**
     * Gets the ship if a ship was hitted.
     * @return The ship.
     */
    public Ship getShip() {
        return ship;
    }

    /**
     * Gets the position of the hit.
     * @return The position of the hit.
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Gets the type of hit.
     * @return The type of hit.
     */
    public HitType getHitType() {
        return type;
    }
}
