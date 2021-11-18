package game;

import ai.AI;
import ai.AiDifficulty;

/**
 * Savegame for AI games in singleplayer mode.
 */
public class SingleplayerAiSavegame extends SingleplayerSavegame {

    private AI playerAi = null;

    /**
     * The constructor of the save game.
     *
     * @param playerMap   The player map.
     * @param enemyMap    The enemy map.
     * @param currentTurn
     * @param enemyAi
     * @param difficulty
     */
    public SingleplayerAiSavegame(Map playerMap, Map enemyMap, PlayerType currentTurn, AI enemyAi, AI playerAi, AiDifficulty difficulty) {
        super(playerMap, enemyMap, currentTurn, enemyAi, difficulty);
        this.playerAi = playerAi;
        this.savegameType = SavegameType.SingleplayerAi;
    }

    /**
     * Gets the player ai.
     *
     * @return The ai.
     */
    public AI getPlayerAi() {
        return this.playerAi;
    }
}
