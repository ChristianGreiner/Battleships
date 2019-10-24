package game;

import core.Helper;
import game.ships.Carrier;
import game.ships.Destroyer;
import game.ships.Ship;
import game.ships.Submarine;

public class MapGenerator {
    public MapGenerator() {
    }

    public Map generate(int size, MapData data) {
        Map map = new Map(size);

        int shipsTotal = data.ShipsCount;
        int currentShipTotal = 0;

        for (int i = 0; i < data.Submarines; i++) {
            if (!insertShip(new Submarine(), map))
                i--;
        }

        for (int i = 0; i < data.Carriers; i++) {
            if (!insertShip(new Carrier(), map))
                i--;
        }

        for (int i = 0; i < data.Destoryers; i++) {
            if (!insertShip(new Destroyer(), map))
                i--;
        }

        for (int i = 0; i < data.Destoryers; i++) {
            if (!insertShip(new Destroyer(), map))
                i--;
        }

        return map;
    }

    private boolean insertShip(Ship ship, Map map) {
        MapTile freeTile = map.getRandomFreeTile();
        return map.insert(ship, freeTile.getPos(), Helper.randomBoolean());
    }
}
