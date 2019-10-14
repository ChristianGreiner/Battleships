package core;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Renderer extends Canvas {

    public Graphics getDoubleBufferGraphics() {
        return doubleBufferGraphics;
    }

    private Graphics doubleBufferGraphics = null;

    private BufferStrategy bs = null;

    public Graphics begin() {
        this.bs = getBufferStrategy();
        if(this.bs == null) {
            createBufferStrategy(2);
            return null;
        }

        this.doubleBufferGraphics = this.bs.getDrawGraphics();
        this.doubleBufferGraphics.clearRect(0, 0, getWidth(), getHeight());

        return this.doubleBufferGraphics;
    }

    public void end() {
        this.doubleBufferGraphics.dispose();
        this.bs.show();
    }
}
