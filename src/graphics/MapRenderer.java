package graphics;

import core.Helper;
import core.Renderer;
import game.Assets;
import game.Map;
import game.MapTile;
import game.ships.Battleship;
import game.ships.Carrier;
import game.ships.Destroyer;

import java.awt.*;
import java.util.ArrayList;

public class MapRenderer extends Renderer {

    private Map map;
    private ArrayList<MapTile> selectedShipTiles;

    public void setMap(Map map) {
        this.map = map;
    }

    public MapRenderer(Map map) {
        this.map = map;
    }

    private void drawTile(Graphics g, int index, int x, int y, int w, int h) {
        g.drawImage(Assets.Images.TILESET, x, y, x + w, y + h, index * w, index * h, index * w + w,index * h + h, null);
    }

    @Override
    public void draw() {
        super.draw();

        if(this.map == null)
            return;

        Graphics g = this.begin();

        Point tileSize = new Point(this.getWidth() / (this.map.getSize() + 1), this.getHeight() / (this.map.getSize() + 1));
        //int tileSize = this.getWidth() / (map.getSize() + 1);

        for (int y = 0; y < this.map.getSize(); y++) {
            for (int x = 0; x < this.map.getSize(); x++) {
                MapTile tile = this.map.getTile(new Point(x, y));

                Point tilePos = new Point(x * tileSize.x + tileSize.x, y * tileSize.y + tileSize.y);
                //g.drawImage(Assets.Images.TILESET, tilePos.x, tilePos.y, tilePos.x + tileSize.x, tilePos.y + tileSize.y, 0, 0, 0 + tileSize.x,0 + tileSize.y, null);
                drawTile(g, 0, tilePos.x, tilePos.y, tileSize.x, tileSize.y);

                if (tile.isBlocked()) {
                    drawTile(g, 1, tilePos.x, tilePos.y, tileSize.x, tileSize.y);
                } else if (tile.hasShip()) {

                    if (tile.getShip() instanceof Battleship) {
                        g.setColor(Color.DARK_GRAY);
                    } else if (tile.getShip() instanceof Carrier) {
                        g.setColor(Color.LIGHT_GRAY);
                    } else if (tile.getShip() instanceof Destroyer) {
                        g.setColor(Color.GRAY);
                    } else {
                        g.setColor(Color.WHITE);
                    }

                    g.fillRect(x * tileSize.x + tileSize.x, y * tileSize.y + tileSize.y, tileSize.x, tileSize.y);

                    if (tile.isHit()) {
                        g.setColor(Color.ORANGE);
                        g.fillRect(x * tileSize.x + tileSize.x, y * tileSize.y + tileSize.y, tileSize.x, tileSize.y);
                    }

                } else if (tile.isHit() && !tile.isBlocked()) {
                    g.setColor(Color.RED);
                    Helper.drawCenteredString(g, "X", new Rectangle(x * tileSize.x + tileSize.x, y * tileSize.y + tileSize.y, tileSize.x, tileSize.y), Assets.Fonts.DEFAULT);
                }
            }
        }

        //draw grid
        g.setColor(Color.BLACK);
        for (int i = 0; i < map.getSize() + 1; i++) {
            //vertical
            g.drawLine(i * tileSize.x,0, i * tileSize.x, this.getHeight());
            //horizontal
            g.drawLine(0,i * tileSize.y , this.getWidth(), i * tileSize.y);
        }


        //ship selection using mouse
        if(this.getMousePosition() != null && this.getMousePosition().x >= tileSize.x && this.getMousePosition().y >= tileSize.y && this.getMousePosition().x <= this.getWidth() && this.getMousePosition().y <= this.getHeight() ) {

            // System.out.println( this.getMousePosition().x + " " + this.getMousePosition().y);




            Point tempPoint = new Point( (this.getMousePosition().x / tileSize.x) - 1, (this.getMousePosition().y / tileSize.y) - 1);
            System.out.println( ((this.getMousePosition().x / tileSize.x) - 1) + " " + ((this.getMousePosition().y / tileSize.y) - 1));


            MapTile hoverdMapTile = this.map.getTile(tempPoint);
            if(hoverdMapTile != null)
            {
                if(hoverdMapTile.hasShip()) {
                    MapTile selectedTile = hoverdMapTile;

                    if (selectedTile.hasShip()) {
                        this.selectedShipTiles = selectedTile.getShip().getTiles();

                        g.setColor(Color.RED);

                        for (int i = 0; i < this.selectedShipTiles.size(); i++) {
                            g.drawRect(this.selectedShipTiles.get(i).getPos().x * tileSize.x + tileSize.x, this.selectedShipTiles.get(i).getPos().y * tileSize.y + tileSize.y, tileSize.x, tileSize.y);
                        }
                    }
                }
                else
                {
                    Point highlightPoint = new Point(((this.getMousePosition().x / (tileSize.x)) * tileSize.x), ((this.getMousePosition().y / (tileSize.y )) * tileSize.y) );
                    g.setColor(Color.GREEN);
                    g.drawRect(highlightPoint.x, highlightPoint.y, tileSize.x, tileSize.y);
                }
            }
            }

        g.setColor(Color.BLACK);
        g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 16));
        // draw numbers above board
        for(Integer num = 1; num <= map.getSize(); num++)
        {
            g.drawString(num.toString(),num * tileSize.x ,10);
        }

        // draw letters next to board
        int asciiCode = 65;
        for(int num = 1; num <= map.getSize(); num++)
        {
            g.drawString(Character.toString((char) asciiCode),5 ,num * tileSize.y + tileSize.y / 2 + 20);
            asciiCode++;
        }

        this.end();


    }
}