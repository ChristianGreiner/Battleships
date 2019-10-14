package scenes;

import core.Drawable;
import core.Game;
import core.Updatable;
import core.Renderer;

import java.awt.*;

public class TestScene extends Scene implements Updatable, Drawable {

    private Point mousePos = new Point();

    public TestScene() {
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
        g.fillRect(mousePos.x, mousePos.y, 32, 32);
    }
}
