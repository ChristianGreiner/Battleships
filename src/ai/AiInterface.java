package ai;

import game.HitType;

import java.awt.*;

public interface AiInterface {

    void receiveAnswer(HitType hitType);

    Point shot();
}
