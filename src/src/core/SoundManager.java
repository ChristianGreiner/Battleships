package core;

public class SoundManager {

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
}
