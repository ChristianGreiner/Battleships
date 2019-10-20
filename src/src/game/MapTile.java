package game;

import game.ships.Ship;

import java.awt.*;

public class MapTile {

    private Point pos;
    private Ship ship;
    private boolean hit;

    public MapTile(Point pos) {
        this.pos = pos;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean shot) {
        this.hit = shot;
    }

    public Point getPos() {
        return pos;
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }
}
