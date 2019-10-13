package scenes;

import core.Drawable;
import core.Game;
import core.Updatable;

import java.awt.*;

public class TestScene extends Scene implements Updatable, Drawable {

    private Point mousePos = new Point();

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void draw(Graphics g) {

         Point pos = Game.getInstance().getRenderer().getMousePosition();

        if(pos != null)
            this.mousePos = pos;

        g.setColor(Color.WHITE);
        g.fillRect(mousePos.x, mousePos.y, 32, 32);
    }
}
