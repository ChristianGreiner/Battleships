import core.Game;

import java.awt.*;

/**
 * The main class.
 */
public class Main {

    /**
     * The main method to start the game.
     * @param args
     */
    public static void main(String[] args) {

        Game game = new Game("Battleships", new Point(1280, 720));
        game.start();
    }
}
