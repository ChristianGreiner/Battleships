package scenes;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TestScene extends Scene implements Updatable, Drawable {

    private Point pos = new Point();

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void draw(Graphics g) {

        g.setColor(Color.WHITE);
        g.fillRect(pos.x, 40, 32, 32);

        super.draw(g);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            pos.x -= 1;
        }

        if (key == KeyEvent.VK_RIGHT) {
            pos.x += 1;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
