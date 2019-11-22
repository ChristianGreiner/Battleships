package ai;

import game.HitType;
import game.Map;

import java.awt.*;

public class AI extends AbstractAi {

    private Point lastShotPos;
    private Point lastHit;
    private Point nextHit;
    private AiStrategy strategy;

    public AI(Map map, AiDifficulty difficulty) {
        super(map, difficulty);

        if (this.difficulty == AiDifficulty.Medium) {
            this.strategy = new HumanStrategy();
        }
        if(this.difficulty == AiDifficulty.Easy){
            this.strategy = new EasyStrategy();
        }
    }

    @Override
    public Point shot() {

        Point shotPoint = this.strategy.process(this.map);

        this.lastShotPos = shotPoint;

        return shotPoint;
    }

    @Override
    public void receiveAnswer(HitType hitType) {
        Point shotPos;
        shotPos = this.strategy.prepare(hitType, this.lastShotPos);

        if (shotPos != null) {
            lastHit = shotPos;
        }
    }
}
