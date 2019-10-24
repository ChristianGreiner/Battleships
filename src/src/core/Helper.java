package core;

public class Helper {
    public static int randomNumber(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    public static boolean randomBoolean() {
        int i = randomNumber(0, 1);

        return i == 1;
    }
}
