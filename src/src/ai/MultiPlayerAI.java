package ai;

import core.AI;
import core.Alignment;
import core.Direction;
import core.Helper;
import game.Map;
import game.MapTile;

import java.awt.*;

public class MultiPlayerAI implements AI {

    //private Map enemymap;
    private MultiplayerMap eMap;
    private boolean shipinfocus;
    private Point hitPoint;
    private Point startP;//margin points of hits left or up
    private Point endP;
    private Point newpoint;
    private Alignment shipAlignment = null;
    private int continueddirection;

    public MultiPlayerAI(Map enemymap){
        //this.enemymap = enemymap;
        this.eMap = new MultiplayerMap(enemymap.getSize());
    }

    //@Override
    public Point shot() {
            if (!shipinfocus){
                return randomHit();
            }
            return continuehit();
    }

    /*
    to do: informationen in eigene Map einarbeiten und rückgabe für den nächsten shot einfügen(hitpoint)
     */
    private boolean hitanswer(int answer) {

        if(!shipinfocus){
            if(answer == 1){ //ship
                shipinfocus=true;
                return true;
            }
            else return false; // water
        }
        if(continueddirection == 0){ //up
            if(answer == 1){ //ship
                this.startP = newpoint;

                if (!(this.hitPoint == null))
                    this.endP = this.hitPoint;

                this.hitPoint = null;
                this.shipAlignment = Alignment.Vertical;
                return true;
            }
            else if(answer == 2){//ship destroyed
                this.hitPoint = null;
                this.startP = null;
                this.endP = null;
                this.shipAlignment = null;
                shipinfocus=false;
                return true;
            }
            else return false; // water
        }
        if(continueddirection == 1){ //right
            if(answer == 1){ //ship
                if (!(this.hitPoint == null))
                    this.startP = this.hitPoint;

                this.endP = newpoint;
                this.hitPoint = null;
                this.shipAlignment = Alignment.Horizontal;
                return true;
            }
            else if(answer == 2){//ship destroyed
                this.hitPoint = null;
                this.startP = null;
                this.endP = null;
                this.shipAlignment = null;
                shipinfocus=false;
                return true;
            }
            else return false; // water
        }
        if(continueddirection == 2){ //down
            if(answer == 1){ //ship
                if (!(this.hitPoint == null))
                    this.startP = this.hitPoint;

                this.endP = newpoint;
                this.hitPoint = null;
                this.shipAlignment = Alignment.Vertical;
                return true;
            }
            else if(answer == 2){//ship destroyed
                shipinfocus=false;
                return true;
            }
            else return false; // water
        }
        if(continueddirection == 3){ //left
            if(answer == 1){ //ship
                this.startP = newpoint;
                if(!(this.hitPoint == null)) this.endP = this.hitPoint;

                this.hitPoint = null;
                this.shipAlignment = Alignment.Horizontal;
                return true;
            }
            else if(answer == 2){//ship destroyed
                this.hitPoint = null;
                this.startP = null;
                this.endP = null;
                this.shipAlignment = null;
                shipinfocus=false;
                return true;
            }
            else return false; // water
        }
        return false;
    }

    private Point continuehit() {
        Alignment align = Helper.getRandomAlignment(); //if 0 ,then vertical hit, if 1 horizontal hit

        //check, if alignment was set in last hit
        if (this.shipAlignment == Alignment.Horizontal || this.shipAlignment == Alignment.Vertical) {
            align = this.shipAlignment;
        }

        Direction dir = Helper.getRandomDirection(align);

        System.out.println("next shot in "+align+" "+dir+".");

        if (align == Alignment.Vertical) { //vertical
            if (dir == Direction.Up) {//vertical up

                if (this.startP != null) {
                    newpoint = new Point(this.startP.x, this.startP.y - 1);
                } else newpoint = new Point(this.hitPoint.x, this.hitPoint.y - 1);
                MapTile newtile = this.eMap.getTile(newpoint);


                if (this.eMap.isInMap(newpoint) && !newtile.isHit()) {//check of legal point
                    this.eMap.shot(newpoint);
                    continueddirection = 0;
                }
            }
            else if (dir == Direction.Down) { //vertical down

                if (this.endP != null) {
                    newpoint = new Point(this.endP.x, this.endP.y + 1);
                } else newpoint = new Point(this.hitPoint.x, this.hitPoint.y + 1);
                MapTile newtile = this.eMap.getTile(newpoint);

                if (this.eMap.isInMap(newpoint) && !newtile.isHit()) { //check of legal point
                    this.eMap.shot(newpoint);
                    continueddirection = 2;
                }
            }
        }
        else if (align == Alignment.Horizontal) { //horizontal
            if (dir == Direction.Left) { //horizontal left

                if (this.startP != null) {
                    newpoint = new Point(this.startP.x - 1, this.startP.y);
                } else newpoint = new Point(this.hitPoint.x - 1, this.hitPoint.y);
                MapTile newtile = eMap.getTile(newpoint);


                if (this.eMap.isInMap(newpoint) && !newtile.isHit()) { //check of legal point
                    this.eMap.shot(newpoint);
                    continueddirection = 3;
                }
            }
            else if (dir == Direction.Right) { //horizontal right

                if (this.endP != null) {
                    newpoint = new Point(this.endP.x + 1, this.endP.y);
                } else newpoint = new Point(this.hitPoint.x + 1, this.hitPoint.y);
                MapTile newtile = this.eMap.getTile(newpoint);


                if (this.eMap.isInMap(newpoint) && !newtile.isHit()) { //check of legal point
                    this.eMap.shot(newpoint);
                    continueddirection = 1;

                }
            }
        }
        return newpoint;
    }

    private Point randomHit(){

        do {
            int tryMarkx = (int) (Math.random() * this.eMap.getSize());
            int tryMarky = (int) (Math.random() * this.eMap.getSize());
            this.hitPoint = new Point(tryMarkx, tryMarky);
        }
        while (this.eMap.getTile(this.hitPoint).isHit());

        this.eMap.shot(this.hitPoint);

        return hitPoint;
    }
}