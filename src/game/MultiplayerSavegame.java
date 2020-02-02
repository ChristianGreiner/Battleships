package game;

public class MultiplayerSavegame extends Savegame {

    public MultiplayerSavegame(String id, Map playerMap, Map enemyMap) {
        super(id, playerMap, enemyMap);
        this.savegameType = SavegameType.Multiplayer;
    }
}
