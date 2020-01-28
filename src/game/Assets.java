package game;

import io.AssetsLoader;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Static class for storing all assets of the game.
 */
public final class Assets {

    public static void init() {

        // load images
        Images.BACKGROUND = AssetsLoader.loadImage(Assets.Paths.BACKGROUND);
        Images.EXPLOSION = AssetsLoader.loadBufferedImage(Assets.Paths.EXPLOSION);
        Images.WATER = AssetsLoader.loadBufferedImage(Assets.Paths.WATER);
        Images.WATER_DARK = AssetsLoader.loadBufferedImage(Assets.Paths.WATER_DARK);
        Images.SAND_LEFT = AssetsLoader.loadBufferedImage(Assets.Paths.SAND_LEFT);
        Images.SAND_TOP = AssetsLoader.loadBufferedImage(Assets.Paths.SAND_TOP);
        Images.SAND_TOP_LEFT = AssetsLoader.loadBufferedImage(Assets.Paths.SAND_TOP_LEFT);
        Images.CROSSHAIR = AssetsLoader.loadBufferedImage(Assets.Paths.CROSSHAIR);

        // load ships
        Images.SHIP_BATTLESHIP = AssetsLoader.loadBufferedImage(Assets.Paths.SHIP_BATTLESHIP);
        Images.SHIP_CARRIER = AssetsLoader.loadBufferedImage(Assets.Paths.SHIP_CARRIER);
        Images.SHIP_DESTROYER = AssetsLoader.loadBufferedImage(Assets.Paths.SHIP_DESTROYER);
        Images.SHIP_SUBMARINE = AssetsLoader.loadBufferedImage(Assets.Paths.SHIP_SUBMARINE);

        // load fonts
        String FONT = Paths.ROBOTO_FONT;

        Fonts.TITLE = AssetsLoader.loadFont(Paths.PLAY_FONT, 48f);
        Fonts.TITLE_36 = AssetsLoader.loadFont(Paths.PLAY_FONT, 36f);
        Fonts.TITLE_BIG = AssetsLoader.loadFont(Paths.PLAY_FONT, 64f);
        Fonts.DEFAULT = AssetsLoader.loadFont(FONT, 18f);
        Fonts.DEFAULT_BOLD = AssetsLoader.loadFont(Paths.ROBOTO_BOLD_FONT, 18f);
        Fonts.DEFAULT_24 = AssetsLoader.loadFont(FONT, 24f);
        Fonts.DEFAULT_BOLD_24 = AssetsLoader.loadFont(Paths.ROBOTO_BOLD_FONT, 24f);

        // load sounds
        Sounds.BUTTON_HOVER = AssetsLoader.loadSound(Paths.Button.HOVER_SFX);
        Sounds.BUTTON_CLICK = AssetsLoader.loadSound(Paths.Button.CLICK_SFX);
        Sounds.SHOT_SFX = AssetsLoader.loadSound(Paths.SHOT_SFX);
        Sounds.WATER_HIT = AssetsLoader.loadSound(Paths.WATER_HIT);
        Sounds.SHIP_HIT = AssetsLoader.loadSound(Paths.SHIP_HIT);
        Sounds.SHIP_EXPLOSION = AssetsLoader.loadSound(Paths.SHIP_EXPLOSION);

        // load music
        Sounds.MAINMENU_MUSIC = AssetsLoader.loadSound(Paths.MAINMENU_MUSIC);
        Sounds.PLAYING_MUSIC = AssetsLoader.loadSound(Paths.PLAYING_MUSIC);

        Files.MAPDATA = new File(Assets.class.getClassLoader().getResource(Paths.MAPDATA).getFile());

        MapGenerator.init();
    }

    /**
     * Storing all images.
     */
    public static class Images {
        public static Image BACKGROUND;
        public static BufferedImage EXPLOSION;
        public static Image WATER;
        public static Image WATER_DARK;
        public static Image SAND_LEFT;
        public static Image SAND_TOP;
        public static Image SAND_TOP_LEFT;
        public static Image CROSSHAIR;
        public static Image SHIP_BATTLESHIP;
        public static Image SHIP_CARRIER;
        public static Image SHIP_DESTROYER;
        public static Image SHIP_SUBMARINE;
    }

    /**
     * Storing all Sounds.
     */
    public static class Sounds {
        public static Clip MAINMENU_MUSIC;
        public static Clip PLAYING_MUSIC;
        public static Clip BUTTON_HOVER;
        public static Clip BUTTON_CLICK;
        public static Clip SHOT_SFX;
        public static Clip WATER_HIT;
        public static Clip SHIP_HIT;
        public static Clip SHIP_EXPLOSION;
    }

    /**
     * Storing all Fonts.
     */
    public static class Fonts {
        public static Font TITLE;
        public static Font TITLE_36;
        public static Font TITLE_BIG;
        public static Font DEFAULT;
        public static Font DEFAULT_BOLD;
        public static Font DEFAULT_24;
        public static Font DEFAULT_BOLD_24;
    }

    public static class Files {
        public static File MAPDATA;
    }

    /**
     * Storing all paths.
     */
    public class Paths {
        public final static String OPTIONS = "options.txt";
        public final static String MAPDATA = "mapdata.json";

        public final static String BACKGROUND = "images/MainMenuBackground.jpg";
        public final static String EXPLOSION = "images/Explosion.png";
        public final static String WATER = "images/tiles/water.png";
        public final static String WATER_DARK = "images/tiles/water_dark.png";
        public final static String SAND_LEFT = "images/tiles/sand_left.png";
        public final static String SAND_TOP = "images/tiles/sand_top.png";
        public final static String SAND_TOP_LEFT = "images/tiles/sand_top_left.png";
        public final static String CROSSHAIR = "images/ui/crosshair_black.png";
        public final static String SHIP_BATTLESHIP = "images/ships/battleship.png";
        public final static String SHIP_CARRIER = "images/ships/carrier.png";
        public final static String SHIP_DESTROYER = "images/ships/destroyer.png";
        public final static String SHIP_SUBMARINE = "images/ships/submarine.png";

        public final static String MAINMENU_MUSIC = "music/mainmenu.wav";
        public final static String PLAYING_MUSIC = "music/undiscovered.wav";

        public final static String ROBOTO_FONT = "fonts/Roboto-Regular.ttf";
        public final static String ROBOTO_BOLD_FONT = "fonts/Roboto-Bold.ttf";
        public final static String PLAY_FONT = "fonts/Play-Regular.ttf";
        public final static String SHOT_SFX = "sfx/shot.wav";
        public final static String WATER_HIT = "sfx/water_hit.wav";
        public final static String SHIP_HIT = "sfx/ship_hit.wav";
        public final static String SHIP_EXPLOSION = "sfx/ship_explosion.wav";

        public final class Button {
            public final static String HOVER_SFX = "sfx/button-hover.wav";
            public final static String CLICK_SFX = "sfx/button-click.wav";
        }
    }
}
