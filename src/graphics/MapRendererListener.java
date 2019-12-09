package graphics;

import game.Map;
import game.ships.Ship;

import java.awt.*;

public interface MapRendererListener {
    //rotated = false if it wasn't rotated after being picked up otherwise true
    void OnShipDropped(Map map, Ship ship, Point pos, boolean rotated);

    void OnShotFired(Map map, Point pos);

    void OnRotated(Map map, Ship ship);


}