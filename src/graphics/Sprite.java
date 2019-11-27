package graphics;

import java.awt.image.BufferedImage;

public class Sprite {
    public BufferedImage getSpriteSheet() {
        return spriteSheet;
    }

    public int getTileSize() {
        return tileSize;
    }

    private BufferedImage spriteSheet;
    private int tileSize = 32;

    public Sprite(BufferedImage image, int tileSize){
        this.spriteSheet = image;
        this.tileSize = tileSize;
    }

    public BufferedImage getSprite(int xGrid, int yGrid) {
        return this.spriteSheet.getSubimage(xGrid * this.tileSize, yGrid * this.tileSize, this.tileSize, this.tileSize);
    }
}
