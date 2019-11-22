package ai;

import core.Alignment;
import core.Direction;
import core.Helper;
import game.HitType;
import game.Map;
import game.MapTile;

import java.awt.*;
import java.io.Serializable;

public class HumanStrategy implements AiStrategy, Serializable {

    private Alignment shipAlignment = null;
    private Point hitPoint;
    private Point startP; //margin points of hits left or up
    private Point endP;
    private Point newPoint;
    private Direction continuedDirection;
    private boolean shipFocused;

    @Override
    public Point prepair(HitType type, Point lastHit) {

        if (!this.shipFocused) {
            if (type == HitType.Ship) { //ship
                this.shipFocused = true;
                this.hitPoint = lastHit;
                return lastHit;
            }
        } else if (this.continuedDirection == Direction.Up) { //up
            if (type == HitType.Ship) { //ship

                this.startP = this.newPoint;

                if (!(this.hitPoint == null))
                    this.endP = this.hitPoint; //lasthit -> hitPoint

                this.hitPoint = null;
                this.shipAlignment = Alignment.Vertical;
                return lastHit;
            } else if (type == HitType.ShipDestroyed) {//ship destroyed
                this.startP = null;
                this.endP = null;
                this.shipAlignment = null;
                this.shipFocused = false;
                this.hitPoint = null;
            }
        } else if (this.continuedDirection == Direction.Right) { //right
            if (type == HitType.Ship) { //ship
                if (!(this.hitPoint == null))
                    this.startP = this.hitPoint;

                this.endP = this.newPoint;
                this.hitPoint = null;
                this.shipAlignment = Alignment.Horizontal;
                return lastHit;
            } else if (type == HitType.ShipDestroyed) {//ship destroyed
                this.startP = null;
                this.endP = null;
                this.shipAlignment = null;
                this.shipFocused = false;
                this.hitPoint = null;
            }
        } else if (this.continuedDirection == Direction.Down) { //down
            if (type == HitType.Ship) { //ship
                if (!(this.hitPoint == null))
                    this.startP = this.hitPoint;

                this.endP = this.newPoint;
                this.hitPoint = null;
                this.shipAlignment = Alignment.Vertical;
                return lastHit;
            } else if (type == HitType.ShipDestroyed) {//ship destroyed
                this.shipFocused = false;
                this.startP = null;
                this.endP = null;
                this.shipAlignment = null;
                this.hitPoint = null;
            }
        } else if (this.continuedDirection == Direction.Left) { //left
            if (type == HitType.Ship) { //ship
                this.startP = this.newPoint;
                if (!(this.hitPoint == null))
                    this.endP = this.hitPoint;

                this.hitPoint = null;
                this.shipAlignment = Alignment.Horizontal;
                return lastHit;
            } else if (type == HitType.ShipDestroyed) { //ship destroyed
                this.startP = null;
                this.endP = null;
                this.shipAlignment = null;
                this.shipFocused = false;
                this.hitPoint = null;
            }
        }
        return null;
    }

    @Override
    public Point process(Map map) {

        if (!this.shipFocused)
            return map.getRandomFreeTileIgnoreShip().getPos();

        // Continue Hit
        Alignment align = Helper.getRandomAlignment();

        //System.out.println("SCHIFF GETROFFEN");

        //check, if alignment was set in last hit
        if (this.shipAlignment == Alignment.Horizontal || this.shipAlignment == Alignment.Vertical) {
            align = this.shipAlignment;
        }

        Direction dir = Helper.getRandomDirection(align);

        if (align == Alignment.Vertical) { //vertical
            if (dir == Direction.Up) {//vertical up

                if (this.startP != null) {
                    this.newPoint = new Point(this.startP.x, this.startP.y - 1);
                } else this.newPoint = new Point(this.hitPoint.x, this.hitPoint.y - 1);

                MapTile newtile = map.getTile(this.newPoint);

                if (map.isInMap(this.newPoint)) {//check of legal point
                    if (!newtile.isHit() && !newtile.isBlocked()) {
                        this.continuedDirection = Direction.Up;
                    }
                    else {
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
                    if (!newtile.isHit() && !newtile.isBlocked()) {
                        this.continuedDirection = Direction.Down;
                    }
                    else {
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
                    if (!newtile.isHit() && !newtile.isBlocked()) {
                        this.continuedDirection = Direction.Left;
                    }
                    else {
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
                    if (!newtile.isHit() && !newtile.isBlocked()) {
                        this.continuedDirection = Direction.Right;
                    }
                    else {
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
