package game;

import ai.AI;
import ai.AiDifficulty;

/**
 * Savegame for regular singleplayer games.
 */
public class SingleplayerSavegame extends Savegame {

    private AiDifficulty difficulty = AiDifficulty.Easy;
    private AI enemyAi = null;
    private PlayerType currentTurn = null;

    /**
     * The constructor of the save game.
     *
     * @param playerMap The player map.
     * @param enemyMap  The enemy map.
     */
    public SingleplayerSavegame(Map playerMap, Map enemyMap, PlayerType currentTurn, AI enemyAi, AiDifficulty difficulty) {
        super(playerMap, enemyMap);
        this.currentTurn = currentTurn;
        this.enemyAi = enemyAi;
        this.difficulty = difficulty;
        this.savegameType = SavegameType.Singleplayer;
    }

    /**
     * Gets the ai.
     *
     * @return The ai.
     */
    public AI getEnemyAi() {
        return this.enemyAi;
    }

    /**
     * Gets the current turn of the playes.
     *
     * @return The player type.
     */
    public PlayerType getCurrentTurn() {
        return currentTurn;
    }

    /**
     * Gets the difficulty of the ai.
     *
     * @return The ai difficulty.
     */
    public AiDifficulty getDifficulty() {
        return difficulty;
    }
}
