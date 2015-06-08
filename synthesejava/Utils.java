package synthesejava;

import java.util.Random;

public class Utils {
    public static int randInt(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }
}
