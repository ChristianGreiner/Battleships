package core;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

public class Helper {
    private static Random r = new Random();

    /**
     * Returns a random number between a range.
     *
     * @param min The min range.
     * @param max The max range.
     * @return The random number.
     */
    public static int randomNumber(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    /**
     * Gets a random float number between to nubmers.
     *
     * @param min The min number.
     * @param max The max number.
     * @return The number.
     */
    public static float randFloat(float min, float max) {
        return r.nextFloat() * (max - min) + min;
    }

    /**
     * Gets a random number between to numbers.
     *
     * @param min The min value.
     * @param max The max value.
     * @return The number.
     */
    public static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        return r.nextInt((max - min) + 1) + min;
    }

    /**
     * Returns a random boolean.
     *
     * @return The random boolean.
     */
    public static boolean randomBoolean() {
        return randomNumber(0, 1) == 1;
    }

    /**
     * Returns a random alignment.
     *
     * @return Ther random alignment.
     */
    public static Alignment getRandomAlignment() {
        int alignNumber = randomNumber(0, 1);

        if (alignNumber == 0)
            return Alignment.Vertical;

        return Alignment.Horizontal;
    }

    /**
     * Gets a random direction
     *
     * @param align The alignment.
     * @return The random direction.
     */
    public static Direction getRandomDirection(Alignment align) {

        int dirNumber = getRandomNumberInRange(0, 1);

        if (align == Alignment.Horizontal) {
            if (dirNumber == 0)
                return Direction.Left;
            return Direction.Right;
        } else {
            if (dirNumber == 0)
                return Direction.Up;
            return Direction.Down;
        }

    }

    public static Point getRandomFreeIndex(ArrayList map) {
        return (Point) map.get(r.nextInt(map.size()));
    }

    /**
     * Draw a String centered in the middle of a Rectangle.
     *
     * @param g    The Graphics instance.
     * @param text The String to draw.
     * @param rect The Rectangle to center the text in.
     * @param font The Font to use when drawing the text
     */
    public static void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        FontRenderContext frc = new FontRenderContext(null, true, true);

        Rectangle2D r2D = font.getStringBounds(text, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        int rX = (int) Math.round(r2D.getX());
        int rY = (int) Math.round(r2D.getY());

        int a = (rect.width / 2) - (rWidth / 2) - rX;
        int b = (rect.height / 2) - (rHeight / 2) - rY;

        g.setFont(font);
        g.drawString(text, rect.x + a, rect.y + b);
    }

    /**
     * Draw a String that's aligned with the left side of a Rectangle
     *
     * @param g    The Graphics instance.
     * @param text The String to draw.
     * @param rect The bounding box for the text
     * @param font The Font to use when drawing the text
     */
    public static void drawLeftAlignedString(Graphics g, String text, Rectangle rect, Font font) {
        FontRenderContext frc = new FontRenderContext(null, true, true);

        Rectangle2D r2D = font.getStringBounds(text, frc);
        int rHeight = (int) Math.round(r2D.getHeight());
        int rY = (int) Math.round(r2D.getY());

        int b = (rect.height / 2) - (rHeight / 2) - rY;

        g.setFont(font);
        g.drawString(text, rect.x, rect.y + b);
    }

    /**
     * Limits a float value between min and max.
     *
     * @param value The value.
     * @param min   The min value.
     * @param max   The max value.
     * @return The limitted value.
     */
    public static float limit(float value, float min, float max) {
        return (value > max) ? max : (value < min ? min : value);
    }

    /**
     * Check regex for a ip adress.
     *
     * @param ip The ip adress.
     * @return The validation.
     */
    public static boolean validateIp(final String ip) {
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";

        return ip.matches(PATTERN) || ip.equals("localhost");
    }
}
