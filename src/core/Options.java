package core;

import ai.AiSpeed;

import java.io.Serializable;

/**
 * The options class used for storing the options of the game.
 */
public class Options implements Serializable {
    private float sfxVolume = .5f;
    private float musicVolume = .5f;
    private int fps = 60;
    private AiSpeed aiSpeed = AiSpeed.Normal;
    private float aiSpeedValue = 1.2f;

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

    /**
     * Gets the ai speed.
     *
     * @return The speed of the ai.
     */
    public AiSpeed getAiSpeed() {
        return aiSpeed;
    }

    /**
     * Sets the a thinking speed.
     *
     * @param aiSpeed The speed.
     */
    public void setAiSpeed(AiSpeed aiSpeed) {
        this.aiSpeed = aiSpeed;

        switch (aiSpeed) {
            case Extreme:
                this.aiSpeedValue = 0.1f;
                break;
            case Fast:
                this.aiSpeedValue = 0.5f;
                break;
            case Slow:
                this.aiSpeedValue = 1.5f;
                break;
            case Normal:
                this.aiSpeedValue = 1.2f;
                break;
        }

    }

    /**
     * Gets the ai speed as value for calculation.
     *
     * @return The ai speed as value.
     */
    public float getAiSpeedValue() {
        return this.aiSpeedValue;
    }
}
