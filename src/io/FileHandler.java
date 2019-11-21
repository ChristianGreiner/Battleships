package io;

import com.google.gson.Gson;
import core.Game;
import game.MapData;
import game.Savegame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

public class FileHandler {
    private Gson gson = new Gson();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Battleship Savegame", "*.bsg", "bsg");

    public FileHandler() {
    }

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
            ex.printStackTrace();
        }
    }

    public void writeObject(Object object, File file, String extension) {
        this.writeObject(object, file.getAbsoluteFile() + "." + extension);
    }

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

    public void saveSavegame(Savegame savegame) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);

        if (fileChooser.showSaveDialog(Game.getInstance().getWindow()) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            this.writeObject(savegame, file, "bsg");
        }
    }

    public Savegame loadSavegame() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);

        if (fileChooser.showOpenDialog(Game.getInstance().getWindow()) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            Savegame savegame = (Savegame)this.loadObject(file);
            return savegame;
        }

        return null;
    }
}
