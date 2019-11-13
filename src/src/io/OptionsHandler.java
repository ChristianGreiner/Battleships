package io;

import core.Options;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class OptionsHandler {

    public void writeOptions(Options options) {
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream("options.txt");
            out = new ObjectOutputStream(fos);
            out.writeObject(options);

            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Options loadOptions() {
        FileInputStream fis = null;
        ObjectInputStream in = null;
        Options options = null;
        try {
            fis = new FileInputStream("options.txt");
            in = new ObjectInputStream(fis);
            options = (Options) in.readObject();
            in.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return options;
    }
}
