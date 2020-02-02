package core;

import game.Assets;
import game.HitType;

import javax.sound.sampled.Clip;

/**
 * A simple sound manager for playing sounds and music.
 */
public class SoundManager {

    private SoundPlayer backgroundPlayer;

    public SoundPlayer getBackgroundPlayer() {
        return backgroundPlayer;
    }

    /**
     * Plays a music in the background.
     *
     * @param audioClip    The music audio clip.
     * @param forceRestart Whenever or not the background player should restart the background music.
     */
    public void playBackgroundMusic(Clip audioClip, boolean forceRestart) {
        if (this.backgroundPlayer != null && forceRestart) {
            this.backgroundPlayer.stop();
            this.backgroundPlayer = null;
        }
        if (this.backgroundPlayer == null) {
            this.backgroundPlayer = new SoundPlayer(audioClip);
            this.backgroundPlayer.play(Game.getInstance().getOptions().getMusicVolume(), true);
        }
    }

    /**
     * Stops the current playing background music.
     */
    public void stopBackgroundMusic() {
        this.backgroundPlayer.stop();
        this.backgroundPlayer = null;
    }

    /**
     * Plays a sound fx.
     *
     * @param audioClip The sfx audio clip.
     */
    public void playSfx(Clip audioClip) {
        SoundPlayer soundPlayer = new SoundPlayer(audioClip);
        soundPlayer.play(Game.getInstance().getOptions().getSfxVolume());
    }

    /**
     * Plays the sound according to the hit type.
     *
     * @param hitType The hit type
     */
    public void handleHitSoundFx(HitType hitType) {
        if (hitType == HitType.Water) {
            Game.getInstance().getSoundManager().playSfx(Assets.Sounds.WATER_HIT);
        } else if (hitType == HitType.Ship) {
            Game.getInstance().getSoundManager().playSfx(Assets.Sounds.SHIP_HIT);
        } else if (hitType == HitType.ShipDestroyed) {
            Game.getInstance().getSoundManager().playSfx(Assets.Sounds.SHIP_EXPLOSION);
        }
    }
}
