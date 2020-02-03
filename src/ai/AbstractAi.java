package ai;

import game.Map;

import java.io.Serializable;

public abstract class AbstractAi implements AiInterface, Serializable {

    protected AiDifficulty difficulty;
    protected Map map;

    /**
     * initialize enemymap and difficulty for the AI
     *
     * @param map        {@link Map}
     * @param difficulty the difficulty level
     */

    public AbstractAi(Map map, AiDifficulty difficulty) {
        this.map = map;
        this.difficulty = difficulty;
    }
}
