package network;

import java.io.InputStream;
import java.util.Scanner;

class ReceivedMessagesHandler implements Runnable {
    private InputStream server;

    public ReceivedMessagesHandler(InputStream server) {
        this.server = server;
    }

    public void run() {
        // receive server messages and print out to screen
        Scanner s = new Scanner(server);
        while (s.hasNextLine()) {
            System.out.println("\n" + s.nextLine());
        }
        s.close();
    }
}