package graphics;

import core.ANSIColors;
import core.Renderer;
import game.Assets;
import game.Map;
import game.MapTile;
import game.ships.Battleship;
import game.ships.Carrier;
import game.ships.Destroyer;
import game.ships.Submarine;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

public class MapRenderer extends Renderer implements MouseListener, MouseWheelListener {

    private Map map;
    private Point tileSize;
    private ArrayList<MapTile> selectedShipTiles;
    private MapTile hoveredMapTile;
    private Point MouseShipOffset;
    private boolean clicked;
    private boolean selected;
    private boolean rotated;
    private boolean pressed;
    private boolean hovered;
    private ArrayList<MapRendererListener> listener = new ArrayList<>();

    public void addMapRendererListener(MapRendererListener mrl)
    {
        this.listener.add(mrl);
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public MapRenderer(Map map) {
        this.map = map;
        this.addMouseListener(this);
        this.addMouseWheelListener(this);
    }

    @Override
    public void draw() {
        super.draw();

        if(this.map == null){
            return;
        }

        Graphics g = this.begin();

        this.tileSize = new Point(this.getWidth() / (this.map.getSize() + 1), this.getHeight() / (this.map.getSize() + 1));

        this.drawShips(g, tileSize);

        this.hovered = false;

        //draw grid
        this.drawGrid(g, tileSize);

        this.HighlightShip(g);

        this.drawNumbers(g, tileSize);
        this.drawLetters(g, tileSize);

        this.end();

    }

    private void drawGrid(Graphics g, Point tileSize)
    {
        g.setColor(Color.BLACK);
        for (int i = 0; i < map.getSize() + 1; i++) {
            //vertical
            g.drawLine(i * tileSize.x,0, i * tileSize.x, this.getHeight());
            //horizontal
            g.drawLine(0,i * tileSize.y , this.getWidth(), i * tileSize.y);
        }
    }

    private void drawShips(Graphics g, Point tileSize)
    {
        for (int y = 0; y < this.map.getSize(); y++) {
            for (int x = 0; x < this.map.getSize(); x++) {
                MapTile tile = this.map.getTile(new Point(x, y));


                if (tile.isBlocked()) {
                    g.setColor(Color.YELLOW);
                    g.fillRect(x * tileSize.x + tileSize.x, y * tileSize.y + tileSize.y, tileSize.x, tileSize.y);


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

                    if(selectedShipTiles != null && selectedShipTiles.contains(tile) && selected){
                        g.setColor(Color.BLUE);
                    }

                    g.fillRect(x * tileSize.x + tileSize.x, y * tileSize.y + tileSize.y, tileSize.x, tileSize.y);

                    if (tile.isHit()) {
                        g.setColor(Color.ORANGE);
                        g.fillRect(x * tileSize.x + tileSize.x, y * tileSize.y + tileSize.y, tileSize.x, tileSize.y);
                    }

                } else if (tile.isHit() && !tile.isBlocked()) {
                    g.setColor(Color.RED);
                    g.fillRect(x * tileSize.x + tileSize.x, y * tileSize.y + tileSize.y, tileSize.x, tileSize.y);
                } else {
                    g.setColor(Color.BLUE);
                    g.fillRect(x * tileSize.x + tileSize.x, y * tileSize.y + tileSize.y, tileSize.x, tileSize.y);
                }

            }
        }

    }

    private void HighlightShip(Graphics g) {
        //ship selection using mouse
        if (this.getMousePosition() != null && this.getMousePosition().x >= tileSize.x && this.getMousePosition().y >= tileSize.y && this.getMousePosition().x <= this.getWidth() && this.getMousePosition().y <= this.getHeight()) {

            Point tempPoint = new Point((this.getMousePosition().x / tileSize.x) - 1, (this.getMousePosition().y / tileSize.y) - 1);
            // System.out.println(((this.getMousePosition().x / tileSize.x) - 1) + " " + ((this.getMousePosition().y / tileSize.y) - 1));

            this.hoveredMapTile = this.map.getTile(tempPoint);
            if (this.hoveredMapTile != null) {
                if (this.hoveredMapTile.hasShip() && !this.selected) {

                    this.selectedShipTiles = this.hoveredMapTile.getShip().getTiles();
                    this.hovered = true;
                    if (!this.pressed) {
                        for (int i = 0; i < this.selectedShipTiles.size(); i++) {
                            g.setColor(Color.RED);
                            g.drawRect(this.selectedShipTiles.get(i).getPos().x * tileSize.x + tileSize.x, this.selectedShipTiles.get(i).getPos().y * tileSize.y + tileSize.y, tileSize.x, tileSize.y);
                        }
                    }

                } else {
                    Point highlightPoint = new Point(((this.getMousePosition().x / (tileSize.x)) * tileSize.x), ((this.getMousePosition().y / (tileSize.y)) * tileSize.y));
                    g.setColor(Color.GREEN);
                    g.drawRect(highlightPoint.x, highlightPoint.y, tileSize.x, tileSize.y);
                }

            }

            if (this.selected) {
                g.setColor(new Color(124, 252, 0, 200));
                Point floatingShipPos = new Point( this.getMousePosition().x - this.MouseShipOffset.x, this.getMousePosition().y - this.MouseShipOffset.y);
                if (this.selectedShipTiles.get(0).getShip().isRotated()) {
                    if(!this.rotated) {
                        g.fillRect(floatingShipPos.x, floatingShipPos.y, tileSize.x * selectedShipTiles.get(0).getShip().getSpace(), tileSize.y);
                    }
                    else {
                        g.fillRect(floatingShipPos.x, floatingShipPos.y, tileSize.x, tileSize.x * selectedShipTiles.get(0).getShip().getSpace());
                    }
                } else {
                    if(!this.rotated) {
                        g.fillRect(floatingShipPos.x, floatingShipPos.y, tileSize.x, tileSize.x * selectedShipTiles.get(0).getShip().getSpace());
                    }
                    else {
                        g.fillRect(floatingShipPos.x, floatingShipPos.y, tileSize.x * selectedShipTiles.get(0).getShip().getSpace(), tileSize.y);
                    }

                }

                //dropped
                if(!this.pressed){
                    this.selected = false;
                    floatingShipPos.x = floatingShipPos.x  / tileSize.x - 1;
                    floatingShipPos.y = floatingShipPos.y / tileSize.y - 1;
                    for(MapRendererListener mrl : listener ){
                        if(mrl != null) {
                            mrl.OnShipDropped(this.selectedShipTiles.get(0).getShip(), floatingShipPos, this.rotated);
                        }
                    }
                    this.rotated = false;
                    System.out.println("DROPPED " + floatingShipPos.x + " " + floatingShipPos.y);
                }
            }
        }
    }


    void drawLetters(Graphics g, Point tileSize)
    {
        // draw letters next to board
        int asciiCode = 65;
        for(int num = 1; num <= map.getSize(); num++)
        {
            g.drawString(Character.toString((char) asciiCode),5 ,num * tileSize.y + tileSize.y / 2 + 20);
            asciiCode++;
        }
    }

    void drawNumbers(Graphics g, Point tileSize)
    {
        g.setColor(Color.BLACK);
        g.setFont(Assets.Fonts.DEFAULT);
        // draw numbers above board
        for(Integer num = 1; num <= map.getSize(); num++)
        {
            g.drawString(num.toString(),num * tileSize.x ,10);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.pressed = true;

        if(this.hoveredMapTile != null) {
            if (this.hoveredMapTile.hasShip() && !this.selected) {

                this.selectedShipTiles = this.hoveredMapTile.getShip().getTiles();

                this.hovered = true;

                this.selected = true;

                Point shipTopPos = new Point(selectedShipTiles.get(0).getPos().x * tileSize.x + tileSize.x, selectedShipTiles.get(0).getPos().y * tileSize.y + tileSize.y);
                Point mousePos = new Point(this.getMousePosition().x, this.getMousePosition().y);

                this.MouseShipOffset = new Point(mousePos.x - shipTopPos.x, mousePos.y - shipTopPos.y);

            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.pressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        this.rotated = !this.rotated;
        System.out.println("rotated");

    }
}
