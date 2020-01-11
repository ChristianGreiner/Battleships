package game;

import java.awt.*;

/**
 * A container for displaying credits.
 */
public class Credit {
    private String text;
    private Font font;

    public Credit(String text, Font font) {
        this.text = text;
        this.font = font;
    }

    public Font getFont() {
        return font;
    }

    public String getText() {
        return text;
    }
}
