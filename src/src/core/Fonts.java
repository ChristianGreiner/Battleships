package core;

import game.Assets;

import java.awt.*;
import java.io.IOException;

public class Fonts {

    public static Font TITLE;
    public static Font DEFAULT;
    public static Font DEFAULT_18;
    public static Font DEFAULT_24;

    public Fonts() {
        try {
            TITLE = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream(Assets.MAIN_FONT)).deriveFont(48f);
            DEFAULT = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream(Assets.MAIN_FONT)).deriveFont(16f);
            DEFAULT_18 = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream(Assets.MAIN_FONT)).deriveFont(18f);
            DEFAULT_24 = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream(Assets.MAIN_FONT)).deriveFont(24f);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
