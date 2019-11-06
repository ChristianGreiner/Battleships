package ai;

import core.AI;
import core.Helper;
import game.Map;
import game.MapTile;
import game.ships.Ship;

import java.awt.*;

public class SinglePlayerAI implements AI {

    private int difficulty = 1; //difficulty 0 for easy(stupid), 1 for fair, 2 hard, 3 hardcore
    private Point lastPoint; //last discovered hit
    private Point startP;//margin points of hits left or up
    private Point endP;//margin points of hits right or down
    private boolean shipInFocus = false; //keeps the shot ship in mind for nxt hit
    private boolean shotHit = false; //check of success of last hit
    private boolean shipDestroyed = true;
    private int shipAlignment = -1; //-1 not set; 0 set vertical; 1 set horizontal
    private int shipDirection = -1;//-1 not set; 0 set left/up; 1 set right/down


    private Map map;

    public SinglePlayerAI(int difficulty, Map map) {
        this.difficulty = difficulty;
        this.map = map;
    }

    @Override
    public void shot() {
        if (this.difficulty != 1 && !this.shipInFocus) {
            if (permitInfluencedHit()) {
                System.out.println("entered AimedHit()");
                AimedHit();
            }
        }
        if ((!this.shotHit && this.shipDestroyed) || !this.shipInFocus) {
            System.out.println("entered tryhit()");
            tryHit();
        } else {
            System.out.println("entered continuehit()");
            continueHit();
        }
    }

    private boolean permitInfluencedHit() {
        int number = (int) (Math.random() * 10);
        System.out.println(number);

        if (this.difficulty == 2 || this.difficulty == 0) {
            return number <= 2;
        } else if (this.difficulty == 3) {
            return number <= 3;
        } else return false;
    }

    private void AimedHit() {
        this.shipDestroyed = false;
        Point hitPoint;

        if (this.difficulty == 2 || this.difficulty == 3) { //AutoHit

            do {
                int hitMarkx = (int) (Math.random() * this.map.getSize());
                int hitMarky = (int) (Math.random() * this.map.getSize());
                hitPoint = new Point(hitMarkx, hitMarky);
            }
            while (this.map.getShip(hitPoint) == null || this.map.getTile(hitPoint).isHit());
            this.map.shot(hitPoint);
            System.out.println("shot in" + hitPoint);
            this.lastPoint = hitPoint;
            this.shipInFocus = true;
        } else if (this.difficulty == 0) {//NoHit

            do {
                int hitMarkx = (int) (Math.random() * this.map.getSize());
                int hitMarky = (int) (Math.random() * this.map.getSize());
                hitPoint = new Point(hitMarkx, hitMarky);
            }
            while (this.map.getShip(hitPoint) != null);
            this.map.shot(hitPoint);
        }
    }

    //complete shooting ship

