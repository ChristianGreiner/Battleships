package core;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Renderer extends JPanel implements Drawable {

    private BufferedImage doubleBufferImage = null;
    private Graphics doubleBufferGraphics = null;

    public Renderer() {
        this.setBackground(Color.WHITE);
    }

    public Graphics begin(Rectangle clearRect) {
        if (this.doubleBufferImage == null) {

            int width = getWidth();
            int height = getHeight();

            if (width <= 0 || height <= 0) {
                width = 1;
                height = 1;
            }

            this.doubleBufferImage = (BufferedImage)createImage(width, height);
        }

        this.doubleBufferGraphics = this.doubleBufferImage.getGraphics();

        // clear image
        this.doubleBufferGraphics.setColor(Color.black);
        this.doubleBufferGraphics.clearRect(clearRect.x, clearRect.y, clearRect.width, clearRect.height);

        return this.doubleBufferGraphics;
    }

    public Graphics begin() {
        return this.begin(new Rectangle(0, 0, getWidth(), getHeight()));
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
