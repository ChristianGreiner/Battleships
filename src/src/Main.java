import core.Game;

import java.awt.*;

public class Main {

    public static void main(String[] args) {

        Game game = new Game("Battleships", new Point(640, 360));
        game.start();
    }
}
