package game;

import game.ships.Ship;

import java.awt.*;
import java.util.HashMap;

public class Map {
    private MapTile[][] tiles;
    private HashMap<Point, Ship> shipsLookup = new HashMap<Point, Ship>();
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

    public boolean insert(Ship ship, Point position, boolean fliped) {

        boolean insertFlag = false;

        // prevent out of bounds
        if (checkPosition(position)) {
            return false;
        }

        if (!fliped) {

            // check vertical
            if (position.y + ship.getFields() <= this.tiles.length - 1) {
                for (int i = 0; i < ship.getFields(); i++) {
                    this.tiles[position.x][position.y + i].setShip(ship);
                }
                insertFlag = true;
            }
        } else {
            // check horizontal
            if (position.x + ship.getFields() <= this.tiles.length - 1) {
                for (int i = 0; i < ship.getFields(); i++) {
                    this.tiles[position.x + 1][position.y].setShip(ship);
                }
                insertFlag = true;
            }
        }

        if (insertFlag) {
            this.shipsLookup.put(position, ship);
        }

        return insertFlag;
    }

    public Ship getShip(Point position) {

        if (checkPosition(position)) {
            return this.tiles[position.x][position.y].getShip();
        }

        return null;
    }

    public boolean move(int id, Point newPosition) {
        return true;
    }

    public boolean remove(int id) {
        return true;
    }

    private boolean checkPosition(Point position) {
        return position.x < 0 || position.y < 0 || position.x > this.size - 1 || position.y > this.size - 1;
    }
}
