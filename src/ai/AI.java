package ai;

import game.HitType;
import game.Map;

import java.awt.*;
import java.io.Serializable;

/**
 * Class for AI.
 */
public class AI extends AbstractAi implements Serializable {

    private Point lastShotPos;
    private AiStrategy strategy;

    /**
     * @param map        the {@link Map} of the enemy
     * @param difficulty the difficulty level
     */

    public AI(Map map, AiDifficulty difficulty) {
        super(map, difficulty);

        if (this.difficulty == AiDifficulty.FullRetard) {
            this.strategy = new FullRetardMode(map);
        }
        if (this.difficulty == AiDifficulty.Easy) {
            this.strategy = new EasyStrategy();
        }
        if (this.difficulty == AiDifficulty.Medium) {
            this.strategy = new HumanStrategy();
        }
        if (this.difficulty == AiDifficulty.Hard) {
            this.strategy = new HardStrategy(map);
        }
        if (this.difficulty == AiDifficulty.Extreme) {
            this.strategy = new AllMightyMode();
        }
    }

    /**
     * calls the process function of the certain strategy
     *
     * @return the chosen point of the strategy
     */
    @Override
    public Point shot() {
        return this.lastShotPos = this.strategy.process(this.map);
    }

    /**
     * calls the prepare function of the certain strategy
     *
     * @param hitType the answer to the hit point
     */
    @Override
    public void receiveAnswer(HitType hitType) {
        this.strategy.prepare(hitType, this.lastShotPos);
    }
}
