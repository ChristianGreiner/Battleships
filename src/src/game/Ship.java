package game;

public class Ship {

    public int getId() {
        return Ship.id;
    }

    private static int id = 0;

    public int getSize() {
        return size;
    }

    private int size = 0;

    public Ship() {
        Ship.id++;
    }

}
