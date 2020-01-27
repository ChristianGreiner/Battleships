package ai;

import core.Helper;
import game.HitType;
import game.Map;

import java.awt.*;
import java.io.Serializable;

/**
 * In this strategy, the AI will shoot at random, even if it locates a ship.
 * Only with a low probability, it will try to sink the ship.
 */

public class EasyStrategy implements AiStrategy, Serializable {

    //private boolean shipFocused;
    private boolean focusable;
    //private Point hitPoint;

    private HumanStrategy internstrategy = new HumanStrategy();

    private void setFocusable(boolean focusable) {
        if (!focusable) {
            this.focusable = false;
        } else if (Helper.randomBoolean() && Helper.randomBoolean()) {
            this.focusable = true;
        }
    }

    @Override
    public void prepare(HitType type, Point lastHit) {
        if (!focusable) {
            if (type == HitType.Ship) { //ship
                setFocusable(true);
                if (focusable)
                    this.internstrategy.prepare(type, lastHit);
            }
        } else if (focusable) {
            this.internstrategy.prepare(type, lastHit);
            if (type == HitType.ShipDestroyed) {
                //this.shipFocused = false;
                setFocusable(false);
            }
        }
    }

    @Override
    public Point process(Map map) {

        //if (this.shipFocused){
        if (this.focusable) {
            this.internstrategy.setShipFocused(focusable);
            return this.internstrategy.process(map);
        }
        //}
        return map.getRandomFreeTileIgnoreShip().getPos();
    }
}
