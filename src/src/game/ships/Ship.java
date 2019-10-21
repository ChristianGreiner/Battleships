package game.ships;

import java.awt.*;
import java.util.ArrayList;

public abstract class Ship {

    public int getId() {
        return Ship.id;
    }
    private static int id = 0;

    private boolean rotated;
    private ArrayList<Point> tiles = new ArrayList<>();
    private int space;

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    private Point position;

    public int getSpace() {
        return space;
    }

    public boolean isRotated() {
        return rotated;
    }

    public void setRotated(boolean rotated) {
        this.rotated = rotated;
    }

    public ArrayList<Point> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Point> tiles) {
        this.tiles = tiles;
    }

    public Ship(int space, Point position) {
        Ship.id++;
        this.space = space;
        this.position = position;
    }


}
