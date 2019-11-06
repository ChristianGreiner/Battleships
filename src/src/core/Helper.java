package core;

public class Helper {
    public static int randomNumber(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    public static boolean randomBoolean() {
        return randomNumber(0, 1) == 1;
    }

    public static Alignment getRandomAlignment() {
        int alignNumber = randomNumber(0, 1);

        if (alignNumber == 0)
            return Alignment.Vertical;

        return Alignment.Horizontal;
    }

    public static Direction getRandomDirection() {
        int dirNumber = randomNumber(0, 3);

        if (dirNumber == 0)
            return Direction.Up;
        else if (dirNumber == 1)
            return Direction.Right;
        else if (dirNumber == 2)
            return Direction.Down;
        else
            return Direction.Left;
    }
}
