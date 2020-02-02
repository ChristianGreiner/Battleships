package game;

public class MultiplayerAiSavegame extends Savegame {

    public MultiplayerAiSavegame(String id, Map playerMap, Map enemyMap) {
        super(id, playerMap, enemyMap);
        this.savegameType = SavegameType.MultiplayerAi;
    }
}
