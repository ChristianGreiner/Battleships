package game;

import ai.AiDifficulty;

public class GameSessionData {
    private int mapSize;

    public Map getMap() {
        return map;
    }

    private Map map;

    public int getMapSize() {
        return mapSize;
    }

    public AiDifficulty getAiDifficulty() {
        return aiDifficulty;
    }

    public GameSessionData(Map map, int mapSize, AiDifficulty aiDifficulty) {
        this.map = map;
        this.mapSize = mapSize;
        this.aiDifficulty = aiDifficulty;
    }

    private AiDifficulty aiDifficulty;

}
