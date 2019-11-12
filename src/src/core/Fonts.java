package core;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Fonts {

    public static Font TITLE;
    public static Font DEFAULT;

    public Fonts() {
        try {
            TITLE = Font.createFont(Font.TRUETYPE_FONT, new File(getClass().getClassLoader().getResource("Play-Regular.ttf").getFile())).deriveFont(48f);
            DEFAULT = Font.createFont(Font.TRUETYPE_FONT, new File(getClass().getClassLoader().getResource("Play-Regular.ttf").getFile())).deriveFont(16f);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
