package synthesejava;

import java.util.Hashtable;

public static class ActionDispatcher {
    private static Hashtable<Integer, String> action = new Hashtable<Integer, String>() {
        {
            put(0, "fff");
            put(1, "asdf");
            put(2, "bar");
            put(3, "y");
        }
    };

    public static String getAction(int n) {
        return action.get(n);
    }
}
