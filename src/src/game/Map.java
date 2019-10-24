package game;

import game.ships.Ship;

import java.awt.*;

public class Map {
    private MapTile[][] tiles;
    private int size;

    public Map(int size) {
        this.size = size;
        this.tiles = new MapTile[size][size];

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                this.tiles[x][y] = new MapTile(new Point(x, y));
            }
        }
    }

    public int getSize() {
        return size;
    }

    public MapTile getTile(Point pos) {
        if (isInMap(pos)) {
            return this.tiles[pos.x][pos.y];
        }
        return null;
    }

    public boolean insert(Ship ship, Point position, boolean rotated) {

        // prevent out of bounds
        if (!isInMap(position)) {
            return false;
        }

        // prevent playing in a other ship
        if (getTile(position).hasShip()) {
            return false;
        }

        ship.setPosition(position);
        ship.setRotated(rotated);

        if (!hasFreeNeighborTiles(ship, position)) {
            return false;
        }

        if (rotated) {
            // check horizontal
            if (position.x + ship.getSpace() <= this.getSize()) {
                for (int i = 0; i < ship.getSpace(); i++) {
                    this.tiles[position.x + i][position.y].setShip(ship);
                    ship.getTiles().add(this.tiles[position.x + i][position.y]);
                }
                return true;
            }
        } else {
            if (areTilesEmpty(position, ship.getSpace(), false)) {
                // check vertical
                if (position.y + ship.getSpace() <= this.getSize()) {
                    for (int i = 0; i < ship.getSpace(); i++) {
                        this.tiles[position.x][position.y + i].setShip(ship);
                        ship.getTiles().add(this.tiles[position.x][position.y + i]);
                    }
                    return true;
                }
            }
        }

        return false;
    }

    /***
     * Hilfsmethode zum Prüfen, ob alle Felder frei sind, wenn das Schiff plaziert wird.
     * @param startPos
     * @param size
     * @param rotated
     * @return
     */
    private boolean areTilesEmpty(Point startPos, int size, boolean rotated) {
        if (rotated) {
            for (int x = 0; x < size; x++) {
                Point nextPos = new Point(startPos.x + x, startPos.y);
                if (getTile(nextPos).hasShip()) {
                    return false;
                }
            }
        } else {
            for (int y = 0; y < size; y++) {
                Point nextPos = new Point(startPos.x, startPos.y + y);
                if (getTile(nextPos).hasShip()) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean hasFreeNeighborTiles(Ship ship, Point position) {

        if (!isInMap(position))
            return false;

        /*
         * OOO
         * OXO
         * OXO
         * OXO
         * OOO
         */

        for (int i = 0; i < ship.getSpace(); i++) {
            if (ship.isRotated()) {
                Point pos = new Point(position.x + i, position.y);

                // check first tile (LEFT, TOP, DOWN)
                if (i == 0) {

                    // check top
                    if (pos.y > 0) {
                        if (this.tiles[pos.x][pos.y - 1].hasShip()) {
                            return false;
                        }
                    }

                    // check bottom
                    if (pos.y < getSize()) {
                        if (this.tiles[pos.x][pos.y + 1].hasShip()) {
                            return false;
                        }
                    }

                    // check left
                    if (pos.x > 0) {
                        if (this.tiles[pos.x - 1][pos.y].hasShip()) {
                            return false;
                        }
                    }

                    // check left bottom
                    if (pos.x > 0 && pos.y < this.getSize()) {
                        if (this.tiles[pos.x - 1][pos.y + 1].hasShip()) {
                            return false;
                        }
                    }

                    // check left top
                    if (pos.x > 0 && pos.y < this.getSize()) {
                        if (this.tiles[pos.x - 1][pos.y - 1].hasShip()) {
                            return false;
                        }
                    }
                } else if (i == ship.getSpace() - 1) {

                    // check top
                    if (pos.y > 0) {
                        if (this.tiles[pos.x][pos.y - 1].hasShip()) {
                            return false;
                        }
                    }

                    // check bottom
                    if (pos.y < getSize()) {
                        if (this.tiles[pos.x][pos.y + 1].hasShip()) {
                            return false;
                        }
                    }

                    // check right
                    if (pos.x < this.getSize()) {
                        if (this.tiles[pos.x + 1][pos.y].hasShip()) {
                            return false;
                        }
                    }

                    // check top right
                    if (pos.x < this.getSize() && pos.y > 0) {
                        if (this.tiles[pos.x + 1][pos.y - 1].hasShip()) {
                            return false;
                        }
                    }

                    // check right bottom
                    if (pos.x > 0 && pos.y < this.getSize()) {
                        if (this.tiles[pos.x + 1][pos.y + 1].hasShip()) {
                            return false;
                        }
                    }
                }

            } else {
                Point pos = new Point(position.x, position.y + i);

                // check first tile
                if (i == 0) {

                    // check top
                    if (pos.y > 0) {
                        if (this.tiles[pos.x][pos.y - 1].hasShip()) {
                            return false;
                        }
                    }

                    // check left
                    if (pos.x > 0) {
                        if (this.tiles[pos.x - 1][pos.y].hasShip()) {
                            return false;
                        }
                    }

                    // check right
                    if (pos.x < this.getSize()) {
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
                    if (pos.x < this.getSize() && pos.y > 0) {
                        if (this.tiles[pos.x + 1][pos.y - 1].hasShip()) {
                            return false;
                        }
                    }

                }
                // check last tile
                else if (i == ship.getSpace() - 1) {

                    // check left
                    if (pos.x > 0) {
                        if (this.tiles[pos.x - 1][pos.y].hasShip()) {
                            return false;
                        }
                    }

                    // check right
                    if (pos.x < this.getSize()) {
                        if (this.tiles[pos.x + 1][pos.y].hasShip()) {
                            return false;
                        }
                    }

                    // check bottom
                    if (pos.y < this.getSize()) {
                        if (this.tiles[pos.x][pos.y - 1].hasShip()) {
                            return false;
                        }
                    }

                    // check left bottom
                    if (pos.x > 0 && pos.y < this.getSize()) {
                        if (this.tiles[pos.x - 1][pos.y - 1].hasShip()) {
                            return false;
                        }
                    }

                    // check right bottom
                    if (pos.x < this.getSize() && pos.y < this.getSize()) {
                        if (this.tiles[pos.x + 1][pos.y + 1].hasShip()) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    public Ship getShip(Point position) {
        if (isInMap(position)) {
            return this.tiles[position.x][position.y].getShip();
        }
        return null;
    }

    public void shot(Point position) {
        if (isInMap(position)) {
            this.tiles[position.x][position.y].setHit(true);
        }
    }

    public boolean move(Ship ship, Point newPosition) {
        if (isInMap(newPosition)) {
            this.remove(ship);
            this.insert(ship, newPosition, ship.isRotated());

            return true;
        }
        return false;
    }

    public boolean remove(Ship ship) {
        for (int i = 0; i < ship.getTiles().size(); i++) {
            Point pos = ship.getTiles().get(i).getPos();
            this.tiles[pos.x][pos.y].setShip(null);
        }
        ship.getTiles().clear();

        return true;
    }

    public void rotateShip(Ship ship) {
        boolean nextRotation = !ship.isRotated();
        this.remove(ship);
        this.insert(ship, ship.getPosition(), nextRotation);
    }

    private boolean isInMap(Point position) {
        return position.x >= 0 && position.x < this.size && position.y >= 0 && position.y < this.size;
    }
}
