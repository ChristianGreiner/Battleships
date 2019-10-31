package game;

import game.ships.Ship;

import java.awt.*;

public class MapTile {

    private Point pos;
    private Ship ship;

    public Ship getBelongsToShip() {
        return belongsToShip;
    }

    public void setBelongsToShip(Ship belongsToShip) {
        this.belongsToShip = belongsToShip;
    }

    private Ship belongsToShip;
    private boolean hit;
    private boolean neighbor;

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    private boolean blocked;

    public boolean isNeighbor() {
        return neighbor;
    }

    public void setNeighbor(boolean neighbor) {
        this.neighbor = neighbor;
    }

    public MapTile(Point pos) {
        this.pos = pos;
    }

    public Ship getShip() {
        return ship;
    }

    public boolean isHit() {
        return hit;
    }

    public boolean hasShip() {
        return this.getShip() != null;
    }

    public void setHit(boolean hit) {

        this.hit = hit;
        if (hasShip())
            ship.isDestroyed();
    }

    public Point getPos() {
        return pos;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }

    public boolean canPlace() {
        return !hasShip();
    }


    public void reset() {
        this.ship = null;
        this.neighbor = false;
        this.hit = false;
    }
}