package game;

import io.AssetsLoader;

import javax.sound.sampled.Clip;
import java.awt.*;

public final class Assets {

    public static class Images {
        public static Image BACKGROUND;
    }

    public static class Sounds {
        public static Clip BACKGROUND_MUSIC;
        public static Clip BUTTON_HOVER;
        public static Clip BUTTON_CLICK;
        public static Clip SEATBELT_SFX;
    }

    public static class Fonts {
        public static Font TITLE;
        public static Font TITLE_BIG;
        public static Font DEFAULT;
        public static Font DEFAULT_18;
        public static Font DEFAULT_24;
        public static Font DEFAULT_BOLD_24;
    }

    public static void init() {

        // load images
        Images.BACKGROUND =  AssetsLoader.loadImage(Assets.Paths.BACKGROUND);

        // load fonts
        Fonts.TITLE = AssetsLoader.loadFont(Assets.Paths.OXYGEN_FONT, 48f);
        Fonts.TITLE_BIG = AssetsLoader.loadFont(Assets.Paths.PLAY_FONT, 64f);
        Fonts.DEFAULT = AssetsLoader.loadFont(Assets.Paths.OXYGEN_FONT, 16f);
        Fonts.DEFAULT_18 = AssetsLoader.loadFont(Assets.Paths.OXYGEN_FONT, 18f);
        Fonts.DEFAULT_24 = AssetsLoader.loadFont(Assets.Paths.OXYGEN_FONT, 24f);
        Fonts.DEFAULT_BOLD_24 = AssetsLoader.loadFont(Assets.Paths.OXYGEN_BOLD_FONT, 24f);

        // load sounds
        Sounds.BUTTON_HOVER = AssetsLoader.loadSound(Paths.Button.HOVER_SFX);
        Sounds.BUTTON_CLICK = AssetsLoader.loadSound(Paths.Button.CLICK_SFX);

        // load music
        Sounds.BACKGROUND_MUSIC = AssetsLoader.loadSound(Paths.BACKGROUND_2_MUSIC);
        Sounds.SEATBELT_SFX = AssetsLoader.loadSound(Paths.SEATBELT_SFX);
    }

    public class Paths {
        public final static String OPTIONS = "options.txt";

        public final static String BACKGROUND = "MainMenuBackground.jpg";
        public final static String BACKGROUND_1_MUSIC = "music/we-will-win-sc1.wav";
        public final static String BACKGROUND_2_MUSIC = "music/redemption-sc1.wav";

        public final static String PLAY_FONT = "fonts/Play-Regular.ttf";
        public final static String OXYGEN_FONT = "fonts/Oxygen-Regular.ttf";
        public final static String OXYGEN_BOLD_FONT = "fonts/Oxygen-Bold.ttf";
        public final static String SEATBELT_SFX = "sfx/seatbelt.wav";

        public final class Button {
            public final static String HOVER_SFX = "sfx/button-hover.wav";
            public final static String CLICK_SFX = "sfx/button-click.wav";
        }
    }
}
