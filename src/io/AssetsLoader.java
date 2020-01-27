package io;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Loads different types of assets.
 */
public class AssetsLoader {

    private static int assetsLoaded = 0;

    /**
     * Static assets loader reference.
     * @return The assets loader.
     */
    public static int getAssetsLoaded() {
        return assetsLoaded;
    }

    /**
     * Loads a font.
     * @param fontName The font name
     * @param fontSize The font size.
     * @return The font.
     */
    public static Font loadFont(String fontName, float fontSize) {
        try {
            assetsLoaded++;
            return Font.createFont(Font.TRUETYPE_FONT, AssetsLoader.class.getClassLoader().getResourceAsStream(fontName)).deriveFont(fontSize);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Loads an image.
     * @param imageName The image name.
     * @return The image.
     */
    public static Image loadImage(String imageName) {
        try {
            assetsLoaded++;
            return ImageIO.read(AssetsLoader.class.getClassLoader().getResourceAsStream(imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Loads a buffered image.
     * @param imageName The name of the image.
     * @return The buffered image.
     */
    public static BufferedImage loadBufferedImage(String imageName) {
        try {
            assetsLoaded++;
            return ImageIO.read(AssetsLoader.class.getClassLoader().getResourceAsStream(imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Loads a clip (sound).
     * @param soundName The sound name.
     * @return The clip.
     */
    public static Clip loadSound(String soundName) {
        try {
            AudioInputStream  input = AudioSystem.getAudioInputStream(AssetsLoader.class.getClassLoader().getResource(soundName));

            Clip soundClip = AudioSystem.getClip();
            soundClip.open(input);

            assetsLoaded++;

            return soundClip;
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        return null;
    }
}
