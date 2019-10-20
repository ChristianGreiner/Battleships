package game.ships;

import java.awt.*;
import java.util.ArrayList;

public abstract class Ship {

    public int getId() {
        return Ship.id;
    }
    private static int id = 0;

    private boolean flipped;
    private ArrayList<Point> tiles = new ArrayList<>();
    private int fields;

    public int getFields() {
        return fields;
    }

    public boolean isFlipped() {
        return flipped;
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    public ArrayList<Point> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Point> tiles) {
        this.tiles = tiles;
    }

    public Ship(int fields) {
        Ship.id++;
        this.fields = fields;
    }


}
