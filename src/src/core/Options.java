package core;

import java.io.Serializable;

public class Options implements Serializable {
    public float getSfxVolume() {
        return sfxVolume;
    }

    public void setSfxVolume(float sfxVolume) {
        this.sfxVolume = sfxVolume;
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(float musicVolume) {
        this.musicVolume = Helper.limit(musicVolume, 0, 1);
    }

    private float sfxVolume = 1f;
    private float musicVolume = 1f;

}
