package scenes;

import core.Game;
import java.awt.*;

public class SceneManager {

    public Scene getActiveScene() {
        return activeScene;
    }

    public void setActiveScene(Scene scene) {

        this.game.removeKeyListener(this.activeScene);
        this.activeScene = scene;
        this.game.addKeyListener(scene);
    }

    private Scene activeScene;
    private Game game;

    public SceneManager(Game game) {
        this.game = game;
    }

    public void update(double deltaTime) {
        if (this.activeScene != null) {
            if (this.activeScene instanceof Updatable) {
                this.activeScene.update(deltaTime);
            }
        }
    }

    public void draw(Graphics g) {
        if (this.activeScene != null) {
            if (this.activeScene instanceof Drawable) {
                this.activeScene.draw(g);
            }
        }
    }
}
