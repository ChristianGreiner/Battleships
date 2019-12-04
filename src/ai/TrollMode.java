package ai;

import game.HitType;
import game.Map;
import core.Helper;

import java.awt.*;

public class TrollMode implements AiStrategy {

    private boolean shipFocused;

    private Trolls randomstrategy;
    private HumanStrategy internstrategy = new HumanStrategy();

    public TrollMode() {
        this.randomstrategy = Helper.getRandomTrollStrategy();
    }

    @Override
    public void prepare(HitType type, Point lastHit) {


    }

    @Override
    public Point process(Map map) {
        if (this.randomstrategy == Trolls.NoMercyHit) {
            return noMercyHit(map);
        }
        return null;
    }

    public Point noMercyHit(Map map) {
        System.out.println(Trolls.values().length);
        if (!this.shipFocused) {
            Point hitPoint;
            do {
                int hitMarkx = (int) (Math.random() * map.getSize());
                int hitMarky = (int) (Math.random() * map.getSize());
                hitPoint = new Point(hitMarkx, hitMarky);
            }
            while (map.getShip(hitPoint) == null || map.getTile(hitPoint).isHit() || map.getTile(hitPoint).isBlocked());
            return hitPoint;
        }
        return this.internstrategy.process(map);
    }

}
