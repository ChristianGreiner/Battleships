package game;

/**
 * Savegame specifically for AI games.
 */
public class MultiplayerAiSavegame extends Savegame {

    /**
     * Constructor for AI game saves
     * @param id The ID of the game.
     * @param playerMap The map of the player.
     * @param enemyMap The map of the opponent.
     */
    public MultiplayerAiSavegame(String id, Map playerMap, Map enemyMap) {
        super(id, playerMap, enemyMap);
        this.savegameType = SavegameType.MultiplayerAi;
    }
}
