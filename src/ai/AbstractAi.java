package ai;

import game.Map;

public abstract class AbstractAi implements AiInterface{

    protected AiDifficulty difficulty;
    protected Map map;

    public AbstractAi(Map map, AiDifficulty difficulty) {
        this.map = map;
        this.difficulty = difficulty;
    }
}
