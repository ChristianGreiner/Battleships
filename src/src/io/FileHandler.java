package io;

import game.MapData;

public interface FileHandler {
    public MapData[] readMapConfig(String filename) throws Exception;
}
