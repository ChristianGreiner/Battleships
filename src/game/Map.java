package game;

import core.Helper;
import game.ships.Battleship;
import game.ships.Destroyer;
import game.ships.Ship;
import game.ships.Submarine;

import java.awt.*;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Map stores all data for the game.
 */
public class Map implements MapInterface, Serializable {
    private MapTile[][] tiles;
    private HashMap<Type, Integer> shipsCounter = new HashMap<>();
    private int size;
    private int outOfShipLength = 1; // value of the ship with the smallest size, which is completely destroyed
    private int numberOfShips;
    private int numberOfDestoryedShips;
    private ArrayList<Ship> ships = new ArrayList<>();

    public Map(int size) {
        this.size = size;
        this.tiles = new MapTile[size][size];

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                this.tiles[x][y] = new MapTile(new Point(x, y));
            }
        }
    }

    /**
     * Gets a hashmap that counts ships by its type.
     * @return A hashmap of ships.
     */
    public HashMap<Type, Integer> getShipsCounter() {
        return shipsCounter;
    }

    /**
     * Gets the number of ships.
     * @return The number of ships,
     */
    public int getNumberOfShips() {
        return numberOfShips;
    }

    /**
     * Gets the number of destroyed ships.
     * @return The number of destroyed ship.s
     */
    public int getNumberOfDestoryedShips() {
        return numberOfDestoryedShips;
    }

    /**
     * Gets the size of the map.
     * @return The size.
     */
    public int getSize() {
        return size;
    }

    /**
     * Gets all ships as array.
     * @return All ships as array.
     */
    public ArrayList<Ship> getShips() {
        return ships;
    }

    /**
     * Gets a tile by its position.
     * @param pos The position of the tile.
     * @return The Map tile.
     */
    public MapTile getTile(Point pos) {
        if (isInMap(pos)) {
            return this.tiles[pos.x][pos.y];
        }
        return null;
    }

    /**
     * Whenever or not all ships are destroyed.
     * @return Returns whenever or not all ships are destroyed.
     */
    public boolean allShipsDestoryed() {
        return this.getNumberOfShips() == this.getNumberOfDestoryedShips();
    }

    /**
     * Gets a random free tile.
     * @return A random tile.
     */
    public MapTile getRandomFreeTile() {
        while (true) {
            int rndX = Helper.randomNumber(0, this.getSize() - 1);
            int rndY = Helper.randomNumber(0, this.getSize() - 1);
            MapTile tile = getTile(new Point(rndX, rndY));
            if (!tile.hasShip() && !tile.isHit() && !tile.isBlocked()) {
                return getTile(new Point(rndX, rndY));
            }
        }
    }

    /**
     * Gets a random free tile having a ship in it.
     * @return A random tile.
     */
    public MapTile getRandomFreeTileIgnoreShip() {
        while (true) {
            int rndX = Helper.randomNumber(0, this.getSize() - 1);
            int rndY = Helper.randomNumber(0, this.getSize() - 1);
            MapTile tile = getTile(new Point(rndX, rndY));
            if (!tile.isHit() && !tile.isBlocked()) {
                return getTile(new Point(rndX, rndY));
            }
        }
    }

    public void setOutOfShipLength() { //everytime ship gets destroyed, this method should be refreshed
        if (this.shipsCounter.get(Submarine.class) == 0) {
            outOfShipLength = 2;
            if (this.shipsCounter.get(Destroyer.class) == 0) {
                outOfShipLength = 3;
                if (this.shipsCounter.get(Battleship.class) == 0) {
                    outOfShipLength = 4;
                }
            }
        }
    }

    /**
     * Inserts a new ship into the map.
     * @param ship The ship.
     * @param position The position.
     * @param rotated Whenever or not the ship is rotated.
     * @return Returns boolean if the ship could be placed.
     */
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
                    this.tiles[position.x][position.y + i].setShip(ship);
                    ship.getTiles().add(this.tiles[position.x][position.y + i]);
                }
            }
        }

        this.computeShipCountAdd(ship);

        return true;
    }

    private void computeShipCountAdd(Ship ship) {
        int counter = 0;
        if (this.shipsCounter.containsKey(ship.getClass()))
            counter = this.shipsCounter.get(ship.getClass());

        counter++;
        this.shipsCounter.put(ship.getClass(), counter);
        this.ships.add(ship);
    }

    private void computeRemoveShip(Ship ship) {
        int counter = 0;
        if (this.shipsCounter.containsKey(ship.getClass()))
            counter = this.shipsCounter.get(ship.getClass());

        counter--;
        this.shipsCounter.put(ship.getClass(), counter);
        this.ships.remove(ship);
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
                        AddNeighborTiles(tiles, pos.x, pos.y - 1);
                    }

                    // check bottom
                    if (pos.y < getSize() - 1) {
                        AddNeighborTiles(tiles, pos.x, pos.y + 1);
                    }

                    // check left
                    if (pos.x > 0) {
                        AddNeighborTiles(tiles, pos.x - 1, pos.y);
                    }

                    // check left bottom
                    if (pos.x > 0 && pos.y < this.getSize() - 1) {
                        AddNeighborTiles(tiles, pos.x - 1, pos.y + 1);
                    }

                    // check left top
                    if (pos.x > 0 && pos.y > 0) {
                        AddNeighborTiles(tiles, pos.x - 1, pos.y - 1);
                    }
                } else if (i == ship.getSpace() - 1) {

                    // check top
                    if (pos.y > 0) {
                        AddNeighborTiles(tiles, pos.x, pos.y - 1);
                    }

                    // check bottom
                    if (pos.y < getSize() - 1) {
                        AddNeighborTiles(tiles, pos.x, pos.y + 1);
                    }

                    // check right
                    if (pos.x < this.getSize() - 1) {
                        AddNeighborTiles(tiles, pos.x + 1, pos.y);
                    }

                    // check top right
                    if (pos.x < this.getSize() - 1 && pos.y > 0) {
                        AddNeighborTiles(tiles, pos.x + 1, pos.y - 1);
                    }

                    // check right bottom
                    if (pos.x < this.getSize() - 1 && pos.y < this.getSize() - 1) {
                        AddNeighborTiles(tiles, pos.x + 1, pos.y + 1);
                    }
                } else {
                    // check top
                    if (pos.y > 0) {
                        AddNeighborTiles(tiles, pos.x, pos.y - 1);
                    }

                    // check bottom
                    if (pos.y < getSize() - 1) {
                        AddNeighborTiles(tiles, pos.x, pos.y + 1);
                    }

                }
            } else {

                Point pos = new Point(ship.getPosition().x, ship.getPosition().y + i);

                // first tile
                if (i == 0) {
                    // check top
                    if (pos.y > 0) {
                        AddNeighborTiles(tiles, pos.x, pos.y - 1);
                    }

                    // check left
                    if (pos.x > 0) {
                        AddNeighborTiles(tiles, pos.x - 1, pos.y);
                    }

                    // check right
                    if (pos.x < this.getSize() - 1) {
                        AddNeighborTiles(tiles, pos.x + 1, pos.y);
                    }

                    // check top left
                    if (pos.x > 0 && pos.y > 0) {
                        AddNeighborTiles(tiles, pos.x - 1, pos.y - 1);
                    }

                    // check top right
                    if (pos.x < this.getSize() - 1 && pos.y > 0) {
                        AddNeighborTiles(tiles, pos.x + 1, pos.y - 1);
                    }
                }
                // check last tile
                else if (i == ship.getSpace() - 1) {

                    // check left
                    if (pos.x > 0) {
                        AddNeighborTiles(tiles, pos.x - 1, pos.y);
                    }

                    // check right
                    if (pos.x < this.getSize() - 1) {
                        AddNeighborTiles(tiles, pos.x + 1, pos.y);
                    }

                    // check bottom
                    if (pos.y < this.getSize() - 1) {
                        AddNeighborTiles(tiles, pos.x, pos.y + 1);
                    }

                    // check left bottom
                    if (pos.x > 0 && pos.y < this.getSize() - 1) {
                        AddNeighborTiles(tiles, pos.x - 1, pos.y + 1);
                    }

                    // check right bottom
                    if (pos.x < this.getSize() - 1 && pos.y < this.getSize() - 1) {
                        AddNeighborTiles(tiles, pos.x + 1, pos.y + 1);
                    }
                } else {
                    // check left
                    if (pos.x > 0) {
                        AddNeighborTiles(tiles, pos.x - 1, pos.y);
                    }

                    // check right
                    if (pos.x < this.getSize() - 1) {
                        AddNeighborTiles(tiles, pos.x + 1, pos.y);
                    }
                }
            }
        }

        return tiles;
    }

    private void AddNeighborTiles(ArrayList<MapTile> tiles, int x, int y) {
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
                    if (pos.x > 0 && pos.y > 0) {
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
                    if (pos.x < this.getSize() - 1 && pos.y < this.getSize() - 1) {
                        if (this.tiles[pos.x + 1][pos.y + 1].hasShip())
                            return false;
                    }
                } else {
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

    public boolean fieldIsViable(Point pos) {

        if (!(isInMap(pos)) || this.tiles[pos.x][pos.y].isHit() || !(this.tiles[pos.x][pos.y].getViable() || this.tiles[pos.x][pos.y].isBlocked())) {
            return false;
        }

        boolean borderXplusReached = false;
        boolean borderYplusReached = false;
        boolean borderXminusReached = false;
        boolean borderYminusReached = false;
        int rangeX = 1;
        int rangeY = 1;

        for (int i = 1; i < 5; i++) {

            if (!borderXplusReached) {
                Point newPosX = new Point(pos.x + i, pos.y);
                if (isInMap(newPosX)) {
                    if (!(this.tiles[pos.x + i][pos.y].isHit() && !(this.tiles[pos.x + 1][pos.y].isBlocked()))) {
                        rangeX++;
                    } else borderXplusReached = true;
                } else borderXplusReached = true;
            }
            if (!borderXminusReached) {
                Point newPosNegX = new Point(pos.x - i, pos.y);
                if (isInMap(newPosNegX)) {
                    if (!(this.tiles[pos.x - i][pos.y].isHit() && !(this.tiles[pos.x - 1][pos.y].isBlocked()))) {
                        rangeX++;
                    } else borderXminusReached = true;
                } else borderXminusReached = true;
            }
            if (!borderYplusReached) {
                Point newPosY = new Point(pos.x, pos.y + i);
                if (isInMap(newPosY)) {
                    if (!(this.tiles[pos.x][pos.y + 1].isHit() && !(this.tiles[pos.x][pos.y + 1].isBlocked()))) {
                        rangeX++;
                    } else borderYplusReached = true;
                } else borderYplusReached = true;
            }
            if (!borderYminusReached) {
                Point newPosNegY = new Point(pos.x, pos.y - i);
                if (isInMap(newPosNegY)) {
                    if (!(this.tiles[pos.x][pos.y - 1].isHit() && !(this.tiles[pos.x][pos.y - 1].isBlocked()))) {
                        rangeX++;
                    } else borderYminusReached = true;
                } else borderYminusReached = true;
            }
        }

        if (((rangeX == 2 || rangeX == 3) && rangeY == 1) || ((rangeY == 2 || rangeY == 3) && rangeX == 1)) {
            return false;
        }

        if (rangeX == 5 || rangeY == 5)
            return true;

        if (borderXminusReached && borderXplusReached && borderYminusReached && borderYplusReached) {
            if (rangeX == 1 && rangeY == 1) return false;
            if (rangeX == 2 && rangeX > rangeY || rangeY == 2 && rangeY > rangeX) {
                if (outOfShipLength == 2) {
                    this.tiles[pos.x][pos.y].setViable(false);
                    return false;
                }
            }
            if (rangeX == 3 && rangeX > rangeY || rangeY == 3 && rangeY > rangeX) {
                if (outOfShipLength == 3) {
                    this.tiles[pos.x][pos.y].setViable(false);
                    return false;
                }
            }
            if (rangeX == 4 && rangeX > rangeY || rangeY == 4 && rangeY > rangeX) {
                if (outOfShipLength == 4) {
                    this.tiles[pos.x][pos.y].setViable(false);
                    return false;
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
     *
     * @param pos
     * @return
     */
    public HitData shot(Point pos) {
        HitData data = null;
        HitType type = HitType.Water;
        Ship ship = null;

        if (isInMap(pos)) {
            MapTile tile = getTile(pos);

            if (tile.isHit() || tile.isBlocked()) {
                return new HitData(null, null, HitType.NotPossible);
            }

            this.tiles[pos.x][pos.y].setHit(true);

            // Water
            if (!tile.hasShip()) {
                type = HitType.Water;
            } else if (tile.hasShip()) {
                ship = tile.getShip();

                // Ship destoryed
                if (ship.isDestroyed()) {
                    type = HitType.ShipDestroyed;
                    this.numberOfDestoryedShips++;
                    this.computeRemoveShip(ship);

                } else {
                    // only ship
                    type = HitType.Ship;
                }
            }

            data = new HitData(pos, ship, type);
        } else {
            try {
                throw new Exception("Point out of Map");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return data;
    }

    /**
     * Moves the ship to the new position, if its possible.
     *
     * @param ship        The ship.
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
     *
     * @param ship     The ship.
     * @param position The position where it should be placed.
     * @param rotated  Whether or not the ship is rotated.
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
     *
     * @param ship The ship.
     */
    public void remove(Ship ship) {
        for (int i = 0; i < ship.getTiles().size(); i++) {
            ship.getTiles().get(i).reset();
        }
        for (int i = 0; i < ship.getNeighborTiles().size(); i++) {
            ship.getNeighborTiles().get(i).reset();
        }

        ship.getTiles().clear();
        ship.getNeighborTiles().clear();

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
        if (position == null)
            return false;

        return position.x >= 0 && position.x < this.size && position.y >= 0 && position.y < this.size;
    }
}
