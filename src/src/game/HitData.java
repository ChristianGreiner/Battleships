package game;

import game.ships.Ship;

import java.awt.*;

public class HitData {

    private Point position;
    private HitType type;
    private Ship ship;

    public HitData(Point position, Ship ship, HitType type) {
        this.position = position;
        this.ship = ship;
        this.type = type;
    }

    public Ship getShip() {
        return ship;
    }

    public Point getPosition() {
        return position;
    }

    public HitType getType() {
        return type;
    }
}