package ai;

import game.HitType;
import game.Map;

import java.awt.*;

public class AI extends AbstractAi {

    private Point lastShotPos;
    private AiStrategy strategy;

    public AI(Map map, AiDifficulty difficulty) {
        super(map, difficulty);

        if (this.difficulty == AiDifficulty.Medium) {
            this.strategy = new HumanStrategy();
        }
    }

    @Override
    public Point shot() {
        return this.lastShotPos = this.strategy.process(this.map);
    }

    @Override
    public void receiveAnswer(HitType hitType) {
        this.strategy.prepair(hitType, this.lastShotPos);
    }
}
