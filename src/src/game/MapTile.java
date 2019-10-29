package game;

import game.ships.Ship;

import java.awt.*;

public class MapTile {

    private Point pos;
    private Ship ship;
    private boolean hit;

    private boolean neighbor;

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

    public boolean blocked = false;

    public boolean isHit() {
        return hit;
    }

    public boolean hasShip() {
        return this.getShip() != null;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public Point getPos() {
        return pos;
    }

    public void setShip(Ship ship) {
        this.setBlocked(ship != null);
        this.ship = ship;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }
}
