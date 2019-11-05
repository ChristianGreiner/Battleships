package io;

import game.MapData;
import game.Savegame;

public interface FileHandler {

    public MapData[] readMapConfig(String filename) throws Exception;

    public void WriteSavegame(Savegame savegame);

    public Savegame LoadSavegame();
}
