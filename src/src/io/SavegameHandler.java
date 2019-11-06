package io;

import game.Savegame;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SavegameHandler {

    public void writeSavegame(Savegame savegame) {
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream("savegame.txt");
            out = new ObjectOutputStream(fos);
            out.writeObject(savegame);

            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Savegame loadSavegame() {
        FileInputStream fis = null;
        ObjectInputStream in = null;
        Savegame sg = null;
        try {
            fis = new FileInputStream("savegame.txt");
            in = new ObjectInputStream(fis);
            sg = (Savegame) in.readObject();
            in.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sg;
    }
}
