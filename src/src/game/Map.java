package game;

import java.awt.*;


public class Map {

    private int[] ships;

    public Map(Point size) {
        this.ships = new int[size.x * size.y];

        for (int i = 0; i < this.ships.length; i++) {
            this.ships[i] = 0;
        }
    }
}
