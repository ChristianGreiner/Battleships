package ai;

import core.Helper;
import game.HitType;
import game.Map;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class TrollMode implements AiStrategy, Serializable {

    private boolean shipFocused;
    private Trolls randomstrategy;
    private HumanStrategy internstrategy = new HumanStrategy();

    private ArrayList<Point> freeFields = new ArrayList<>();
    private ArrayList<Point> shipFields = new ArrayList<>();

    TrollMode(Map map) {
        this.randomstrategy = Helper.getRandomTrollStrategy();
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
        if (this.randomstrategy == Trolls.NoMercyHit) {
            return noMercyHit(map);
        }
        return null;
    }

    private Point noMercyHit(Map map) {
        return Helper.getRandomFreeIndex(shipFields);
    }

}
