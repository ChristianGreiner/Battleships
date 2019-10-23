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

        /*if(!hasFreeNeighborTiles(ship, position)) {
            return false;
        }*/

        if (!rotated) {
            // check vertical
            if (position.y + ship.getSpace() <= this.tiles.length - 1) {
                for (int i = 0; i < ship.getSpace(); i++) {
                    this.tiles[position.x][position.y + i].setShip(ship);
                    ship.getTiles().add(this.tiles[position.x][position.y + i]);
                }
                return true;
            }
        } else {
            // check horizontal
            if (position.x + ship.getSpace() <= this.tiles.length - 1) {
                for (int i = 0; i < ship.getSpace(); i++) {
                    this.tiles[position.x + i][position.y].setShip(ship);
                    ship.getTiles().add(this.tiles[position.x + i][position.y]);
                }
                return true;
            }
        }

        return false;
    }

    public boolean hasFreeNeighborTiles(Ship ship, Point position) {

        if (!isInMap(position))
            return false;

        for (int i = 0; i < ship.getSpace(); i++) {
            if (ship.isRotated()) {
                Point pos = new Point(position.x + i, position.y);

                // check first tile (LEFT, TOP, DOWN)
                if (i == 0) {
                    /*if (pos.x - 1 >= 0 && pos.x + 1 < this.size - 1) {
                        if (this.tiles[pos.x - 1][pos.y].getShip() != null && this.tiles[pos.x][pos.y - 1].getShip() != null) {
                            return false;
                        }
                    }*/
                } else if (i == getSize() - 1) {

                }

            } else {
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
        return position.x >= 0 && position.y >= 0 && position.x <= this.size - 1 && position.y <= this.size - 1;
    }
}
