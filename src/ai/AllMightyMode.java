package ai;

import core.Helper;
import game.HitType;
import game.Map;

import java.awt.*;

public class AllMightyMode implements AiStrategy {

    private HumanStrategy internstrategy = new HumanStrategy();
    private boolean shipfocused;

    @Override
    public void prepare(HitType type, Point lastHit) {
        if (type == HitType.Ship){
            this.shipfocused = true;
            //this.internstrategy.setShipFocused(true);
            this.internstrategy.prepare(type, lastHit);
        }
        if (type == HitType.ShipDestroyed){
            this.shipfocused = false;
            this.internstrategy.prepare(type, lastHit);
            //this.internstrategy.setShipFocused(false);
        }
    }

    @Override
    public Point process(Map map) {
        if (!this.shipfocused){
            if (permitInfluencedHit()) {
                Point hitPoint;
                    do {
                        int hitMarkx = (int) (Math.random() * map.getSize());
                        int hitMarky = (int) (Math.random() * map.getSize());
                        hitPoint = new Point(hitMarkx, hitMarky);
                    }
                    while (map.getShip(hitPoint) == null || map.getTile(hitPoint).isHit() || map.getTile(hitPoint).isBlocked());
                    return hitPoint;
            }
        }
        return this.internstrategy.process(map);
    }


    private boolean permitInfluencedHit() {
        int number = (int) (Math.random() * 10);
        return number <= 2;
    }
}
