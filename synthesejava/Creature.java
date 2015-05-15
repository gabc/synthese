package synthesejava;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import java.util.ArrayList;
import java.util.Hashtable;


public abstract class Creature {
    Boolean isCarnivore;
    Boolean isAnimal;
    int id;
    Taille taille;

    public abstract int aggressivite();

    public abstract Boolean canAttack(Creature c);

    public abstract void statistiques();

    public abstract Boolean canSee(Creature c);

    public abstract Boolean canReproduce();

    public abstract Boolean canReproduceWith(Creature c);

    public abstract Creature reproduceWith(Creature c);

    public abstract String update();

    public abstract Boolean isAlive();

    public abstract Taille position();

    public abstract void changeStats();

    public abstract int health();

    public abstract int hitPower();

    public abstract int defence();

    public abstract int getId();

    public abstract Boolean isCarnivore();

    public abstract Boolean isAnimal();

    public abstract Boolean takeDommage(int force);

    public abstract Creature interactWith(Creature creature);

    public abstract Color getColor();

    public abstract void showDNAChart();

    public abstract void goTowards(Creature c);

    public double getDistance(Creature c) {
        return this.getTaille().distance(c.getTaille());
    }

    public Boolean equals(Creature c) {
        return this.taille.getX() == c.taille.getX() && this.taille.getY() == c.taille.getY();
    }

    public void attack(Creature c) {
        if (!this.equals(c))
            c.takeDommage(this.hitPower());
    }

    public Boolean getIsCarnivore() {
        return isCarnivore;
    }

    public Boolean getIsAnimal() {
        return isAnimal;
    }

    public int getId1() {
        return id;
    }

    public void setTaille(Taille taille) {
        this.taille = taille;
    }

    public Taille getTaille() {
        return taille;
    }
}
