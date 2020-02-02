package game.ships;

import game.Map;
import game.MapTile;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Ship implements Serializable {

    private static int id = 0;

    private Map parentMap;
    private boolean rotated;
    private Point position;
    private int space;
    private ArrayList<MapTile> tiles = new ArrayList<>();
    private ArrayList<MapTile> neighborTiles = new ArrayList<>();

    public Ship(Map parentMap, int space) {
        Ship.id++;
        this.space = space;
        this.position = null;
        this.parentMap = parentMap;
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

        // its true, so block all neighbors
        for (int i = 0; i < this.neighborTiles.size(); i++) {
            this.neighborTiles.get(i).setBlocked(true);
        }

        this.parentMap.setOutOfShipLength();

        return true;
    }

    public void forceDestroy() {
        for (int i = 0; i < this.tiles.size(); i++) {
            this.tiles.get(i).setHit(true);
            this.neighborTiles.get(i).setBlocked(true);
        }
    }
}