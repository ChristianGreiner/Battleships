package game;

import game.ships.Ship;

import java.awt.*;

public class Map {
    private MapTile[][] tiles;
    //private HashMap<Point, Ship> shipsLookup = new HashMap<Point, Ship>();
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
        if (checkPosition(pos)) {
            return this.tiles[pos.x][pos.y];
        }
        return null;
    }

    public boolean insert(Ship ship, Point position, boolean fliped) {

        // prevent out of bounds
        if (!checkPosition(position)) {
            return false;
        }

        if (!fliped) {
            // check vertical
            if (position.y + ship.getFields() <= this.tiles.length - 1) {
                for (int i = 0; i < ship.getFields(); i++) {
                    this.tiles[position.x][position.y + i].setShip(ship);
                    ship.getTiles().add(new Point(position.x, position.y + i));
                }
            }
        } else {
            // check horizontal
            if (position.x + ship.getFields() <= this.tiles.length - 1) {
                for (int i = 0; i < ship.getFields(); i++) {
                    this.tiles[position.x + i][position.y].setShip(ship);
                    ship.getTiles().add(new Point(position.x + i, position.y));
                }
            }
        }

        return true;
    }

    public Ship getShip(Point position) {
        if (checkPosition(position)) {
            return this.tiles[position.x][position.y].getShip();
        }
        return null;
    }

    public void shot(Point position) {
        if (checkPosition(position)) {
            this.tiles[position.x][position.y].setHit(true);
        }
    }

    public boolean move(Ship ship, Point newPosition) {
        if (checkPosition(newPosition)) {
            this.remove(ship);
            this.insert(ship, newPosition, ship.isFlipped());

            return true;
        }
        return false;
    }

    public boolean remove(Ship ship) {
        for (int i = 0; i < ship.getTiles().size(); i++) {
            Point pos = ship.getTiles().get(i);
            this.tiles[pos.x][pos.y].setShip(null);
        }
        ship.getTiles().clear();

        return true;
    }

    private boolean checkPosition(Point position) {
        return position.x >= 0 && position.y >= 0 && position.x <= this.size - 1 && position.y <= this.size - 1;
    }
}
