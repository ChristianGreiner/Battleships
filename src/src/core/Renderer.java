package core;

import javax.swing.*;
import java.awt.*;

public class Renderer extends JPanel {

    private Image doubleBufferImage = null;
    private Graphics doubleBufferGraphics = null;

    public Graphics begin(){
        if (doubleBufferImage == null) {
            doubleBufferImage = createImage(getWidth(), getHeight());
        }
        doubleBufferGraphics = doubleBufferImage.getGraphics();

        // clear image
        doubleBufferGraphics.clearRect(0, 0, getWidth(), getHeight());

        return doubleBufferGraphics;
    }

    public void end() {
        this.repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(this.doubleBufferGraphics != null && this.doubleBufferImage != null) {
            g.drawImage(doubleBufferImage, 0, 0, null);
        }

    }
}
