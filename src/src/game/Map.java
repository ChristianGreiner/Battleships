package game;

import core.Drawable;
import core.Renderer;

import java.awt.*;
import java.util.HashMap;

public class Map implements Drawable {

    private int[][] ships;
    private HashMap<Integer, Ship> shipsLookup = new HashMap<Integer, Ship>();
    private Point pos;
    private Point size;
    private Point tileSize;

    public Map(Point pos, Point size, Point tileSize) {
        this.pos = pos;
        this.size = size;
        this.tileSize = tileSize;

        this.ships = new int[size.x][size.y];

        for (int x = 0; x < size.x; x++) {
            for (int y = 0; y < size.y; y++) {
                this.ships[x][y] = 0;
            }
        }
    }

    public boolean insert(Ship ship, Point position) {

        this.shipsLookup.put(ship.getId(), ship);
        this.ships[position.x][position.y] = ship.getId();
        return true;
    }

    public boolean move(int id, Point newPosition) {
        return true;
    }

    public boolean remove(int id) {
        return true;
    }

    @Override
    public void draw(Renderer renderer) {
        Graphics g = renderer.getDoubleBufferGraphics();
        g.fillRect(this.pos.x, this.pos.y, this.size.x * this.tileSize.x, this.size.y * this.tileSize.y);
    }
}