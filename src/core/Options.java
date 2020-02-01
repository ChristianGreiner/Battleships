package core;

import java.io.Serializable;

/**
 * The options class used for storing the options of the game.
 */
public class Options implements Serializable {
    private float sfxVolume = .5f;
    private float musicVolume = .5f;
    private int fps = 60;

    /**
     * Gets the sfx volume.
     *
     * @return The volume as float.
     */
    public float getSfxVolume() {
        return sfxVolume;
    }

    /**
     * Sets the sfx volume.
     *
     * @param sfxVolume The volume as float.
     */
    public void setSfxVolume(float sfxVolume) {
        this.sfxVolume = sfxVolume;
    }

    /**
     * Gets the music volume.
     *
     * @return The volume as float.
     */
    public float getMusicVolume() {
        return musicVolume;
    }

    /**
     * Sets the music volume.
     *
     * @param musicVolume The volume as float.
     */
    public void setMusicVolume(float musicVolume) {
        this.musicVolume = Helper.limit(musicVolume, 0, 1);
    }

    /**
     * Gets the target fps of the game.
     *
     * @return The fps.
     */
    public int getFps() {
        return fps;
    }

    /**
     * Sets the target fps of the game.
     *
     * @param fps The fps.
     */
    public void setFps(int fps) {
        this.fps = fps;
    }
}
