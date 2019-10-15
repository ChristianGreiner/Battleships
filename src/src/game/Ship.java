package game;

public class Ship {

    public int getId() {
        return Ship.id;
    }

    private static int id = 0;

    public int getSize() {
        return size;
    }

    private int size;

    public Ship() {
        Ship.id++;
        // todo
    }

}
