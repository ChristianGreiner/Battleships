package game;

import game.ships.Ship;

import java.awt.*;
import java.io.Serializable;

public class MapTile implements Serializable {

    private Point pos;
    private Ship ship;
    private boolean hit;
    private boolean logicfree;
    private boolean neighbor;
    private boolean blocked;

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

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

    public void setlogicfree(boolean logicfree) {
        this.logicfree = logicfree;
    }

    public boolean getlogicfree(){ return this.logicfree;}

    public boolean hasShip() {
        return this.getShip() != null;
    }

    public boolean setHit(boolean hit) {
        this.hit = hit;
        if (hasShip())
            return ship.isDestroyed();

        return false;
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