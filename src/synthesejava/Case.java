package synthesejava;

import java.awt.Dimension;
import java.awt.Point;

public class Case {
    private Point pos;
    private static Dimension size;

    public Case(Point pos) {
        this.pos = pos;
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }

    public Point getPos() {
        return pos;
    }

    public static void setSize(Dimension size) {
        Case.size = size;
    }

    public static Dimension getSize() {
        return size;
    }
}
