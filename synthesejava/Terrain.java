package synthesejava;

import java.awt.Dimension;

import java.awt.Point;

import java.util.ArrayList;
import java.util.List;

public class Terrain {
    private List<Case> terrain;
    private Dimension taille;

    public Terrain() {
        this.taille = new Dimension(0, 0);
        this.terrain = getFreshTerrain();
    }

    public Terrain(Dimension taille) {
        this.taille = taille;
        this.terrain = getFreshTerrain();
    }

    private List<Case> getFreshTerrain() {
        List<Case> list = new ArrayList<Case>();
        for (double i = 0; i < this.taille.getHeight(); i++) {
            for (double j = 0; j < this.taille.getWidth(); j++) {
                list.add(new Case(new Point((int)i, (int)j)));
            }
        }
        return list;
    }

    public void setTerrain(List<Case> terrain) {
        this.terrain = terrain;
    }

    public List<Case> getTerrain() {
        return terrain;
    }

    public void setTaille(Dimension taille) {
        this.taille = taille;
        this.terrain = getFreshTerrain();
    }

    public Dimension getTaille() {
        return taille;
    }
}
