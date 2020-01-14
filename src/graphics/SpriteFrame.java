package graphics;

import java.awt.image.BufferedImage;

/**
 * Contains all data for a single sprite frame.
 */
public class SpriteFrame {

    private BufferedImage frame;
    private int duration;

    /**
     * The constructor of a sprite frame.
     * @param frame The buffered image frame.
     * @param duration The duration of the sprite frame.
     */
    public SpriteFrame(BufferedImage frame, int duration) {
        this.frame = frame;
        this.duration = duration;
    }

    /**
     * Gets the frame.
     * @return The buffered image frame.
     */
    public BufferedImage getFrame() {
        return frame;
    }
}