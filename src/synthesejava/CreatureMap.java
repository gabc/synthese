package synthesejava;

public class CreatureMap {
    private Creature[][] map;
    private int maxw;
    private int maxh;

    public CreatureMap(int w, int h) {
        w += 1;
        h += 1;
        map = new Creature[h][w];
        this.maxw = w;
        this.maxh = h;
    }

    public void insert(Creature c, int w, int h) {
        if (w < 0)
            w = 0;
        if (h < 0)
            h = 0;
        if (w > maxw)
            w = maxw;
        if (h > maxh)
            h = maxh;

        if (map[h][w] != null)
            return; // Il y a quelque chose la.
        else
            map[h][w] = c;
    }

    public void update(Creature c, int w, int h) {
        if (w < 0)
            w = 0;
        if (h < 0)
            h = 0;
        if (w > maxw)
            w = maxw;
        if (h > maxh)
            h = maxh;

        map[h][w] = c;
    }

    public Creature get(int w, int h) {
        return map[h][w];
    }

    public CreatureList getAround(int w, int h, int range) {
        CreatureList cl = new CreatureList(null);

        if (w < 0)
            w = 0;
        if (h < 0)
            h = 0;
        if (w > maxw)
            w = maxw;
        if (h > maxh)
            h = maxh;

        int x1, x2, y1, y2;

        x1 = w - range;
        x2 = w + range;
        y1 = h - range;
        y2 = h + range;

        if (x1 > maxw)
            x1 = maxw;
        if (x1 > maxw)
            x1 = maxw;
        if (x1 < 0)
            x1 = 0;
        if (x1 < 0)
            x1 = 0;

        if (y1 > maxh)
            y1 = maxh;
        if (y1 > maxh)
            y1 = maxh;
        if (y1 < 0)
            y1 = 0;
        if (y1 < 0)
            y1 = 0;

        if (x2 > maxw)
            x2 = maxw;
        if (x2 > maxw)
            x2 = maxw;
        if (x2 < 0)
            x2 = 0;
        if (x2 < 0)
            x2 = 0;

        if (y2 > maxh)
            y2 = maxh;
        if (y2 > maxh)
            y2 = maxh;
        if (y2 < 0)
            y2 = 0;
        if (y2 < 0)
            y2 = 0;

        for (int i = x1; i < x2; i++) {
            for (int j = y1; j < y2; j++) {
                if (map[j][i] != null)
                    cl.add(map[j][i]);
            }
        }

        return cl;
    }
}