    private void continueHit() {
        int allignmenthit = (int) Helper.randomNumber(0, 1); //if 0 ,then vertical hit, if 1 horizontal hit
        int choosedir = (int) Helper.randomNumber(0, 1);

        System.out.println("Align: " + allignmenthit + " , Choosdir" + choosedir);
        Point newpoint;

        //check, if alignment was set in last hit
        if (this.shipAlignment == 0 || this.shipAlignment == 1) {
            allignmenthit = this.shipAlignment;
        }

        //check if direction was set in last hit
        if (this.shipDirection == 0 || this.shipDirection == 1) {
            choosedir = this.shipDirection;
        }

        if (allignmenthit == 0) { //vertical
            if (choosedir == 0) {//vertical up

                if (this.startP != null) {
                    newpoint = new Point(this.startP.x, this.startP.y - 1);
                } else newpoint = new Point(this.lastPoint.x, this.lastPoint.y - 1);
                MapTile newtile = this.map.getTile(newpoint);


                if (this.map.isInMap(newpoint) && !newtile.isHit()) { //check of legal point
                    Ship unknownShip = newtile.getShip();
                    this.map.shot(newpoint);

                    if (!(unknownShip == null) && unknownShip.isDestroyed()) { //check of destroyed ship
                        System.out.println("Schiff versenkt");
                        this.shotHit = false;
                        this.shipDestroyed = true;
                        this.shipInFocus = false;
                        this.lastPoint = null;
                        this.startP = null;
                        this.endP = null;
                        this.shipAlignment = -1;
                        this.shipDirection = -1;
                    } else if (newtile.hasShip()) { //check, if shot hit a ship
                        //this.lasttile = newtile;
                        this.startP = newpoint;
                        this.endP = lastPoint;
                        this.shipAlignment = 0;
                        this.shotHit = true;
                    } else {
                        //this.shipinfocus=true;
                        this.shotHit = false;
                    }
                } else {
                    continueHit();
                }
            } else if (choosedir == 1) { //vertical down

                if (this.endP != null) {
                    newpoint = new Point(this.endP.x, this.endP.y + 1);
                } else newpoint = new Point(this.lastPoint.x, this.lastPoint.y + 1);
                MapTile newtile = this.map.getTile(newpoint);


                if (this.map.isInMap(newpoint) && !newtile.isHit()) { //check of legal point
                    Ship unknownShip = newtile.getShip();
                    this.map.shot(newpoint);

                    if (!(unknownShip == null) && unknownShip.isDestroyed()) { //check of destroyed ship
                        System.out.println("Schiff versenkt");
                        this.shotHit = false;
                        this.shipDestroyed = true;
                        this.shipInFocus = false;
                        this.lastPoint = null;
                        this.startP = null;
                        this.endP = null;
                        this.shipAlignment = -1;
                        this.shipDirection = -1;
                    } else if (newtile.hasShip()) { //check, if shot hit a ship
                        //this.lasttile = newtile;
                        this.startP = lastPoint;
                        this.endP = newpoint;
                        lastPoint = newpoint;
                        this.shipAlignment = 0;
                        this.shotHit = true;
                    } else {
                        //this.shipinfocus=true;
                        this.shotHit = false;
                    }
                } else {
                    continueHit();
                }
            }
        } else if (allignmenthit == 1) { //horizontal
            if (choosedir == 0) { //horizontal left

                if (this.startP != null) {
                    newpoint = new Point(this.startP.x - 1, this.startP.y);
                } else newpoint = new Point(this.lastPoint.x - 1, this.lastPoint.y);
                MapTile newtile = map.getTile(newpoint);


                if (this.map.isInMap(newpoint) && !newtile.isHit()) { //check of legal point
                    Ship unknownShip = newtile.getShip();
                    this.map.shot(newpoint);

                    if (!(unknownShip == null) && unknownShip.isDestroyed()) { //check of destroyed ship
                        System.out.println("Schiff versenkt");
                        this.shipDestroyed = true;
                        this.shipInFocus = false;
                        this.lastPoint = null;
                        this.startP = null;
                        this.endP = null;
                        this.shipAlignment = -1;
                        this.shipDirection = -1;
                    } else if (newtile.hasShip()) { //check, if shot hit a ship
                        //this.lasttile = newtile;
                        this.startP = newpoint;
                        this.endP = lastPoint;
                        this.shipAlignment = 1;
                        this.shotHit = true;
                    } else {
                        //this.shipinfocus=true;
                        this.shotHit = false;
                    }
                } else {
                    continueHit();
                }
            } else if (choosedir == 1) { //horizontal right

                if (this.endP != null) {
                    newpoint = new Point(this.endP.x + 1, this.endP.y);
                } else newpoint = new Point(this.lastPoint.x + 1, this.lastPoint.y);
                MapTile newtile = this.map.getTile(newpoint);


                if (this.map.isInMap(newpoint) && !newtile.isHit()) { //check of legal point
                    Ship unknownShip = newtile.getShip();
                    this.map.shot(newpoint);

                    if (!(unknownShip == null) && unknownShip.isDestroyed()) { //check of destroyed ship
                        System.out.println("Schiff versenkt");
                        this.shipDestroyed = true;
                        this.shipInFocus = false;
                        this.lastPoint = null;
                        this.startP = null;
                        this.endP = null;
                        this.shipAlignment = -1;
                        this.shipDirection = -1;
                    } else if (newtile.hasShip()) { //check, if shot hit a ship
                        //this.lasttile = newtile;
                        this.startP = lastPoint;
                        this.endP = newpoint;
                        this.shipAlignment = 1;
                        this.shipDirection = -1;
                        this.shotHit = true;
                    } else {
                        //this.shipinfocus=true;
                        this.shotHit = false;
                    }
                } else {
                    continueHit();
                }
            }
        }
    }

    //random hit
    private void tryHit() {
        this.shipDestroyed = false;
        Point tryPoint;

        do {
            int tryMarkx = (int) (Math.random() * this.map.getSize());
            int tryMarky = (int) (Math.random() * this.map.getSize());
            tryPoint = new Point(tryMarkx, tryMarky);
        }
        while (this.map.getTile(tryPoint).isHit());
        this.shotHit = this.map.shot(tryPoint);
        if (shotHit) {
            this.lastPoint = tryPoint;
            shipInFocus = true;
        }
    }

    public boolean permitNextShot() {
        return shotHit || shipDestroyed;
    }
}
