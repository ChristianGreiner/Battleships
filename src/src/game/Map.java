package game;

import core.Helper;
import game.ships.Ship;

import java.awt.*;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class Map implements MapInterface, Serializable {
    private MapTile[][] tiles;

    public HashMap<Type, Integer> getShipsCounter() {
        return shipsCounter;
    }

    private HashMap<Type, Integer> shipsCounter = new HashMap<>();
    private int size;

    public int getNumberOfShips() {
        return numberOfShips;
    }

    public int getNumberOfDestoryedShips() {
        return numberOfDestoryedShips;
    }

    private int numberOfShips;
    private int numberOfDestoryedShips;

    public int getSize() {
        return size;
    }

    public Map(int size) {
        this.size = size;
        this.tiles = new MapTile[size][size];

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                this.tiles[x][y] = new MapTile(new Point(x, y));
            }
        }
    }

    public MapTile getTile(Point pos) {
        if (isInMap(pos)) {
            return this.tiles[pos.x][pos.y];
        }
        return null;
    }

    public MapTile getRandomFreeTile() {
        while (true) {
            int rndX = Helper.randomNumber(0, this.getSize() - 1);
            int rndY = Helper.randomNumber(0, this.getSize() - 1);
            if (!getTile(new Point(rndX, rndY)).hasShip()) {
                return getTile(new Point(rndX, rndY));
            }
        }
    }

    public boolean insert(Ship ship, Point position, boolean rotated) {

        if (!canInsertShip(ship, position, rotated))
            return false;

        this.numberOfShips++;
        // all checks passed!
        ship.setPosition(position);
        ship.setRotated(rotated);
        ship.setNeighborTiles(getNeighborTiles(ship));

        if (rotated) {
            // check horizontal
            if (position.x + ship.getSpace() <= this.getSize()) {
                for (int i = 0; i < ship.getSpace(); i++) {
                    this.tiles[position.x + i][position.y].setShip(ship);
                    ship.getTiles().add(this.tiles[position.x + i][position.y]);
                }
            }
        } else {
            // check vertical
            if (position.y + ship.getSpace() <= this.getSize()) {
                for (int i = 0; i < ship.getSpace(); i++) {
                    this.tiles[position.x][position.y + 1].setShip(ship);
                    ship.getTiles().add(this.tiles[position.x][position.y + i]);
                }
            }
        }

        this.computeShipCountAdd(ship);

        return true;
    }

    private void computeShipCountAdd(Ship ship) {
        int counter = 0;
        if(this.shipsCounter.containsKey(ship.getClass()))
            counter = this.shipsCounter.get(ship.getClass());

        counter++;
        this.shipsCounter.put(ship.getClass(), counter);
    }

    private ArrayList<MapTile> getNeighborTiles(Ship ship) {

        ArrayList<MapTile> tiles = new ArrayList<>();

        for (int i = 0; i < ship.getSpace(); i++) {

            if (ship.isRotated()) {

                Point pos = new Point(ship.getPosition().x + i, ship.getPosition().y);

                // check first tile (LEFT, TOP, DOWN)
                if (i == 0) {

                    // check top
                    if (pos.y > 0) {
                        AddNeighborTiles(tiles, ship, pos.x, pos.y - 1);
                    }

                    // check bottom
                    if (pos.y < getSize() - 1) {
                        AddNeighborTiles(tiles, ship, pos.x, pos.y + 1);
                    }

                    // check left
                    if (pos.x > 0) {
                        AddNeighborTiles(tiles, ship, pos.x - 1, pos.y);
                    }

                    // check left bottom
                    if (pos.x > 0 && pos.y < this.getSize() - 1) {
                        AddNeighborTiles(tiles, ship, pos.x - 1, pos.y + 1);
                    }

                    // check left top
                    if (pos.x > 0 && pos.y > 0 && pos.y < this.getSize()) {
                        AddNeighborTiles(tiles, ship, pos.x - 1, pos.y - 1);
                    }
                } else if (i == ship.getSpace() - 1) {

                    // check top
                    if (pos.x > 0 && pos.x < this.getSize() - 1 && pos.y > 0 && pos.y < this.getSize() - 1) {
                        AddNeighborTiles(tiles, ship, pos.x, pos.y - 1);
                    }

                    // check bottom
                    if (pos.y < getSize() - 1) {
                        AddNeighborTiles(tiles, ship, pos.x, pos.y + 1);
                    }

                    // check right
                    if (pos.x < this.getSize() - 1) {
                        AddNeighborTiles(tiles, ship, pos.x + 1, pos.y);
                    }

                    // check top right
                    if (pos.x < this.getSize() - 1 && pos.y > 0) {
                        AddNeighborTiles(tiles, ship, pos.x + 1, pos.y - 1);
                    }

                    // check right bottom
                    if (pos.x > 0 && pos.x < this.getSize() - 1 && pos.y > 0 && pos.y < this.getSize() - 1) {
                        AddNeighborTiles(tiles, ship, pos.x + 1, pos.y + 1);
                    }
                } else {
                    // check top
                    if (pos.x > 0 && pos.x < this.getSize() - 1 && pos.y > 0 && pos.y < this.getSize() - 1) {
                        AddNeighborTiles(tiles, ship, pos.x, pos.y - 1);
                    }

                    // check bottom
                    if (pos.y < getSize() - 1) {
                        AddNeighborTiles(tiles, ship, pos.x, pos.y + 1);
                    }

                }
            } else {

                Point pos = new Point(ship.getPosition().x, ship.getPosition().y + i);

                // first tile
                if (i == 0) {
                    // check top
                    if (pos.y > 0) {
                        AddNeighborTiles(tiles, ship, pos.x, pos.y - 1);
                    }

                    // check left
                    if (pos.x > 0) {
                        AddNeighborTiles(tiles, ship, pos.x - 1, pos.y);
                    }

                    // check right
                    if (pos.x < this.getSize() - 1) {
                        AddNeighborTiles(tiles, ship, pos.x + 1, pos.y);
                    }

                    // check top left
                    if (pos.x > 0 && pos.y > 0) {
                        AddNeighborTiles(tiles, ship, pos.x - 1, pos.y - 1);
                    }

                    // check top right
                    if (pos.x < this.getSize() - 1 && pos.y > 0) {
                        AddNeighborTiles(tiles, ship, pos.x + 1, pos.y - 1);
                    }
                }
                // check last tile
                else if (i == ship.getSpace() - 1) {

                    // check left
                    if (pos.x > 0) {
                        AddNeighborTiles(tiles, ship, pos.x - 1, pos.y);
                    }

                    // check right
                    if (pos.x < this.getSize() - 1) {
                        AddNeighborTiles(tiles, ship, pos.x + 1, pos.y);
                    }

                    // check bottom
                    if (pos.y < this.getSize() - 1) {
                        AddNeighborTiles(tiles, ship, pos.x, pos.y + 1);
                    }

                    // check left bottom
                    if (pos.x > 0 && pos.y < this.getSize() - 1) {
                        AddNeighborTiles(tiles, ship, pos.x - 1, pos.y + 1);
                    }

                    // check right bottom
                    if (pos.x < this.getSize() - 1 && pos.y < this.getSize() - 1) {
                        AddNeighborTiles(tiles, ship, pos.x + 1, pos.y + 1);
                    }
                } else {
                    // check left
                    if (pos.x > 0) {
                        AddNeighborTiles(tiles, ship, pos.x - 1, pos.y);
                    }

                    // check right
                    if (pos.x < this.getSize() - 1) {
                        AddNeighborTiles(tiles, ship, pos.x + 1, pos.y);
                    }
                }
            }
        }

        return tiles;
    }

    private void AddNeighborTiles(ArrayList<MapTile> tiles, Ship ship, int x, int y) {
        MapTile t = this.tiles[x][y];
        t.setNeighbor(true);
        tiles.add(t);
    }

    private boolean areTilesEmpty(Point startPos, int size, boolean rotated) {

        for (int i = 0; i < size; i++) {

            Point nextPos = new Point(startPos.x, startPos.y + i);
            if (rotated) {
                nextPos = new Point(startPos.x + i, startPos.y);
            }

            if (!isInMap(nextPos))
                return false;

            if (getTile(nextPos).hasShip()) {
                return false;
            }
        }

        return true;
    }

    private boolean hasFreeNeighborTiles(Point position, int space, boolean rotated) {

        if (!isInMap(position))
            return false;

        for (int i = 0; i < space; i++) {
            if (rotated) {

                Point pos = new Point(position.x + i, position.y);

                if (!isInMap(pos))
                    return false;

                // check first tile (LEFT, TOP, DOWN)
                if (i == 0) {

                    // check top
                    if (pos.y > 0) {
                        if (this.tiles[pos.x][pos.y - 1].hasShip())
                            return false;
                    }

                    // check bottom
                    if (pos.y < getSize() - 1) {
                        if (this.tiles[pos.x][pos.y + 1].hasShip())
                            return false;
                    }

                    // check left
                    if (pos.x > 0) {
                        if (this.tiles[pos.x - 1][pos.y].hasShip())
                            return false;
                    }

                    // check left bottom
                    if (pos.x > 0 && pos.y < this.getSize() - 1) {
                        if (this.tiles[pos.x - 1][pos.y + 1].hasShip())
                            return false;
                    }

                    // check left top
                    if (pos.x > 0 && pos.y > 0 && pos.y < this.getSize()) {
                        if (this.tiles[pos.x - 1][pos.y - 1].hasShip())
                            return false;
                    }
                } else if (i == space - 1) {

                    // check top
                    if (pos.x > 0 && pos.x < this.getSize() - 1 && pos.y > 0 && pos.y < this.getSize() - 1) {
                        if (this.tiles[pos.x][pos.y - 1].hasShip())
                            return false;
                    }

                    // check bottom
                    if (pos.y < getSize() - 1) {
                        if (this.tiles[pos.x][pos.y + 1].hasShip())
                            return false;
                    }

                    // check right
                    if (pos.x < this.getSize() - 1) {
                        if (this.tiles[pos.x + 1][pos.y].hasShip())
                            return false;
                    }

                    // check top right
                    if (pos.x < this.getSize() - 1 && pos.y > 0) {
                        if (this.tiles[pos.x + 1][pos.y - 1].hasShip())
                            return false;
                    }

                    // check right bottom
                    if (pos.x > 0 && pos.x < this.getSize() - 1 && pos.y > 0 && pos.y < this.getSize() - 1) {
                        if (this.tiles[pos.x + 1][pos.y + 1].hasShip())
                            return false;
                    }
                } else {
                    // check top
                    if (pos.x > 0 && pos.x < this.getSize() - 1 && pos.y > 0 && pos.y < this.getSize() - 1) {
                        if (this.tiles[pos.x][pos.y - 1].hasShip())
                            return false;
                    }

                    // check bottom
                    if (pos.y < getSize() - 1) {
                        if (this.tiles[pos.x][pos.y + 1].hasShip())
                            return false;
                    }

                }
            } else {
                Point pos = new Point(position.x, position.y + i);

                if (!isInMap(pos))
                    return false;

                // Check first tile
                if (i == 0) {
                    // check top
                    if (pos.y > 0) {
                        if (this.tiles[pos.x][pos.y - 1].hasShip())
                            return false;
                    }

                    // check left
                    if (pos.x > 0) {
                        if (this.tiles[pos.x - 1][pos.y].hasShip()) {
                            return false;
                        }
                    }

                    // check right
                    if (pos.x < this.getSize() - 1) {
                        if (this.tiles[pos.x + 1][pos.y].hasShip()) {
                            return false;
                        }
                    }

                    // check top left
                    if (pos.x > 0 && pos.y > 0) {
                        if (this.tiles[pos.x - 1][pos.y - 1].hasShip()) {
                            return false;
                        }
                    }

                    // check top right
                    if (pos.x < this.getSize() - 1 && pos.y > 0) {
                        if (this.tiles[pos.x + 1][pos.y - 1].hasShip()) {
                            return false;
                        }
                    }

                }
                // check last tile
                else if (i == space - 1) {
                    // check left
                    if (pos.x > 0) {
                        if (this.tiles[pos.x - 1][pos.y].hasShip()) {
                            return false;
                        }
                    }

                    // check right
                    if (pos.x < this.getSize() - 1) {
                        if (this.tiles[pos.x + 1][pos.y].hasShip()) {
                            return false;
                        }
                    }

                    // check bottom
                    if (pos.y < this.getSize() - 1) {
                        if (this.tiles[pos.x][pos.y + 1].hasShip())
                            return false;
                    }

                    // check left bottom
                    if (pos.x > 0 && pos.y < this.getSize() - 1) {
                        if (this.tiles[pos.x - 1][pos.y + 1].hasShip())
                            return false;
                    }

                    // check right bottom
                    if (pos.x < this.getSize() - 1 && pos.y < this.getSize() - 1) {
                        if (this.tiles[pos.x + 1][pos.y + 1].hasShip())
                            return false;
                    }
                } else {
                    // check left
                    if (pos.x > 0) {
                        if (this.tiles[pos.x - 1][pos.y].hasShip())
                            return false;
                    }

                    // check right
                    if (pos.x < this.getSize() - 1) {
                        if (this.tiles[pos.x + 1][pos.y].hasShip())
                            return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Gets a ship by given position.
     *
     * @param position
     * @return
     */
    public Ship getShip(Point position) {
        if (isInMap(position)) {
            return this.tiles[position.x][position.y].getShip();
        }
        return null;
    }

    /**
     * Shot a bullet at given position on the map.
     * @param pos
     * @return
     */
    public boolean shot(Point pos) {
        if (isInMap(pos)) {
            if (!getTile(pos).isHit()) {
                this.tiles[pos.x][pos.y].setHit(true);
                return this.tiles[pos.x][pos.y].hasShip();
            }
        }
        return false;
    }

    public HitData shot2(Point pos) {
        HitData data = null;
        HitType type = HitType.Water;
        Ship ship = null;

        if (isInMap(pos)) {
            MapTile tile = getTile(pos);

            if (tile.isHit() || tile.isBlocked()) {
                return new HitData(null, null, HitType.NotPossible);
            }

            // Water
            if (!tile.hasShip()) {
                type = HitType.Water;
            } else if (tile.hasShip()) {
                ship = tile.getShip();

                // Ship destoryed
                if (ship.isDestroyed()) {
                    type = HitType.ShipDestroyed;
                    this.numberOfDestoryedShips++;
                } else {
                    // only ship
                    type = HitType.Ship;
                }
            }

            this.tiles[pos.x][pos.y].setHit(true);
            data = new HitData(pos, ship, type);
        }

        return data;
    }

    /**
     * Moves the ship to the new position, if its possible.
     * @param ship The ship.
     * @param newPosition The new position.
     * @return Whether or not it could be moved.
     */
    public boolean move(Ship ship, Point newPosition) {
        Point oldPos = ship.getPosition();

        this.remove(ship);

        if (canInsertShip(ship, newPosition, ship.isRotated())) {
            this.insert(ship, newPosition, ship.isRotated());
        } else {
            // fallback
            this.insert(ship, oldPos, ship.isRotated());
        }

        return true;
    }

    /**
     * Checks if a ship can placed into the map at given point.
     * @param ship The ship.
     * @param position The position where it should be placed.
     * @param rotated Whether or not the ship is rotated.
     * @return Returns boolean if the should could be insert sucessfully
     */
    private boolean canInsertShip(Ship ship, Point position, boolean rotated) {

        // prevent out of bounds
        if (!isInMap(position)) {
            return false;
        }

        MapTile firstTile = getTile(position);

        // prevent putting into an other ship
        if (firstTile.hasShip()) {
            return false;
        }

        if (!areTilesEmpty(position, ship.getSpace(), rotated))
            return false;

        return hasFreeNeighborTiles(position, ship.getSpace(), rotated);
    }

    /**
     * Removes the ship from the map
     * @param ship The ship.
     */
    public void remove(Ship ship) {
        for (int i = 0; i < ship.getTiles().size(); i++) {
            ship.getTiles().get(i).reset();
        }
        for (int i = 0; i < ship.getNeighborTiles().size(); i++) {
            ship.getNeighborTiles().get(i).reset();
        }

        this.numberOfShips--;
    }

    /***
     * Rotates a ship in the map, if its possible.
     * @param ship The ship.
     * @return Returns true if it could be rotated.
     */
    public boolean rotate(Ship ship) {
        boolean oldRotation = ship.isRotated();
        boolean nextRotation = !ship.isRotated();
        this.remove(ship);

        if (canInsertShip(ship, ship.getPosition(), nextRotation)) {
            this.insert(ship, ship.getPosition(), nextRotation);
        } else {
            // fallback
            this.insert(ship, ship.getPosition(), oldRotation);
        }

        return true;
    }

    /***
     * Checks if the point is in the map.
     * @param position The position
     * @return
     */
    public boolean isInMap(Point position) {
        return position.x >= 0 && position.x < this.size && position.y >= 0 && position.y < this.size;
    }
}
