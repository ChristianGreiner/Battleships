package network.commands;

import network.NetworkMessage;

public class ShotCommand implements NetworkMessage {

    private int x, y;

    public ShotCommand(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String create() {
        return "SHOT " + this.x + " " + this.y;
    }
}
