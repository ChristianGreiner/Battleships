package game;

import core.Game;
import core.Helper;
import game.ships.*;

import java.util.HashMap;

public class MapGenerator {

    private HashMap<Integer, MapData> configMap = new HashMap<>();

    public MapGenerator() {
        try {
            MapData[] dat = Game.getInstance().getFileHandler().readMapConfig(Assets.Files.MAPDATA.getAbsolutePath());
            for (int i = 0; i < dat.length; i++) {
                configMap.put(dat[i].MapSize, dat[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map generate(int size) {

        if (this.configMap == null)
            return null;

        MapData data = this.configMap.get(size);
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

    private boolean insertShip(Ship ship, Map map) {
        MapTile freeTile = map.getRandomFreeTile();
        return map.insert(ship, freeTile.getPos(), Helper.randomBoolean());
    }
}
