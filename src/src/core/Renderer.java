package core;


import javax.swing.*;
import java.awt.*;

public class Renderer extends JPanel implements Drawable {

    private Image doubleBufferImage = null;
    private Graphics doubleBufferGraphics = null;
    private boolean initialized = false;

    public Renderer() {
        this.setBackground(Color.black);
    }

    public Graphics begin() {

        if (doubleBufferImage == null) {

            int width = getWidth();
            int height = getHeight();

            if(width <= 0 || height <= 0) {
                width = 1;
                height = 1;
            }

            doubleBufferImage = createImage(width, height);
        }
        doubleBufferGraphics = doubleBufferImage.getGraphics();

        // clear image
        doubleBufferGraphics.setColor(Color.black);
        doubleBufferGraphics.clearRect(0, 0, getWidth(), getHeight());

        return doubleBufferGraphics;
    }

    public void end() {
        this.repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (this.doubleBufferGraphics != null && this.doubleBufferImage != null) {
            g.drawImage(doubleBufferImage, 0, 0, null);
        }
    }

    @Override
    public void draw() {
    }
}
