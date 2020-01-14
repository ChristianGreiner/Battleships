package game;

import ai.AI;
import ai.AiDifficulty;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Stores all data for the save game.
 */
public class Savegame implements Serializable {

    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private Date date = new Date();

    private String timestamp = null;
    private Map playerMap = null;
    private Map enemyMap = null;
    private AiDifficulty difficulty = AiDifficulty.Easy;
    private PlayerType currentTurn = null;
    private AI ai = null;

    /**
     * The constructor of the save game.
     * @param playerMap The player map.
     * @param enemyMap The enemy map.
     * @param currentTurn The current turn of the players.
     * @param difficulty The difficulty of the ai.
     * @param ai THe current ai.
     */
    public Savegame(Map playerMap, Map enemyMap, PlayerType currentTurn, AiDifficulty difficulty, AI ai) {
        this.timestamp = this.dateFormat.format(date);
        this.playerMap = playerMap;
        this.enemyMap = enemyMap;
        this.currentTurn = currentTurn;
        this.difficulty = difficulty;
        this.ai = ai;
    }

    /**
     * Gets the ai.
     * @return The ai.
     */
    public AI getAi() {
        return ai;
    }

    /**
     * Gets the timestamp of the savegame.
     * @return The timestamp.
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Gets the map of the player.
     * @return The map.
     */
    public Map getPlayerMap() {
        return playerMap;
    }

    /**
     * Gets the map of the enemy.
     * @return The map.
     */
    public Map getEnemyMap() {
        return enemyMap;
    }

    /**
     * Gets the current turn of the playes.
     * @return The player type.
     */
    public PlayerType getCurrentTurn() {
        return currentTurn;
    }

    /**
     * Gets the difficulty of the ai.
     * @return The ai difficulty.
     */
    public AiDifficulty getDifficulty() {
        return difficulty;
    }
}
