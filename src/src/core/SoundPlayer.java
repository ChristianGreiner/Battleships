package core;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer {

    private File soundFile;
    private Clip soundClip;

    public SoundPlayer(String fileName) {
        this.soundFile = new File(getClass().getClassLoader().getResource(fileName).getFile());
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
        AudioInputStream audioIn = null;
        try {
            audioIn = AudioSystem.getAudioInputStream(soundFile.getAbsoluteFile());
            this.soundClip = AudioSystem.getClip();
            this.soundClip.open(audioIn);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.soundClip.start();
    }

    public void stop() {
        if(this.soundClip != null)
            this.soundClip.stop();
    }
}
