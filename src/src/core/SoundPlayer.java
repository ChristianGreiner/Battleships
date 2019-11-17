package core;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class SoundPlayer {

    private Clip soundClip;
    private AudioInputStream audioInputStream;
    private InputStream inputStream;

    public SoundPlayer(String fileName) {
        this.inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
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
        try {
            this.audioInputStream = AudioSystem.getAudioInputStream(inputStream);
            this.soundClip = AudioSystem.getClip();
            this.soundClip.open(this.audioInputStream);
            this.soundClip.start();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if(this.soundClip != null)
            this.soundClip.stop();
    }
}
