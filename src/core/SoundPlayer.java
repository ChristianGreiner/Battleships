package core;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * A wrapper for the swing audio clip.
 */
public class SoundPlayer {

    private Clip soundClip;

    public SoundPlayer(Clip soundClip) {
        this.soundClip = soundClip;
    }

    /**
     * Sets the volume ot the player.
     *
     * @param value The volume.
     */
    public void setVolume(float value) {
        if (this.soundClip != null) {
            FloatControl gainControl = (FloatControl) this.soundClip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(value) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
    }

    /**
     * Plays the sound clip.
     *
     * @param volume The volume of the clip.
     */
    public void play(float volume) {
        this.play();
        if (this.soundClip != null)
            this.setVolume(volume);

    }

    /**
     * Plays the sound clip.
     */
    public void play() {
        if (this.soundClip != null) {
            this.soundClip.setFramePosition(0);
            this.soundClip.start();
        }
    }

    /**
     * Plays the sound clip.
     *
     * @param volume The volume of the clip.
     * @param loop   Whenever or not the sound clip should be looped.
     */
    public void play(float volume, boolean loop) {
        this.play();
        if (this.soundClip != null) {
            this.setVolume(volume);

            if (loop)
                this.soundClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * Stops the sound.
     */
    public void stop() {
        if (this.soundClip != null)
            this.soundClip.stop();
    }
}
