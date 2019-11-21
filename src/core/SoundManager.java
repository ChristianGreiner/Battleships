package core;

import javax.sound.sampled.Clip;

public class SoundManager {

    public SoundPlayer getBackgroundPlayer() {
        return backgroundPlayer;
    }

    private SoundPlayer backgroundPlayer;

    public SoundManager() {
    }

    public void playBackgroundMusic(Clip audioClip, boolean forceRestart) {
        if(this.backgroundPlayer != null && forceRestart) {
            this.backgroundPlayer.stop();
            this.backgroundPlayer = null;
        }
        if(this.backgroundPlayer == null) {
            this.backgroundPlayer = new SoundPlayer(audioClip);
            this.backgroundPlayer.play(Game.getInstance().getOptions().getMusicVolume(), true);
        }
    }

    public void stopBackgroundMusic() {
        this.backgroundPlayer.stop();
        this.backgroundPlayer = null;
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
