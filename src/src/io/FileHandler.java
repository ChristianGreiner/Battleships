package io;

import com.google.gson.Gson;
import core.Options;
import game.MapData;

import java.io.*;

public class FileHandler {
    private Gson gson = new Gson();

    public MapData[] readMapConfig(String filename) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        MapData[] json = gson.fromJson(bufferedReader, MapData[].class);
        return json;
    }

    public void writeObject(Object object, String fileName) {
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(fileName);
            out = new ObjectOutputStream(fos);
            out.writeObject(object);
            out.close();
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
    }

    public Object loadObject(String fileName) {
        FileInputStream fis = null;
        ObjectInputStream in = null;
        Options options = null;
        try {
            fis = new FileInputStream(fileName);
            in = new ObjectInputStream(fis);
            options = (Options) in.readObject();
            in.close();
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
        return options;
    }
}
