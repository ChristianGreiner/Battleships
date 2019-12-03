package ai;

import game.HitType;
import game.Map;

import java.awt.*;

public class FullRetardMode implements AiStrategy {

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
                    Point hitPoint;
                    do {
                        int hitMarkx = (int) (Math.random() * map.getSize());
                        int hitMarky = (int) (Math.random() * map.getSize());
                        hitPoint = new Point(hitMarkx, hitMarky);
                    }
                    while (map.getShip(hitPoint) != null);
                    return hitPoint;
                }
            return this.internstrategy.process(map);
        }

}