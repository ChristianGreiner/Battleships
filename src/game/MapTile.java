package game;

import game.ships.Ship;

import java.awt.*;
import java.io.Serializable;

/**
 * A map tile that stores data about ships.
 */
public class MapTile implements Serializable {

    private Point pos;
    private Ship ship;
    private boolean hit;
    private boolean viable = true;
    private boolean neighbor;
    private boolean blocked;

    /**
     * The constructor of the map tile.
     *
     * @param pos
     */
    public MapTile(Point pos) {
        this.pos = pos;
    }

    /**
     * Whenever or not a map tile is blocked.
     *
     * @return
     */
    public boolean isBlocked() {
        return blocked;
    }

    /**
     * Sets the tile blocked.
     *
     * @param blocked If the tile is blocked.
     */
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    /**
     * Whenever or not the tile is not hitted or blocked.
     *
     * @return Whenever or not the tile is not hitted or blocked.
     */
    public boolean isFree() {
        return (!hit && !blocked && !hasShip());
    }

    /**
     * Whenever or not the tile is a neighbor field.
     *
     * @return Whenever or not the tile is a neighbor field.
     */
    public boolean isNeighbor() {
        return neighbor;
    }

    /**
     * Sets a field to a neighbor.
     *
     * @param neighbor Whenever or not the tile is a neighbor.
     */
    public void setNeighbor(boolean neighbor) {
        this.neighbor = neighbor;
    }

    /**
     * Gets the ship if the ship contains a ship.
     *
     * @return The ship.
     */
    public Ship getShip() {
        return ship;
    }

    /**
     * Sets a ship to the tile.
     *
     * @param ship The ship.
     */
    public void setShip(Ship ship) {
        this.ship = ship;
    }

    /**
     * Whenever or not the tile is hitted.
     *
     * @return Whenever or not the tile is hitted.
     */
    public boolean isHit() {
        return hit;
    }

    /**
     * Whenever or not the tile is viable. (used by the ai)
     *
     * @return The viablity of the field.
     */
    public boolean getViable() {
        return this.viable;
    }

    /**
     * Whenever or not the tile is viable.
     *
     * @param viable The viablity of the field.
     */
    public void setViable(boolean viable) {
        this.viable = viable;
    }

    /**
     * Whenever or not the tile has a ship.
     *
     * @return If the tile has a ship.
     */
    public boolean hasShip() {
        return this.getShip() != null;
    }

    /**
     * Whenever or not the tile is hitted.
     *
     * @param hit If the tile is hitted.
     * @return Returns true if the ship is destroyed.
     */
    public boolean setHit(boolean hit) {
        this.hit = hit;
        if (hasShip())
            return ship.isDestroyed();

        return false;
    }

    /**
     * Gets the position of the tile.
     *
     * @return
     */
    public Point getPos() {
        return pos;
    }

    /**
     * Resets the map tile.
     * The ship gets removed.
     */
    public void reset() {
        this.ship = null;
        this.neighbor = false;
        this.hit = false;
    }
}