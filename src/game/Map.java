package game;

import core.Game;
import core.Helper;
import game.ships.*;

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
    private HashMap<Type, Integer> shipsCounter = new HashMap<Type, Integer>();
    private HashMap<Type, Integer> availableCounter = new HashMap<Type, Integer>();
    private int size;
    private int outOfShipLength = 1; // value of the ship with the smallest size, which is completely destroyed
    private int numberOfShips;
    private int numberOfDestroyedShips;
    private ArrayList<Ship> ships;
    private ArrayList<Ship> destroyedShips;
    private ArrayList<MapListener> listeners;
    private MapData mapData;

    /**
     * The constructor of the map.
     *
     * @param size The size of the map.
     */
    public Map(int size) {
        this.size = size;
        this.tiles = new MapTile[size][size];
        this.mapData = MapGenerator.getConfigMap().get(this.size);
        this.listeners = new ArrayList<MapListener>();
        this.ships = new ArrayList<Ship>();
        this.destroyedShips = new ArrayList<>();

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                this.tiles[x][y] = new MapTile(new Point(x, y));
            }
        }

        // initialize default ships
        this.shipsCounter.put(Carrier.class, 0);
        this.shipsCounter.put(Battleship.class, 0);
        this.shipsCounter.put(Destroyer.class, 0);
        this.shipsCounter.put(Submarine.class, 0);

        this.availableCounter.put(Carrier.class, mapData.Carriers);
        this.availableCounter.put(Battleship.class, mapData.Battleships);
        this.availableCounter.put(Destroyer.class, mapData.Destroyers);
        this.availableCounter.put(Submarine.class, mapData.Submarines);
    }

    /**
     * Gets all destroyed ships.
     *
     * @return The ship.s
     */
    public ArrayList<Ship> getDestroyedShips() {
        return this.destroyedShips;
    }

    /**
     * Gets the map data.
     * @return
     */
    public MapData getMapData() {
        return mapData;
    }

    /**
     * Gets a hashmap that counts ships by its type.
     *
     * @return A hashmap of ships.
     */
    public HashMap<Type, Integer> getShipsCounter() {
        return shipsCounter;
    }

    /**
     * Gets the number of ships.
     *
     * @return The number of ships,
     */
    public int getNumberOfShips() {
        return numberOfShips;
    }

    /**
     * Gets the number of destroyed ships.
     *
     * @return The number of destroyed ship.s
     */
    public int getNumberOfDestroyedShips() {
        return numberOfDestroyedShips;
    }

    /**
     * Gets the size of the map.
     *
     * @return The size.
     */
    public int getSize() {
        return size;
    }

    /**
     * Gets all ships as array.
     *
     * @return All ships as array.
     */
    public ArrayList<Ship> getShips() {
        return ships;
    }

    /**
     * Adds a new listener to the map.
     * @param listener The listener.
     */
    public void addListener(MapListener listener) {
        this.listeners.add(listener);
    }

    /**
     * Whenever or not the map is correct filled with ships.
     *
     * @return True or false.
     */
    public boolean isCorrectFilled() {
        return this.mapData.Carriers == this.shipsCounter.get(Carrier.class) &&
                this.mapData.Battleships == this.shipsCounter.get(Battleship.class) &&
                this.mapData.Destroyers == this.shipsCounter.get(Destroyer.class) &&
                this.mapData.Submarines == this.shipsCounter.get(Submarine.class);
    }

    /**
     * Mark a tile as hit.
     * @param pos The position.
     * @param type The typ.
     */
    public void markTile(Point pos, HitType type) {
        if (isInMap(pos)) {
            if (type == HitType.Water) {
                this.tiles[pos.x][pos.y].setHit(true);
            } else if (type == HitType.Ship || type == HitType.ShipDestroyed) {
                this.tiles[pos.x][pos.y].setShip(new DummyShip(this));
                this.tiles[pos.x][pos.y].setHit(true);
            }
        }
    }

    /**
     * Gets a tile by its position.
     *
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
     *
     * @return Returns whenever or not all ships are destroyed.
     */
    public boolean allShipsDestroyed() {
        return this.getNumberOfShips() == this.getNumberOfDestroyedShips();
    }

    /**
     * Gets a random free tile.
     *
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
     *
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

    /**
     * Uses by the ai for calculation the next hit.
     */
    public void setOutOfShipLength() {
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
     *
     * @param ship     The ship.
     * @param position The position.
     * @param rotated  Whenever or not the ship is rotated.
     * @return Returns boolean if the ship could be placed.
     */
    public boolean insert(Ship ship, Point position, boolean rotated) {

        if (!checkShipCountAmount(ship))
            return false;

        if (!canInsertShip(ship, position, rotated))
            return false;

        this.numberOfShips++;

        // all checks passed!
        ship.setPosition(position);
        ship.setRotated(rotated);
        ship.setNeighborTiles(getNeighborTiles(ship));

        this.setShipTiles(ship, position, rotated);

        // increase ship counter
        if (this.shipsCounter.containsKey(ship.getClass())) {
            int counter = this.shipsCounter.get(ship.getClass());
            if (counter < this.availableCounter.get(ship.getClass()))
                this.shipsCounter.put(ship.getClass(), counter + 1);

            this.ships.add(ship);
        }

        // trigger listener
        for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).OnMapUpdated();
        }

        return true;
    }

    /**
     * Sets the tiles a ship occupies.
     * @param ship The {@link Ship} in the map.
     * @param position The position of the ship.
     * @param rotated Whether or not the ship is rotated.
     */
    private void setShipTiles(Ship ship, Point position, boolean rotated) {
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
    }

    /**
     * Decrements shipsCounter if a ship has to be removed and removes it.
     * @param ship The ship to be removed
     */
    private void computeRemoveShip(Ship ship) {
        if (this.shipsCounter.containsKey(ship.getClass())) {
            int counter = this.shipsCounter.get(ship.getClass());
            if (counter > 0) {
                counter--;
                this.shipsCounter.put(ship.getClass(), counter);
            }

            this.ships.remove(ship);
        }
    }

    /**
     * Get the adjacent tiles of a ship.
     * @param ship The ship of which the adjacent tiles need to be returned.
     * @return The adjacent tiles.
     */
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

    /**
     * Adds adjacent tiles.
     * @param tiles The tiles to be added.
     * @param x The x component of the postion where they have to be added.
     * @param y the y component of the position where they have to be added.
     */
    private void AddNeighborTiles(ArrayList<MapTile> tiles, int x, int y) {
        MapTile t = this.tiles[x][y];
        t.setNeighbor(true);
        tiles.add(t);
    }

    /**
     * Checks whether or not tiles are empty.
     * @param startPos Where the checking needs to start.
     * @param size How many tiles need to be checked.
     * @param rotated Direction of the checking.
     * @return true or false depending on whether or not the tiles are empty.
     */
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

    /**
     * Checks whether or not adjacent tiles of a line are empty.
     * @param position Starting posiiton.
     * @param space Lenght of the check.
     * @param rotated Direction of the check.
     * @return true or false depending on whether or not there are empty adjacent tiles.
     */
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

    /**
     * Whenever or not a field is visable or not. Mainly used by the ai.
     *
     * @param pos The pos of the field.
     * @return If the field is visable.
     */
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


            Game.getInstance().getLogger().info("MAP: set hit at: " + pos);
            this.tiles[pos.x][pos.y].setHit(true);

            // Water
            if (!tile.hasShip()) {
                type = HitType.Water;
            } else if (tile.hasShip()) {
                ship = tile.getShip();

                // Ship destroyed
                if (ship.isDestroyed()) {
                    type = HitType.ShipDestroyed;
                    this.numberOfDestroyedShips++;
                    this.computeRemoveShip(ship);
                    this.destroyedShips.add(ship);
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

        // trigger listener
        for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).OnMapUpdated();
        }

        return true;
    }

    /**
     * Checks if the right number of ships are in the map.
     * @param ship The ship that needs to be checked.
     * @return Returns boolean if the ship is allowed to be in the map.
     */
    private boolean checkShipCountAmount(Ship ship) {
        MapData mapData = MapGenerator.getConfigMap().get(this.size);

        if (ship.getClass().equals(Carrier.class))
            return this.shipsCounter.get(Carrier.class) < mapData.Carriers;
        else if (ship.getClass().equals(Battleship.class))
            return this.shipsCounter.get(Battleship.class) < mapData.Battleships;
        else if (ship.getClass().equals(Destroyer.class))
            return this.shipsCounter.get(Destroyer.class) < mapData.Destroyers;
        else if (ship.getClass().equals(Submarine.class))
            return this.shipsCounter.get(Submarine.class) < mapData.Submarines;

        return false;
    }

    /**
     * Checks if a ship can placed into the map at given point.
     *
     * @param ship     The ship.
     * @param position The position where it should be placed.
     * @param rotated  Whether or not the ship is rotated.
     * @return Returns boolean if the ship could be insert sucessfully
     */
    public boolean canInsertShip(Ship ship, Point position, boolean rotated) {

        if (!checkShipCountAmount(ship))
            return false;

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
     * Tests if a ship can move to a specific position
     *
     * @param ship     The ship
     * @param position The new position.
     * @param rotated  If the ships is rotated or not.
     * @return If the ship can be moved.
     */
    public boolean canMoveShip(Ship ship, Point position, boolean rotated) {

        this.remove(ship);

        boolean canMove = canInsertShip(ship, position, rotated);

        this.insert(ship, ship.getPosition(), ship.isRotated());

        return canMove;
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

        computeRemoveShip(ship);

        // trigger listener
        for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).OnMapUpdated();
        }
    }

    /**
     * Moves and simultaneously rotates a ship.
     * @param ship The ship to be moved and rotated.
     * @param pos The position the ship needs to be moved to.
     * @return Returns boolean if the ship could be moved and rotated.
     */
    public boolean moveAndRotate(Ship ship, Point pos) {
        boolean oldRotation = ship.isRotated();
        boolean nextRotation = !ship.isRotated();
        this.remove(ship);

        if (canInsertShip(ship, pos, nextRotation)) {
            this.insert(ship, pos, nextRotation);
        } else {
            // fallback
            this.insert(ship, ship.getPosition(), oldRotation);
        }

        // trigger listener
        for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).OnMapUpdated();
        }

        return true;
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

        // trigger listener
        for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).OnMapUpdated();
        }

        return true;
    }

    /**
     * Checks if the point is in the map.
     *
     * @param position The position
     * @return Whenever or not the point is in the map.
     */
    public boolean isInMap(Point position) {
        if (position == null)
            return false;

        return position.x >= 0 && position.x < this.size && position.y >= 0 && position.y < this.size;
    }

    /**
     * Removes {@link DummyShip}s and replaces them with a "real" ship of appropriate length.
     * @param dShip Point where the merging starts.
     */
    public void mergeDummyShips(Point dShip) {

        Point ShipPos = dShip;
        int ShipLengthCounter = 1;
        boolean shipIsX = false;

        Point neighborX = new Point(dShip.x + 1, dShip.y);
        Point neighborXMinus = new Point(dShip.x - 1, dShip.y);
        Point neighborY = new Point(dShip.x, dShip.y + 1);
        Point neighborYMinus = new Point(dShip.x, dShip.y - 1);

        if (isInMap(neighborX)) {
            if (this.tiles[neighborX.x][neighborX.y].hasShip()) {
                //this.remove(this.tiles[neighborX.x][neighborX.y].getShip());
                //ShipLengthCounter++;
                shipIsX = true;
            }
        }
        if (isInMap(neighborXMinus)) {
            if (this.tiles[neighborXMinus.x][neighborXMinus.y].hasShip()) {
                //this.remove(this.tiles[neighborXMinus.x][neighborXMinus.y].getShip());
                //ShipLengthCounter++;
                shipIsX = true;
                ShipPos = neighborXMinus;
            }
        }

        boolean proofPlus = true;
        boolean proofMinus = true;

        for (int i = 1; i <= 4; i++) {
            Point neighbor;
            Point neighborMinus;
            if (shipIsX) {
                neighbor = new Point(dShip.x + i, dShip.y);
                neighborMinus = new Point(dShip.x - i, dShip.y);
            } else {
                neighbor = new Point(dShip.x, dShip.y + i);
                neighborMinus = new Point(dShip.x, dShip.y - i);
            }

            if (isInMap(neighbor) && proofPlus) {
                if (this.tiles[neighbor.x][neighbor.y].hasShip()) {
                    this.remove(this.tiles[neighbor.x][neighbor.y].getShip());
                    ShipLengthCounter++;
                    System.out.println("SLC FY " + i + ": " + ShipLengthCounter);
                } else proofPlus = false;
            } else proofPlus = false;
            if (isInMap(neighborMinus) && proofMinus) {
                if (this.tiles[neighborMinus.x][neighborMinus.y].hasShip()) {
                    this.remove(this.tiles[neighborMinus.x][neighborMinus.y].getShip());
                    ShipLengthCounter++;
                    System.out.println("SLC FYM " + i + ": " + ShipLengthCounter);
                    ShipPos = neighborMinus;
                } else proofMinus = false;
            } else proofMinus = false;
        }

        Ship ship = null;
        switch (ShipLengthCounter) {
            case 2:
                ship = new Submarine(this);
                ship.setRotated(shipIsX);
                ship.setPosition(ShipPos);
                break;
            case 3:
                ship = new Destroyer(this);
                ship.setRotated(shipIsX);
                ship.setPosition(ShipPos);
                break;
            case 4:
                ship = new Battleship(this);
                ship.setRotated(shipIsX);
                ship.setPosition(ShipPos);
                break;
            case 5:
                ship = new Carrier(this);
                ship.setRotated(shipIsX);
                ship.setPosition(ShipPos);
                break;
        }
        System.out.println(ship + " " + ShipPos + " " + ShipLengthCounter + " ");
        this.numberOfDestroyedShips++;
        this.setShipTiles(ship, ShipPos, shipIsX);
        ship.setNeighborTiles(getNeighborTiles(ship));
        ship.forceDestroy();
        this.destroyedShips.add(ship);
    }
}
