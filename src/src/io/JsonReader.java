package io;

import com.google.gson.Gson;
import game.MapData;

import java.io.BufferedReader;
import java.io.FileReader;

public class JsonReader {

    public JsonReader() {

    }

    public static MapData[] readJson(String filename) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        Gson gson = new Gson();
        MapData[] json = gson.fromJson(bufferedReader, MapData[].class);
        return json;
    }
}
