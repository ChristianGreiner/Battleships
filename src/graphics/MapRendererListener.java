package graphics;

import game.ships.Ship;

import java.awt.*;

public interface MapRendererListener {
    //rotated = false if it wasn't rotated after being picked up otherwise true
    void OnShipDropped(Ship ship, Point pos, boolean rotated);

    void OnShotFired(Point pos);

    void OnRotated(Ship ship);

}