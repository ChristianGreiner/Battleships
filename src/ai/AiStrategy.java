package ai;

import game.HitType;
import game.Map;

import java.awt.*;

public interface AiStrategy {

    Point prepair(HitType type, Point lastHit);

    Point process(Map map);

}
