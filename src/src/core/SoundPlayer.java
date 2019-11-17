package core;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundPlayer {

    private Clip soundClip;

    public SoundPlayer(Clip soundClip) {
        this.soundClip = soundClip;
    }

    public void setVolume(float value) {
        if(this.soundClip != null) {
            FloatControl gainControl  = (FloatControl) this.soundClip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(value) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
    }

    public void play(float volume) {
        this.play();
        if(this.soundClip != null)
            this.setVolume(volume);

    }

    public void play() {
        if(this.soundClip != null) {
            this.soundClip.setFramePosition(0);
            this.soundClip.start();
        }
    }

    public void stop() {
        if(this.soundClip != null)
            this.soundClip.stop();
    }
}
