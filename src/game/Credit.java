package game;

import java.awt.*;

/**
 * A container for displaying credits.
 */
public class Credit {

    private String text;
    private Font font;

    /**
     * Creates a new credit container.
     *
     * @param text The text.
     * @param font The font.
     */
    public Credit(String text, Font font) {
        this.text = text;
        this.font = font;
    }

    /**
     * Gets the font of the credit.
     *
     * @return The font
     */
    public Font getFont() {
        return font;
    }

    /**
     * Gehts the text of the credit.
     *
     * @return The text.
     */
    public String getText() {
        return text;
    }
}
