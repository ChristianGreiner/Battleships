package scenes;

import core.Drawable;
import core.Game;
import core.Updatable;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

public class SceneManager {

    public Scene getActiveScene() {
        return activeScene;
    }

    public void setActiveScene(Scene scene) {

        if(scene instanceof MouseListener)
            this.game.removeMouseListener((MouseListener)this.activeScene);

        if(scene instanceof KeyListener)
            this.game.removeKeyListener((KeyListener)this.activeScene);

        this.activeScene = scene;

        if(scene instanceof MouseListener)
            this.game.addMouseListener((MouseListener)scene);

        if(scene instanceof KeyListener)
            this.game.addKeyListener((KeyListener)scene);
    }

    private Scene activeScene;
    private Game game;

    public SceneManager(Game game) {
        this.game = game;
    }

    public void update(double deltaTime) {
        if (this.activeScene != null) {
            if (this.activeScene instanceof Updatable) {
                ((Updatable)this.activeScene).update(deltaTime);
            }
        }
    }

    public void draw(Graphics g) {
        if (this.activeScene != null) {
            if (this.activeScene instanceof Drawable) {
                ((Drawable)this.activeScene).draw(g);
            }
        }
    }
}
