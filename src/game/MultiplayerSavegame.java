package game;

/**
 * Savegame for online games
 */
public class MultiplayerSavegame extends Savegame {

    /**
     * Constructor for online game saves
     * @param id The ID of the game.
     * @param playerMap The player map.
     * @param enemyMap The map of the opponent.
     */
    public MultiplayerSavegame(String id, Map playerMap, Map enemyMap) {
        super(id, playerMap, enemyMap);
        this.savegameType = SavegameType.Multiplayer;
    }
}
