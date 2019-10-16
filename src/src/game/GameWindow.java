package game;

import core.Game;
import core.Renderer;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    final int TARGET_FPS = 60;
    final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

    private Renderer renderer = null;
    private Game game;

    public GameWindow(Game game, String title, Point size) {
        this.game = game;
        this.renderer = new Renderer();

        this.setLayout(null);
        this.setPreferredSize(new Dimension(size.x, size.y));
        this.setBackground(Color.WHITE);
        this.setTitle(title);
        this.setSize(size.x, size.y);
        this.setResizable(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.renderer.setBackground(Color.black);
        this.renderer.setSize(size.x, size.y);
        this.renderer.setLocation(0, 0);
        this.add(this.renderer, BorderLayout.CENTER);

        this.pack();
        this.setLocationRelativeTo(null);
    }

}
