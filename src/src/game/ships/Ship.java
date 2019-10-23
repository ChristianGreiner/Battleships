package game.ships;

import game.MapTile;

import java.awt.*;
import java.util.ArrayList;

public abstract class Ship {

    public int getId() {
        return Ship.id;
    }

    private static int id = 0;

    private boolean rotated;
    private ArrayList<MapTile> tiles = new ArrayList<>();
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

    public Ship(int space) {
        Ship.id++;
        this.space = space;
        this.position = null;
    }

    public ArrayList<MapTile> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<MapTile> tiles) {
        this.tiles = tiles;
    }

    public boolean isDestroyed() {
        for (int i = 0; i < this.tiles.size(); i++) {
            if (!this.tiles.get(i).isHit()) {
                return false;
            }
        }
        return true;
    }
}