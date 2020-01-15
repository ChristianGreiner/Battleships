package io;

import com.google.gson.Gson;
import core.Game;
import game.MapData;
import game.Savegame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

/**
 * Handles different file types like reading map config.
 */
public class FileHandler {

    private FileNameExtensionFilter filter = new FileNameExtensionFilter("Battleship Savegame", "*.bsg", "bsg");
    private Gson gson = new Gson();

    /**
     * Reads the map config.
     * @param filename The name of the config file.
     * @return The map config.
     * @throws Exception Gets thrown when the map cant be find.
     */
    public MapData[] readMapConfig(String filename) throws Exception {

        InputStream input = AssetsLoader.class.getClassLoader().getResourceAsStream(filename);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
        MapData[] json = gson.fromJson(bufferedReader, MapData[].class);
        return json;
    }


    /**
     * Serialize an object to a file.
     * @param object The object.
     * @param fileName The file.
     */
    public void writeObject(Object object, String fileName) {
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(fileName);
            out = new ObjectOutputStream(fos);
            out.writeObject(object);
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Serialize an object to a file.
     * @param object The object.
     * @param file The file.
     * @param extension The extension of the file.
     */
    public void writeObject(Object object, File file, String extension) {
        this.writeObject(object, file.getAbsoluteFile() + "." + extension);
    }

    /**
     * Deserialize an object from a file.
     * @param fileName The file name.
     * @return The object.
     */
    public Object loadObject(String fileName) {
        FileInputStream fis = null;
        ObjectInputStream in = null;
        Object object = null;
        try {
            fis = new FileInputStream(fileName);
            in = new ObjectInputStream(fis);
            object = in.readObject();
            in.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return object;
    }

    /**
     * Deserialize an object from a file.
     * @param file The file.
     * @return The object.
     */
    public Object loadObject(File file) {
        FileInputStream fis = null;
        ObjectInputStream in = null;
        Object object = null;
        try {
            fis = new FileInputStream(file.getAbsoluteFile());
            in = new ObjectInputStream(fis);
            object = in.readObject();
            in.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return object;
    }

    /**
     * Writes the savegame to a file.
     * @param savegame The savegame.
     */
    public void saveSavegame(Savegame savegame) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);

        if (fileChooser.showSaveDialog(Game.getInstance().getWindow()) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            this.writeObject(savegame, file, "bsg");
        }
    }

    /**
     * Loads the save game.
     * @return The save game.
     */
    public Savegame loadSavegame() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);

        if (fileChooser.showOpenDialog(Game.getInstance().getWindow()) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            Savegame savegame = (Savegame) this.loadObject(file);
            return savegame;
        }

        return null;
    }
}
