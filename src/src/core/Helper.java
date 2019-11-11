package core;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class Helper {
    public static int randomNumber(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    public static boolean randomBoolean() {
        return randomNumber(0, 1) == 1;
    }

    public static Alignment getRandomAlignment() {
        int alignNumber = randomNumber(0, 1);

        if (alignNumber == 0)
            return Alignment.Vertical;

        return Alignment.Horizontal;
    }

    public static Direction getRandomDirection() {
        int dirNumber = randomNumber(0, 3);

        if (dirNumber == 0)
            return Direction.Up;
        else if (dirNumber == 1)
            return Direction.Right;
        else if (dirNumber == 2)
            return Direction.Down;
        else
            return Direction.Left;
    }

    /**
     * Draw a String centered in the middle of a Rectangle.
     *
     * @param g The Graphics instance.
     * @param text The String to draw.
     * @param rect The Rectangle to center the text in.
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
}
