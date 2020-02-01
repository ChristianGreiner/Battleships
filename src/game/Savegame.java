package game;

import ai.AI;
import ai.AiDifficulty;
import network.NetworkType;

import java.io.Serializable;


/**
 * Stores all data for the save game.
 */
public class Savegame implements Serializable {

    private String id;
    private Map playerMap = null;
    private Map enemyMap = null;
    private AiDifficulty difficulty = AiDifficulty.Easy;
    private PlayerType currentTurn = null;
    private NetworkType currentNetworkTurn = null;
    private AI ai = null;
    private boolean isNetworkGame = false;

    /**
     * The constructor of the save game.
     *
     * @param playerMap   The player map.
     * @param enemyMap    The enemy map.
     * @param currentTurn The current turn of the players.
     * @param difficulty  The difficulty of the ai.
     * @param ai          THe current ai.
     */
    public Savegame(Map playerMap, Map enemyMap, PlayerType currentTurn, AiDifficulty difficulty, AI ai) {
        this.id = String.valueOf(System.currentTimeMillis());
        this.playerMap = playerMap;
        this.enemyMap = enemyMap;
        this.currentTurn = currentTurn;
        this.difficulty = difficulty;
        this.ai = ai;
    }

    /**
     * The constructor of the savegame.
     *
     * @param playerMap          The player map.
     * @param enemyMap           The enemy map.
     * @param currentNetworkTurn The current turn of the players.
     * @param id                 The id of the savegame.
     */
    public Savegame(Map playerMap, Map enemyMap, NetworkType currentNetworkTurn, String id) {
        this.id = id;
        this.playerMap = playerMap;
        this.enemyMap = enemyMap;
        this.currentNetworkTurn = currentNetworkTurn;
    }

    /**
     * Whenever or not the game was an network game.
     *
     * @return True or false.
     */
    public boolean isNetworkGame() {
        return isNetworkGame;
    }

    /**
     * Sets the savegame as a network game.
     *
     * @param networkGame True or false
     */
    public void setNetworkGame(boolean networkGame) {
        isNetworkGame = networkGame;
    }

    /**
     * Gets the ai.
     *
     * @return The ai.
     */
    public AI getAi() {
        return ai;
    }

    /**
     * Gets the timestamp of the savegame.
     *
     * @return The timestamp.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the map of the player.
     *
     * @return The map.
     */
    public Map getPlayerMap() {
        return playerMap;
    }

    /**
     * Gets the map of the enemy.
     *
     * @return The map.
     */
    public Map getEnemyMap() {
        return enemyMap;
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
