package ai;

import game.HitType;
import game.Map;

import java.awt.*;

public class AI extends AbstractAi {

    private Point lastShotPos;
    private AiStrategy strategy;

    public AI(Map map, AiDifficulty difficulty) {
        super(map, difficulty);

        if(this.difficulty == AiDifficulty.Hard){
            this.strategy = new HardStrategy(map);
        }

        if (this.difficulty == AiDifficulty.Medium) {
            this.strategy = new HumanStrategy();
        }
        if(this.difficulty == AiDifficulty.Easy){
            this.strategy = new EasyStrategy();
        }
    }

    @Override
    public Point shot() {
        return this.lastShotPos = this.strategy.process(this.map);
    }

    @Override
    public void receiveAnswer(HitType hitType) {
        this.strategy.prepare(hitType, this.lastShotPos);
    }
}
