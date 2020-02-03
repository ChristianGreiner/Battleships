package game;

import core.Game;
import core.Helper;
import game.ships.*;

import java.util.HashMap;

/**
 * A map generator which places predefined random ships into the map.
 */
public class MapGenerator {

    private static HashMap<Integer, MapData> configMap = new HashMap<>();

    /**
     * A container for storing the mapdata (value) foreach map size. (Key)
     *
     * @return
     */
    public static HashMap<Integer, MapData> getConfigMap() {
        return configMap;
    }

    /**
     * Initialize the map generator and reads the map data. (Json file)
     */
    public static void init() {
        MapData[] data = null;
        try {
            data = Game.getInstance().getGameFileHandler().readMapConfig(Assets.Paths.MAPDATA);
            for (int i = 0; i < data.length; i++) {
                configMap.put(data[i].MapSize, data[i]);
            }
        } catch (Exception e) {
        }
    }

    /**
     * Generates the map.
     *
     * @param size The size of the map.
     * @return The map.
     */
    public Map generate(int size) {

        if (configMap == null)
            return null;

        MapData data = configMap.get(size);
        Map map = new Map(size);

        int countCarriers = 0;
        while (countCarriers < data.Carriers) {
            if (insertShip(new Carrier(map), map)) {
                countCarriers++;
            }
        }

        int countBattleships = 0;
        while (countBattleships < data.Battleships) {
            if (insertShip(new Battleship(map), map)) {
                countBattleships++;
            }
        }

        int countSubmarines = 0;
        while (countSubmarines < data.Submarines) {
            if (insertShip(new Submarine(map), map)) {
                countSubmarines++;
            }
        }

        int countDestroyer = 0;
        while (countDestroyer < data.Destroyers) {
            if (insertShip(new Destroyer(map), map)) {
                countDestroyer++;
            }
        }

        return map;
    }

    /**
     * Inserts a ship.
     * @param ship The ship to be inserted.
     * @param map The {@link Map} the ship has to be inserted into.
     * @return Returns a boolean if the insertion was succesful.
     */
    private boolean insertShip(Ship ship, Map map) {
        MapTile freeTile = map.getRandomFreeTile();
        return map.insert(ship, freeTile.getPos(), Helper.randomBoolean());
    }
}
