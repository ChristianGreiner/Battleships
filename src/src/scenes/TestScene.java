package scenes;

import core.Drawable;
import core.Game;
import core.Renderer;
import core.Updatable;
import game.Map;

import java.awt.*;

public class TestScene extends Scene implements Updatable, Drawable {

    private Point mousePos = new Point();
    private Map playerMap;
    private Map enemyMap;

    public TestScene() {
        this.playerMap = new Map(new Point(10, 10), new Point(10, 10), new Point(32, 32));
        this.enemyMap = new Map(new Point(400, 10), new Point(10, 10), new Point(32, 32));
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void draw(Renderer renderer) {
        Point pos = Game.getInstance().getRenderer().getMousePosition();

        Graphics g = renderer.getDoubleBufferGraphics();

        if(pos != null)
            this.mousePos = pos;

        g.setColor(Color.WHITE);
        g.fillOval(mousePos.x, mousePos.y, 5, 5);

        this.playerMap.draw(renderer);
        this.enemyMap.draw(renderer);
    }
}
