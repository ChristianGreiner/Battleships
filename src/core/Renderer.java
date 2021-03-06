package core;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A rendering panel used drawing stuff on it really fast.
 * Has double buffer included.
 */
public class Renderer extends JPanel implements Drawable, Updatable {

    private boolean isDirty = false;
    private BufferedImage doubleBufferImage = null;
    private Graphics2D doubleBufferGraphics = null;

    public Renderer() {
        this.setBackground(Color.WHITE);
    }

    /**
     * When rendering stuff, begin has called first, then drawing, then call end().
     *
     * @param clearRect The rectangle where the image should be cleard.
     * @return The swing graphics element.
     */
    public Graphics2D begin(Rectangle clearRect) {
        isDirty = true;
        if (this.doubleBufferImage == null) {

            int width = getWidth();
            int height = getHeight();

            if (width <= 0 || height <= 0) {
                width = 1;
                height = 1;
            }

            this.doubleBufferImage = (BufferedImage) createImage(width, height);
        }

        this.doubleBufferGraphics = this.doubleBufferImage.createGraphics();

        // clear image
        this.doubleBufferGraphics.setColor(Color.BLACK);
        this.doubleBufferGraphics.clearRect(clearRect.x, clearRect.y, clearRect.width, clearRect.height);

        isDirty = false;

        return this.doubleBufferGraphics;
    }

    /**
     * When rendering stuff, begin has called first, then drawing, then call end().
     *
     * @return The swing graphics element.
     */
    public Graphics2D begin() {
        return this.begin(new Rectangle(0, 0, getWidth(), getHeight()));
    }

    /**
     * Has to be called after the begin() method. Repaints the image.
     */
    public void end() {
        this.repaint();
    }

    /**
     * Paints the image.
     *
     * @param g The graphics element.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (this.doubleBufferGraphics != null && this.doubleBufferImage != null) {
            g.drawImage(doubleBufferImage, 0, 0, null);
        }
    }

    public void invalidateBuffer() {
        if (!isDirty) {
            this.doubleBufferImage = null;
            this.doubleBufferGraphics = null;
        }
    }

    @Override
    public void draw() {
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void lateUpdate(double deltaTime) {
    }
}
