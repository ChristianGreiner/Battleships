package game;

import ai.AiDifficulty;

/**
 * Contains data of the game session.
 */
public class GameSessionData {
    private int mapSize;
    private Map map;
    private AiDifficulty aiDifficulty;
    private Savegame savegame;
    private boolean aiGame;

    /**
     * The game session data constructor.
     *
     * @param map          The map.
     * @param mapSize      The size of the map.
     * @param aiDifficulty The difficulty of the ai.
     */
    public GameSessionData(Map map, int mapSize, AiDifficulty aiDifficulty) {
        this.map = map;
        this.mapSize = mapSize;
        this.aiDifficulty = aiDifficulty;
    }

    /**
     * The game session data constructor.
     *
     * @param map          The map.
     * @param mapSize      The size of the map.
     * @param aiDifficulty The difficulty of the ai.
     */
    public GameSessionData(Map map, int mapSize, AiDifficulty aiDifficulty, boolean aiGame) {
        this.map = map;
        this.mapSize = mapSize;
        this.aiDifficulty = aiDifficulty;
        this.aiGame = aiGame;
    }

    /**
     * New Game session with just the savegame.
     *
     * @param savegame The savegame
     */
    public GameSessionData(Savegame savegame) {
        this.savegame = savegame;
    }

    /**
     * Gets the savegame.
     *
     * @return
     */
    public Savegame getSavegame() {
        return savegame;
    }

    /**
     * Gets the map.
     *
     * @return The map.
     */
    public Map getMap() {
        return map;
    }

    /**
     * Gets the map size.
     *
     * @return The size of the map.
     */
    public int getMapSize() {
        return mapSize;
    }

    /**
     * Gets the difficulty of the ai.
     *
     * @return The difficulty of the ai.
     */
    public AiDifficulty getAiDifficulty() {
        return aiDifficulty;
    }

    /**
     * Whenever or not the game session is an ai game
     *
     * @return True or false.
     */
    public boolean isAiGame() {
        return aiGame;
    }
}
