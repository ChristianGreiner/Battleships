package core;

import javax.sound.sampled.Clip;

public class SoundManager {

    public SoundPlayer getBackgroundPlayer() {
        return backgroundPlayer;
    }

    private SoundPlayer backgroundPlayer;

    public SoundManager() {
    }

    public void playBackgroundMusic(Clip audioClip) {
        if(this.backgroundPlayer == null) {
            this.backgroundPlayer = new SoundPlayer(audioClip);
            this.backgroundPlayer.play(Game.getInstance().getOptions().getMusicVolume());
        }
    }

    public void stopBackgroundMusic() {
        this.backgroundPlayer.stop();
    }

    public void playSfx(Clip audioClip) {
        SoundPlayer soundPlayer = new SoundPlayer(audioClip);
        soundPlayer.play(Game.getInstance().getOptions().getSfxVolume());
    }


    public void playSfx(Clip audioClip, float volume) {
        SoundPlayer soundPlayer = new SoundPlayer(audioClip);
        soundPlayer.play(volume);
    }

}
