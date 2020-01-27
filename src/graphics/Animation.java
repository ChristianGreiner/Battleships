package graphics;

import core.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores and draws animation stuff.
 */
public class Animation {
    private int frameCount;                 // Counts ticks for change
    private int frameDelay;                 // frame delay 1-12 (You will have to play around with this)
    private int currentFrame;               // animations current frame
    private int animationDirection;         // animation direction (i.e counting forward or backward)
    private int totalFrames;                // total amount of frames for your animation
    private boolean stopped;                // has animations stopped
    private boolean looped = false;
    private List<SpriteFrame> frames = new ArrayList<SpriteFrame>();    // Arraylist of frames

    /**
     * The constructor of the animation.
     * @param frames Array of frames of the animation.
     * @param frameDelay The delay of frame.
     */
    public Animation(BufferedImage[] frames, int frameDelay) {
        this.frameDelay = frameDelay;
        this.stopped = true;

        for (int i = 0; i < frames.length; i++) {
            addFrame(frames[i], frameDelay);
        }

        this.frameCount = 0;
        this.currentFrame = 0;
        this.animationDirection = 1;
        this.totalFrames = this.frames.size();

        this.frameDelay = frameDelay;

        if(Game.getInstance().getTargetFps() == 60)
            this.frameDelay *= 2;

    }

    /**
     * Whenever or not the animation is stopped.
     * @return If the animation is stopped.
     */
    public boolean isStopped() {
        return stopped;
    }

    /**
     * Whenever or not the animation is looped.
     * @return If the animation is looped.
     */
    public boolean isLooped() {
        return looped;
    }

    /**
     * Gets the frames of the animation.
     * @return A list of frames.
     */
    public List<SpriteFrame> getFrames() {
        return frames;
    }

    /**
     * Starts the animation.
     */
    public void start() {
        if (!stopped) {
            return;
        }

        if (frames.size() == 0) {
            return;
        }

        stopped = false;
    }

    /**
     * Stops the animation.
     */
    public void stop() {
        if (frames.size() == 0) {
            return;
        }

        stopped = true;
    }

    /**
     * Restarts the animation.
     */
    public void restart() {
        if (frames.size() == 0) {
            return;
        }

        stopped = false;
        currentFrame = 0;
    }

    /**
     * Resets the animation.
     */
    public void reset() {
        this.stopped = true;
        this.frameCount = 0;
        this.currentFrame = 0;
    }

    private void addFrame(BufferedImage frame, int duration) {
        if (duration <= 0) {
            throw new RuntimeException("Invalid duration: " + duration);
        }

        frames.add(new SpriteFrame(frame, duration));
        currentFrame = 0;
    }

    /**
     * Gets the image of the current sprite.
     * @return
     */
    public BufferedImage getSprite() {
        return frames.get(currentFrame).getFrame();
    }

    /**
     * Updates the animation.
     */
    public void update() {
        if (!stopped) {
            frameCount++;

            if(!looped) {
                if (currentFrame > totalFrames - 1) {
                    currentFrame = 0;
                    stop();
                    return;
                }
            }

            if (frameCount > frameDelay) {
                frameCount = 0;
                currentFrame += animationDirection;

                if (currentFrame > totalFrames - 1) {
                    currentFrame = 0;
                    if (!looped) {
                        stop();
                    }
                } else if (currentFrame < 0) {
                    currentFrame = totalFrames - 1;
                }
            }
        }
    }

    /**
     * Draws the animation.
     * @param g The graphics element.
     * @param pos The position of the animation.
     * @param size The size of the image.
     */
    public void draw(Graphics g, Point pos, Point size) {
        g.drawImage(this.getSprite(), pos.x, pos.y, size.x, size.y, null);
    }
}
