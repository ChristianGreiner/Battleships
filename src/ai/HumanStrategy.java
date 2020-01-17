package ai;

import core.Alignment;
import core.Direction;
import core.Helper;
import game.HitType;
import game.Map;
import game.MapTile;

import java.awt.*;
import java.io.Serializable;

/**
 * In this Strategy, the AI will play mostly like human does by excluding
 * disadvantageous fields, sinking a detected ship.
 */

public class HumanStrategy implements AiStrategy, Serializable {

    private Alignment shipAlignment = null;
    private Point hitPoint;
    private Point startP; //margin points of hits
    private Point endP; //margin points of hits
    private Point newPoint;
    private Direction lastDirection;
    private boolean shipFocused;

    void setShipFocused(boolean shipFocused) {
        this.shipFocused = shipFocused;
    }

    @Override
    public void prepare(HitType type, Point lastHit) {

        if (!this.shipFocused) {
            if (type == HitType.Ship) { //ship
                this.shipFocused = true;
                this.hitPoint = lastHit;
                return;
            }
        } else if (this.lastDirection == Direction.Up) { //up
            if (type == HitType.Ship) { //ship
                this.startP = this.newPoint;

                if (!(this.hitPoint == null))
                    this.endP = this.hitPoint;

                this.hitPoint = null;
                this.shipAlignment = Alignment.Vertical;

            } else if (type == HitType.ShipDestroyed) {//ship destroyed
                this.startP = null;
                this.endP = null;
                this.shipAlignment = null;
                this.shipFocused = false;
                this.hitPoint = null;
            }
        } else if (this.lastDirection == Direction.Right) { //right
            if (type == HitType.Ship) { //ship
                if (!(this.hitPoint == null))
                    this.startP = this.hitPoint;

                this.hitPoint = null;
                this.endP = this.newPoint;
                this.shipAlignment = Alignment.Horizontal;

            } else if (type == HitType.ShipDestroyed) {//ship destroyed
                this.startP = null;
                this.endP = null;
                this.shipAlignment = null;
                this.shipFocused = false;
                this.hitPoint = null;
            }
        } else if (this.lastDirection == Direction.Down) { //down
            if (type == HitType.Ship) { //ship
                if (!(this.hitPoint == null))
                    this.startP = this.hitPoint;

                this.endP = this.newPoint;
                this.hitPoint = null;
                this.shipAlignment = Alignment.Vertical;
            } else if (type == HitType.ShipDestroyed) {//ship destroyed
                this.startP = null;
                this.endP = null;
                this.shipAlignment = null;
                this.shipFocused = false;
                this.hitPoint = null;
            }
        } else if (this.lastDirection == Direction.Left) { //left
            if (type == HitType.Ship) { //ship
                this.startP = this.newPoint;
                if (!(this.hitPoint == null))
                    this.endP = this.hitPoint;

                this.hitPoint = null;
                this.shipAlignment = Alignment.Horizontal;
            } else if (type == HitType.ShipDestroyed) { //ship destroyed
                this.startP = null;
                this.endP = null;
                this.shipAlignment = null;
                this.shipFocused = false;
                this.hitPoint = null;
            }
        }
        this.lastDirection = null;
    }

    @Override
    public Point process(Map map) {

        if (!this.shipFocused) {
            while (true) {
                Point trypoint = map.getRandomFreeTileIgnoreShip().getPos();
                if (map.fieldIsViable(trypoint)) {
                    return trypoint;
                }
            }
        }

        // Continue Hit
        Alignment align = Helper.getRandomAlignment();

        //System.out.println("SCHIFF GETROFFEN");

        //check, if alignment was set in last hit
        if (this.shipAlignment == Alignment.Horizontal || this.shipAlignment == Alignment.Vertical) {
            align = this.shipAlignment;
        }

        Direction dir = Helper.getRandomDirection(align);

        if (this.newPoint == this.hitPoint) {
            if (lastDirection == Direction.Left) {
                dir = Direction.Right;
            }
            if (lastDirection == Direction.Right) {
                dir = Direction.Left;
            }
            if (lastDirection == Direction.Up) {
                dir = Direction.Down;
            }
            if (lastDirection == Direction.Down) {
                dir = Direction.Up;
            }
        }

        if (align == Alignment.Vertical) { //vertical
            if (dir == Direction.Up) {//vertical up

                if (this.startP != null) {
                    this.newPoint = new Point(this.startP.x, this.startP.y - 1);
                } else this.newPoint = new Point(this.hitPoint.x, this.hitPoint.y - 1);

                MapTile newtile = map.getTile(this.newPoint);

                if (map.isInMap(this.newPoint)) {//check of legal point
                    this.lastDirection = Direction.Up;
                    if (newtile.isHit() || newtile.isBlocked()) {
                        if (newtile.hasShip()) {
                            this.hitPoint = newtile.getPos();
                            this.shipAlignment = Alignment.Vertical;
                            this.lastDirection = Direction.Up;
                        }
                        return process(map);
                    }
                } else {
                    return process(map);
                }
            } else if (dir == Direction.Down) { //vertical down

                if (this.endP != null) {
                    this.newPoint = new Point(this.endP.x, this.endP.y + 1);
                } else this.newPoint = new Point(this.hitPoint.x, this.hitPoint.y + 1);

                MapTile newtile = map.getTile(this.newPoint);

                if (map.isInMap(this.newPoint)) {//check of legal point
                    this.lastDirection = Direction.Down;
                    if (newtile.isHit() || newtile.isBlocked()) {
                        if (newtile.hasShip()) {
                            this.hitPoint = newtile.getPos();
                            this.shipAlignment = Alignment.Vertical;
                            this.lastDirection = Direction.Down;
                        }
                        return process(map);
                    }
                } else {
                    return process(map);
                }
            }
        } else if (align == Alignment.Horizontal) { //horizontal
            if (dir == Direction.Left) { //horizontal left

                if (this.startP != null) {
                    this.newPoint = new Point(this.startP.x - 1, this.startP.y);
                } else this.newPoint = new Point(this.hitPoint.x - 1, this.hitPoint.y);

                MapTile newtile = map.getTile(this.newPoint);

                if (map.isInMap(this.newPoint)) {//check of legal point
                    this.lastDirection = Direction.Left;
                    if (newtile.isHit() || newtile.isBlocked()) {
                        if (newtile.hasShip()) {
                            this.hitPoint = newtile.getPos();
                            this.shipAlignment = Alignment.Horizontal;
                            this.lastDirection = Direction.Left;
                        }
                        return process(map);
                    }
                } else {
                    return process(map);
                }

            } else if (dir == Direction.Right) { //horizontal right

                if (this.endP != null) {
                    this.newPoint = new Point(this.endP.x + 1, this.endP.y);
                } else this.newPoint = new Point(this.hitPoint.x + 1, this.hitPoint.y);

                MapTile newtile = map.getTile(this.newPoint);

                if (map.isInMap(this.newPoint)) {//check of legal point
                    this.lastDirection = Direction.Right;
                    if (newtile.isHit() || newtile.isBlocked()) {
                        if (newtile.hasShip()) {
                            this.hitPoint = newtile.getPos();
                            this.shipAlignment = Alignment.Horizontal;
                            this.lastDirection = Direction.Right;
                        }
                        return process(map);
                    }
                } else {
                    return process(map);
                }
            }
        }

        return this.newPoint;
    }
}
