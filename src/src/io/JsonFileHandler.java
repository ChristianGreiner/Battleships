package io;

import com.google.gson.Gson;
import game.MapData;

import java.io.BufferedReader;
import java.io.FileReader;

public class JsonFileHandler implements FileHandler {

    private Gson gson = new Gson();

    public MapData[] readMapConfig(String filename) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        MapData[] json = gson.fromJson(bufferedReader, MapData[].class);
        return json;
    }

}
