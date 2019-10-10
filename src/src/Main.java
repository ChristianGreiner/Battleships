import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Game game = new Game("Battleships", new Point(1280, 720));
        game.run();
    }
}
