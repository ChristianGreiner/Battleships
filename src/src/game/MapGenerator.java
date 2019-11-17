package game;

import core.Helper;
import game.ships.*;

public class MapGenerator {
    public Map generate(int size, MapData data) {
        Map map = new Map(size);

        int countCarriers = 0;
        while (countCarriers < data.Carriers) {
            if (insertShip(new Carrier(), map)) {
                countCarriers++;
            }
        }

        int countBattleships = 0;
        while (countBattleships < data.Battleships) {
            if (insertShip(new Battleship(), map)) {
                countBattleships++;
            }
        }

        int countSubmarines = 0;
        while (countSubmarines < data.Submarines) {
            if (insertShip(new Submarine(), map)) {
                countSubmarines++;
            }
        }

        int countDestroyer = 0;
        while (countDestroyer < data.Destroyers) {
            if (insertShip(new Destroyer(), map)) {
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
