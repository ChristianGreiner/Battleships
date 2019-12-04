package graphics;

import game.ships.Ship;

import java.awt.*;

public interface MapRendererListener {

    void OnShipDropped(Ship ship, Point pos);

    void OnShotFired(Point pos);

    void OnRotated(Ship ship);

}