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
    private SavegameType gameType;

    /**
     * The game session data constructor.
     *
     * @param map          The map.
     * @param mapSize      The size of the map.
     * @param aiDifficulty The difficulty of the ai.
     * @param gameType     The game type.
     */
    public GameSessionData(Map map, int mapSize, AiDifficulty aiDifficulty, SavegameType gameType) {
        this.map = map;
        this.mapSize = mapSize;
        this.aiDifficulty = aiDifficulty;
        this.gameType = gameType;
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

    public SavegameType getGameType() {
        return gameType;
    }
}
