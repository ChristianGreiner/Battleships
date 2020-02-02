package game.ships;

import game.Map;
import game.MapTile;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Abstract class for ships
 */
public abstract class Ship implements Serializable {

    private static int id = 0;

    private Map parentMap;
    private boolean rotated;
    private Point position;
    private int space;
    private ArrayList<MapTile> tiles = new ArrayList<>();
    private ArrayList<MapTile> neighborTiles = new ArrayList<>();

    /**
     * Constructor for ships.
     * @param parentMap The {@link Map} a ship will be in.
     * @param space The size of the ship.
     */
    public Ship(Map parentMap, int space) {
        Ship.id++;
        this.space = space;
        this.position = null;
        this.parentMap = parentMap;
    }

    /**
     * Get the ID of a ship.
     * @return the ID of a ship.
     */
    public int getId() {
        return Ship.id;
    }

    /**
     * Get the position of a ship.
     * @return the position of a ship.
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Set the position of a the ship.
     * @param position The position.
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * Get the space the ship takes up.
     * @return the space the ship takes up.
     */
    public int getSpace() {
        return space;
    }

    /**
     * Shows whether or not the ship is rotated.
     * @return the rotation of the ship.
     */
    public boolean isRotated() {
        return rotated;
    }

    /**
     * Sets the rotation of the ship.
     * @param rotated The rotation of the ship.
     */
    public void setRotated(boolean rotated) {
        this.rotated = rotated;
    }

    /**
     * Get the tiles the ship occupies.
     * @return The tiles the ship occupies.
     */
    public ArrayList<MapTile> getTiles() {
        return tiles;
    }

    /**
     * Get the tiles adjacent to the ship.
     * @return The adjacent tiles.
     */
    public ArrayList<MapTile> getNeighborTiles() {
        return neighborTiles;
    }

    /**
     * Set the adjacent tiles of the ship.
     * @param neighborTiles The adjacent tiles of the ship.
     */
    public void setNeighborTiles(ArrayList<MapTile> neighborTiles) {
        this.neighborTiles = neighborTiles;
    }

    /**
     * Whether or not the ship is destroyed.
     * @return Whether or not the ship is destroyed.
     */
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

    /**
     * Completely destroys a ship.
     */
    public void forceDestroy() {
        for (int i = 0; i < this.tiles.size(); i++) {
            this.tiles.get(i).setHit(true);
            this.neighborTiles.get(i).setBlocked(true);
        }
    }
}