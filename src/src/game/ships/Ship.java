package game.ships;

import game.MapTile;

import java.awt.*;
import java.util.ArrayList;

public abstract class Ship {

    private static int id = 0;

    private boolean rotated;
    private ArrayList<MapTile> tiles = new ArrayList<>();

    private ArrayList<MapTile> neighborTiles = new ArrayList<>();
    private Point position;
    private int space;

    public Ship(int space) {
        Ship.id++;
        this.space = space;
        this.position = null;
    }

    public int getId() {
        return Ship.id;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public int getSpace() {
        return space;
    }

    public boolean isRotated() {
        return rotated;
    }

    public void setRotated(boolean rotated) {
        this.rotated = rotated;
    }

    public ArrayList<MapTile> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<MapTile> tiles) {
        this.tiles = tiles;
    }

    public ArrayList<MapTile> getNeighborTiles() {
        return neighborTiles;
    }

    public void setNeighborTiles(ArrayList<MapTile> neighborTiles) {
        this.neighborTiles = neighborTiles;
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