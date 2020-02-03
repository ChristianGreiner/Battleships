package ai;

import game.HitType;
import game.Map;

import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;

/**
 * In this Strategy, the AI will shoot after a certain grid, so that it can exclude even more
 * disadvantageous fields.
 */

public class HardStrategy implements AiStrategy, Serializable {

    private boolean raster = true;
    private boolean shipFocused;
    private int maxshotperline;
    private HashMap<Integer, Integer> columncounter = new HashMap<>();
    private HashMap<Integer, Integer> rowcounter = new HashMap<>();


    private HumanStrategy internstrategy = new HumanStrategy();

    /**
     * Constructor for the hard strategy.
     * @param map The map it will play on.
     */
    public HardStrategy(Map map) {
        this.maxshotperline = map.getSize() / 5;
        for (int i = 0; i < map.getSize(); i++) {
            this.columncounter.put(i, 0);
            this.rowcounter.put(i, 0);
        }
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public void prepare(HitType type, Point lastHit) {
        //rastercheck(lastHit);
        if (type == HitType.Ship) {
            this.shipFocused = true;
            this.internstrategy.prepare(type, lastHit);
            //this.internstrategy.setShipFocused(true);
        }
        if (type == HitType.ShipDestroyed) {
            this.shipFocused = false;
            this.internstrategy.prepare(type, lastHit);
        }
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public Point process(Map map) {
        Point trypoint = this.internstrategy.process(map);
        if (this.raster && !this.shipFocused) {
            trypoint = rastercheck(trypoint, map);
        }
        return trypoint;
    }

    /**
     * proves, if the selected rows and columns of the point have too many hits.
     * If they have, they will be excluded and a new point will be selected
     * by setNewRasterLocation to keep the grid.
     *
     * @param checkpoint selected point of process
     * @param map        the current map of the player in the game.
     * @return the selected point or a new point
     */
    private Point rastercheck(Point checkpoint, Map map) {
        if (this.columncounter.get(checkpoint.x) != null && this.rowcounter.get(checkpoint.y) != null) {
            if (this.columncounter.get(checkpoint.x) < this.maxshotperline && this.rowcounter.get(checkpoint.y) < this.maxshotperline) {
                this.columncounter.replace(checkpoint.x, this.columncounter.get(checkpoint.x) + 1);
                this.rowcounter.replace(checkpoint.y, this.rowcounter.get(checkpoint.y) + 1);
            } else checkpoint = setNewRasterLocation(checkpoint, map);
        } else checkpoint = setNewRasterLocation(checkpoint, map);


        for (int i = 0; i < map.getSize(); i++) {
            if (this.columncounter.get(i) != null) {
                if (this.columncounter.get(i) == this.maxshotperline) {
                    this.columncounter.remove(i);
                }
            }
            if (this.rowcounter.get(i) != null) {
                if (this.rowcounter.get(i) == this.maxshotperline) {
                    this.rowcounter.remove(i);
                }
            }
            if (this.columncounter.isEmpty()) {
                raster = false;
            }
        }

        return checkpoint;
    }

    /**
     * choose a new point in the grid or a new random point.
     *
     * @param checkpoint selected point from process
     * @param map        current map of player in the game.
     * @return the new point
     */

    private Point setNewRasterLocation(Point checkpoint, Map map) {
        boolean searching = true;
        Object[] columncounterkey = this.columncounter.keySet().toArray();
        Object[] rowcounterkey = this.rowcounter.keySet().toArray();
        int trymarkx = checkpoint.x;
        int trymarky = checkpoint.y;

        while (searching) {

            if (this.columncounter.get(checkpoint.x) == null) {
                int indexc = new Random().nextInt(columncounterkey.length);
                trymarkx = (int) columncounterkey[indexc];
            }

            if (this.rowcounter.get(checkpoint.y) == null) {
                int indexr = new Random().nextInt(rowcounterkey.length);
                trymarky = (int) rowcounterkey[indexr];
            }

            Point newcheckpoint = new Point(trymarkx, trymarky);

            if (map.fieldIsViable(newcheckpoint)) {
                searching = false;
                this.columncounter.replace(newcheckpoint.x, this.columncounter.get(newcheckpoint.x) + 1);
                this.rowcounter.replace(newcheckpoint.y, this.rowcounter.get(newcheckpoint.y) + 1);
                return newcheckpoint;
            } else {
                this.columncounter.replace(newcheckpoint.x, this.columncounter.get(newcheckpoint.x) + 1);
                this.rowcounter.replace(newcheckpoint.y, this.rowcounter.get(newcheckpoint.y) + 1);
                Point ranPoint = map.getRandomFreeTileIgnoreShip().getPos();
                if (map.fieldIsViable(ranPoint)) {
                    return ranPoint;
                }
            }
        }
        return checkpoint;
    }
}
