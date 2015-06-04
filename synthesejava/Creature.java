package synthesejava;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import java.util.ArrayList;
import java.util.Hashtable;


public abstract class Creature {
    boolean isCarnivore;
    boolean isAnimal;
    int id;
    int health;
    Taille taille;
    Creature goal;
    Color color;
    double faim;
    double maxFaim;
    int reproductionCooldown;

    public abstract int aggressivite();

    public abstract boolean canAttack(Creature c);

    public abstract void statistiques();

    public abstract boolean canSee(Creature c);

    public abstract boolean canReproduce();

    public abstract boolean canReproduceWith(Creature c);

    public abstract Creature reproduceWith(Creature c);

    public abstract void changeStats();

    public abstract int health();

    public abstract int hitPower();

    public abstract int defence();

    public abstract int getId();

    public abstract boolean isCarnivore();

    public abstract boolean isAnimal();

    public abstract Creature interactWith(Creature creature);

    public abstract void showDNAChart();

    public abstract boolean mightAttack(Creature creature);

    public double getDistance(Creature c) {
        if (c == null)
            return 0;
        return this.getTaille().distance(c.getTaille());
    }

    public boolean equals(Creature c) {
        return this.taille.getX() == c.taille.getX() && this.taille.getY() == c.taille.getY();
    }

    public void attack(Creature c) {
        if (!this.equals(c))
            if (!c.takeDommage(this.hitPower())) {
                System.out.println("Ymange");
                this.mange(c.getFaim());
                this.goal = null;
            }
    }

    public boolean getIsCarnivore() {
        return isCarnivore;
    }

    public boolean getIsAnimal() {
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

    private int ilast = 0;

    /*
    * Are we there yet?
    */

    public boolean goTowards(Creature c, CreatureList cl) {
        if (getDistance(c) <= 1.5 && !this.equals(c)) {
            ilast = 0;
            return true;
        } else {
            ilast++;
            if (ilast > 10)
                System.out.println(c);
        }

        int px = 0;
        int py = 0;

        if (this.getTaille().getX() < c.getTaille().getX()) {
            px++;
        }
        if (this.getTaille().getY() < c.getTaille().getY()) {
            py++;
        }
        if (this.getTaille().getX() > c.getTaille().getX()) {
            px--;
        }
        if (this.getTaille().getY() > c.getTaille().getY()) {
            py--;
        }
        Taille t = null;

        t = (Taille)this.taille.clone();

        this.taille.move(getTaille().getX() + px, getTaille().getY() + py);
        if (!SyntheseFrame.onOccupedSpace(this, cl)) {
            System.out.println("Samespace");
            this.taille = t;
        }
        return false;
    }

    public void goTowards(Taille c) {
        int px = 0;
        int py = 0;

        if (this.getTaille().getX() < c.getX()) {
            px++;
        }
        if (this.getTaille().getY() < c.getY()) {
            py++;
        }
        if (this.getTaille().getX() > c.getX()) {
            px--;
        }
        if (this.getTaille().getY() > c.getY()) {
            py--;
        }
        this.taille.move(getTaille().getX() + px, getTaille().getY() + py);
    }

    public void goAwayFrom(Creature c) {
        int px = 0;
        int py = 0;

        if (this.getTaille().getX() < c.getTaille().getX()) {
            px--;
        }
        if (this.getTaille().getY() < c.getTaille().getY()) {
            py--;
        }
        if (this.getTaille().getX() > c.getTaille().getX()) {
            px++;
        }
        if (this.getTaille().getY() > c.getTaille().getY()) {
            py++;
        }

        this.taille.move(getTaille().getX() + px, getTaille().getY() + py);
    }

    public boolean isAlive() {
        boolean alivep = true;
        if (this.health <= 0) {
            System.out.println("isded");
            alivep = false;
        }
        return alivep;
    }

    public boolean takeDommage(int force) {
        this.health -= force;
        return this.isAlive();
    }

    public Creature getGoal() {
        return this.goal;
    }

    public void setGoal(Creature c) {
        if (this.equals(c))
            this.goal = null;
        else
            this.goal = c;
    }

    public void setGoal() {
        this.setGoal(new DummyCreature(new Taille(Utils.randInt(this.getTaille().getX() - 5, this.getTaille().getX() + 5),
                                                  Utils.randInt(this.getTaille().getY() - 5, this.getTaille().getY() + 5))));
    }

    public Color getColor() {
        return this.color;
    }

    public Taille position() {
        return this.taille;
    }

    public String update() {
        this.faim -= 0.5;
        return null;
    }

    void mange(double i) {
        this.faim += i;
        if (this.faim > this.maxFaim)
            this.faim = this.maxFaim;
    }

    double getFaim() {
        return this.faim;
    }
}
