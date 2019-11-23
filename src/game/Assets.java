package game;

import io.AssetsLoader;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.io.File;

public final class Assets {

    public static class Images {
        public static Image BACKGROUND;
        public static Image TILESET;
    }

    public static class Sounds {
        public static Clip BACKGROUND_MUSIC;
        public static Clip PLAYING_MUSIC;
        public static Clip BUTTON_HOVER;
        public static Clip BUTTON_CLICK;
        public static Clip SEATBELT_SFX;
        public static Clip SHOT_SFX;
    }

    public static class Fonts {
        public static Font TITLE;
        public static Font TITLE_BIG;
        public static Font DEFAULT;
        public static Font DEFAULT_24;
        public static Font DEFAULT_BOLD_24;
    }

    public static class Files {
        public static File MAPDATA;
    }

    public static void init() {

        // load images
        Images.BACKGROUND =  AssetsLoader.loadImage(Assets.Paths.BACKGROUND);
        Images.TILESET = AssetsLoader.loadImage(Assets.Paths.TILESET);

        // load fonts

        String FONT = Paths.ROBOTO_FONT;

        Fonts.TITLE = AssetsLoader.loadFont(Paths.PLAY_FONT, 48f);
        Fonts.TITLE_BIG = AssetsLoader.loadFont(Paths.PLAY_FONT, 64f);
        Fonts.DEFAULT = AssetsLoader.loadFont(FONT, 18f);
        Fonts.DEFAULT_24 = AssetsLoader.loadFont(FONT, 24f);
        Fonts.DEFAULT_BOLD_24 = AssetsLoader.loadFont(FONT, 24f);

        // load sounds
        Sounds.BUTTON_HOVER = AssetsLoader.loadSound(Paths.Button.HOVER_SFX);
        Sounds.BUTTON_CLICK = AssetsLoader.loadSound(Paths.Button.CLICK_SFX);
        Sounds.SHOT_SFX = AssetsLoader.loadSound(Paths.SHOT_SFX);

        // load music
        Sounds.BACKGROUND_MUSIC = AssetsLoader.loadSound(Paths.BACKGROUND_2_MUSIC);
        Sounds.PLAYING_MUSIC = AssetsLoader.loadSound(Paths.PLAYING_MUSIC);
        Sounds.SEATBELT_SFX = AssetsLoader.loadSound(Paths.SEATBELT_SFX);

        Files.MAPDATA = new File(Assets.class.getClassLoader().getResource(Paths.MAPDATA).getFile());
    }

    public class Paths {
        public final static String OPTIONS = "options.txt";
        public final static String MAPDATA = "mapdata.json";

        public final static String BACKGROUND = "images/MainMenuBackground.jpg";
        public final static String TILESET = "images/tileset.png";

        public final static String BACKGROUND_1_MUSIC = "music/we-will-win-sc1.wav";
        public final static String BACKGROUND_2_MUSIC = "music/redemption-sc1.wav";
        public final static String PLAYING_MUSIC = "music/undiscovered.wav";

        public final static String ROBOTO_FONT = "fonts/Roboto-Regular.ttf";
        public final static String ROBOTO_BOLD_FONT = "fonts/Roboto-Bold.ttf";
        public final static String PLAY_FONT = "fonts/Play-Regular.ttf";
        public final static String SEATBELT_SFX = "sfx/seatbelt.wav";
        public final static String SHOT_SFX = "sfx/shot.wav";



        public final class Button {
            public final static String HOVER_SFX = "sfx/button-hover.wav";
            public final static String CLICK_SFX = "sfx/button-click.wav";
        }
    }
}
