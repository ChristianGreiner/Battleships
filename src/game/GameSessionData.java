package game;

import ai.AiDifficulty;

/**
 * Contains data of the game session.
 */
public class GameSessionData {
    private int mapSize;
    private Map map;
    private AiDifficulty aiDifficulty;

    /**
     * Gets the map.
     * @return The map.
     */
    public Map getMap() {
        return map;
    }

    /**
     * Gets the map size.
     * @return The size of the map.
     */
    public int getMapSize() {
        return mapSize;
    }

    /**
     * Gets the difficulty of the ai.
     * @return The difficulty of the ai.
     */
    public AiDifficulty getAiDifficulty() {
        return aiDifficulty;
    }

    /**
     * The game session data constructor.
     * @param map The map.
     * @param mapSize The size of the map.
     * @param aiDifficulty The difficulty of the ai.
     */
    public GameSessionData(Map map, int mapSize, AiDifficulty aiDifficulty) {
        this.map = map;
        this.mapSize = mapSize;
        this.aiDifficulty = aiDifficulty;
    }
}
