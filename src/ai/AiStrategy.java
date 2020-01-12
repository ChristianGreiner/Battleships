package ai;

import game.HitType;
import game.Map;

import java.awt.*;

public interface AiStrategy {

    /**
     * Prepare the AI for the next call of process function by adapting variables.
     *
     * @param type    the answer to the last hitten point
     * @param lastHit the location of the last point.
     */
    void prepare(HitType type, Point lastHit);

    /**
     * Calculating the next point to shoot following a certain strategy.
     *
     * @param map the current map of the player in the game.
     * @return the calculated point
     */
    Point process(Map map);

}
