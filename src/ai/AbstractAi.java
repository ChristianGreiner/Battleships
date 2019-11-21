package ai;

import game.Map;

import java.io.Serializable;

public abstract class AbstractAi implements AiInterface, Serializable {

    protected AiDifficulty difficulty;
    protected Map map;

    public AbstractAi(Map map, AiDifficulty difficulty) {
        this.map = map;
        this.difficulty = difficulty;
    }
}
