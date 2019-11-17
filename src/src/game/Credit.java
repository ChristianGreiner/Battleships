package game;

import java.awt.*;

public class Credit {
    public Font getFont() {
        return font;
    }

    public Credit(String text, Font font) {
        this.text = text;
        this.font = font;
    }

    public String getText() {
        return text;
    }

    private String text;
    private Font font;
}
