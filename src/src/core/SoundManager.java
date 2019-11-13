package core;

public class SoundManager {

    public SoundPlayer getBackgroundPlayer() {
        return backgroundPlayer;
    }

    private SoundPlayer backgroundPlayer;

    public SoundManager() {
    }

    public void playBackgroundMusic(String fileName) {
        if(this.backgroundPlayer == null) {
            this.backgroundPlayer = new SoundPlayer(fileName);
            this.backgroundPlayer.play();
        }
    }

    public void stopBackgroundMusic() {
        this.backgroundPlayer.stop();
    }

    public void playSfx(String fileName) {
        SoundPlayer soundPlayer = new SoundPlayer(fileName);
        soundPlayer.play(Game.getInstance().getOptions().getSfxVolume());
    }

    public void playSfx(String fileName, float volume) {
        SoundPlayer soundPlayer = new SoundPlayer(fileName);
        soundPlayer.play(volume);
    }
}
