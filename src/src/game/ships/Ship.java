package game.ships;

public abstract class Ship {

    public int getId() {
        return Ship.id;
    }

    private static int id = 0;

    private int fields;

    public Ship(int fields) {
        Ship.id++;
        this.fields = fields;
    }

    public int getFields() {
        return fields;
    }

}
