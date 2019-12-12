package ai;

import core.Helper;
import game.HitType;
import game.Map;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class FullRetardMode implements AiStrategy, Serializable {

    private HumanStrategy internstrategy = new HumanStrategy();
    private ArrayList<Point> freeFields = new ArrayList<>();
    private ArrayList<Point> shipFields = new ArrayList<>();


    FullRetardMode(Map map) {
        for (int x = 0; x < map.getSize(); x++) {
            for (int y = 0; y < map.getSize(); y++) {
                Point p = new Point(x, y);
                if (!(map.getTile(p).hasShip())) {
                    this.freeFields.add(p);
                } else this.shipFields.add(p);
            }
        }
    }

    @Override
    public void prepare(HitType type, Point lastHit) {
        if (type == HitType.Ship) {
            //this.internstrategy.setShipFocused(true);
            this.internstrategy.prepare(type, lastHit);
            this.shipFields.remove(lastHit);
        }
        if (type == HitType.ShipDestroyed) {
            this.internstrategy.prepare(type, lastHit);
            //this.internstrategy.setShipFocused(false);
            this.shipFields.remove(lastHit);
        }
        if (type == HitType.Water) {
            //if ((!this.freeFields.isEmpty()))
            this.freeFields.remove(lastHit);
        }
    }

    @Override
    public Point process(Map map) {
        if (!this.freeFields.isEmpty()) {
            return Helper.getRandomFreeIndex(this.freeFields);
        }

        return Helper.getRandomFreeIndex(this.shipFields);
    }

}
