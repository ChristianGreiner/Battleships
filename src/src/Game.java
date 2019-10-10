import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {

    final int TARGET_FPS = 60;
    final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

    private Image doubleBufferImage = null;
    private Graphics doubleBufferGraphics = null;
    private boolean isRunning;
    private int counter = 0;

    public Game(String title, Point size)
    {
        this.setPreferredSize(new Dimension(size.x, size.y));
        this.setBackground(Color.white);
        this.setTitle(title);
        this.setSize(size.x, size.y);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);


    }

    public void run() {
        this.isRunning = true;
        this.setVisible(true);
        this.gameLoop();
    }

    public void update(double deltaTime) {
    }

    @Override
    public void paint(final Graphics g) {
        if (doubleBufferImage == null) {
            doubleBufferImage = createImage(getWidth(), getHeight());
        }
        doubleBufferGraphics = doubleBufferImage.getGraphics();
        doubleBufferGraphics.setColor(Color.black);
        doubleBufferGraphics.fillRect(0, 0, getWidth(), getHeight()) ;

        // render game
        draw(doubleBufferImage.getGraphics());

        // render double buffer
        g.drawImage(doubleBufferImage, 0, 0, null);
    }

    public void draw(final Graphics g) {
        g.setColor(Color.white);

        g.drawString("Counter:" + counter, 100, 100);

        counter++;
    }

    private void gameLoop() {
        long lastLoopTime = System.nanoTime();

        while (isRunning)
        {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double deltaTime = updateLength / ((double)OPTIMAL_TIME);

            this.update(deltaTime);
            this.repaint();

            try {
                Thread.sleep( (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
