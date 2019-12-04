package ai;

import game.HitType;
import game.Map;

import java.awt.*;

public class AllMightyMode implements AiStrategy {

    private HumanStrategy internstrategy = new HumanStrategy();
    private boolean shipFocused;

    @Override
    public void prepare(HitType type, Point lastHit) {
        if (type == HitType.Ship) {
            this.shipFocused = true;
            //this.internstrategy.setShipFocused(true);
            this.internstrategy.prepare(type, lastHit);
        }
        if (type == HitType.ShipDestroyed) {
            this.shipFocused = false;
            this.internstrategy.prepare(type, lastHit);
            //this.internstrategy.setShipFocused(false);
        }
    }

    @Override
    public Point process(Map map) {
        System.out.println(Trolls.values().length);
        if (!this.shipFocused) {
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
