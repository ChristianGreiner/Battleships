package core;

public class Helper {
    public static int randomNumber(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    public static boolean randomBoolean() {
        return randomNumber(0, 1) == 1;
    }
}
