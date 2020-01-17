package graphics;

import java.awt.image.BufferedImage;

/**
 * Stores data of a sprite.
 */
public class Sprite {
    private BufferedImage spriteSheet;
    private int tileSize = 32;

    /**
     * The constructor of a sprite.
     * @param image The image of the sprite
     * @param tileSize The size of the tiles.
     */
    public Sprite(BufferedImage image, int tileSize) {
        this.spriteSheet = image;
        this.tileSize = tileSize;
    }

    /**
     * Gets the sprite sheet.
     * @return The sprite sheet as buffered image.
     */
    public BufferedImage getSpriteSheet() {
        return spriteSheet;
    }

    /**
     * Gets the tile size
     * @return The tilesize.
     */
    public int getTileSize() {
        return tileSize;
    }

    /**
     * Gets the sprite at given x- and y-position.
     * @param xGrid The x pos.
     * @param yGrid The y pos.
     * @return The buffered image.
     */
    public BufferedImage getSprite(int xGrid, int yGrid) {
        return this.spriteSheet.getSubimage(xGrid * this.tileSize, yGrid * this.tileSize, this.tileSize, this.tileSize);
    }
}
