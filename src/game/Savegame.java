package game;

import java.io.Serializable;


/**
 * Stores all data for the save game.
 */
public class Savegame implements Serializable {

    protected SavegameType savegameType;
    private String id;
    private Map playerMap = null;
    private Map enemyMap = null;

    /**
     * The constructor of the save game.
     *
     * @param playerMap The player map.
     * @param enemyMap  The enemy map.
     */
    public Savegame(Map playerMap, Map enemyMap) {
        this.id = String.valueOf(System.currentTimeMillis());
        this.playerMap = playerMap;
        this.enemyMap = enemyMap;
    }

    /**
     * The constructor of the save game.
     *
     * @param id        The the id of the savegame.
     * @param playerMap The player map.
     * @param enemyMap  The enemy map.
     */
    public Savegame(String id, Map playerMap, Map enemyMap) {
        this.id = id;
        this.playerMap = playerMap;
        this.enemyMap = enemyMap;
    }

    /**
     * Reurns the typ of savegame.
     * @return the typ of savegame.
     */
    public SavegameType getSavegameType() {
        return savegameType;
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
}
