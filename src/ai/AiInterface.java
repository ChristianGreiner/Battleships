package ai;

import game.HitType;

import java.awt.*;

/**
 * Interface for AI
 */
public interface AiInterface {

    /**
     * Gets an answer for the last shot
     *
     * @param hitType hittyoe of last hit pos
     */
    void receiveAnswer(HitType hitType);

    /**
     * Calls the AI to shot
     *
     * @return {@link Point} calculated Point of AI
     */
    Point shot();
}
