package io;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.*;
import java.io.IOException;

public class AssetsLoader {

    public static int getAssetsLoaded() {
        return assetsLoaded;
    }

    private static int assetsLoaded = 0;

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

    public static Image loadImage(String imageName) {
        try {
            assetsLoaded++;
            return ImageIO.read(AssetsLoader.class.getClassLoader().getResourceAsStream(imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Clip loadSound(String soundName) {
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(AssetsLoader.class.getClassLoader().getResourceAsStream(soundName));
            Clip soundClip = AudioSystem.getClip();
            soundClip.open(audioInputStream);

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
