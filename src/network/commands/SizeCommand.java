package network.commands;

import network.NetworkMessage;

public class SizeCommand implements NetworkMessage {

    private int size;

    public SizeCommand(int size) {
        this.size = size;
    }

    @Override
    public String create() {
        return "SIZE " + this.size;
    }
}