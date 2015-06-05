package synthesejava;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;

import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;


public abstract class Creature {
    static int ID = 0;
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
    BufferedImage img;

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

    public abstract boolean isCarnivore();

    public abstract boolean isAnimal();

    public abstract Creature interactWith(Creature creature);

    public abstract void showDNAChart();

    public abstract boolean mightAttack(Creature creature);
    
    public abstract void updateDNA();

    public Creature() {
        this.id = ++Creature.ID;
    }

    public double getDistance(Creature c) {
        if (c == null)
            return 0;
        return this.getTaille().distance(c.getTaille());
    }

    public boolean equals(Creature c) {
        if (c == null)
            return false;
        return this.id == c.getId();
    }

    public void attack(Creature c) {
        if (c == null)
            return;
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

    public int getId() {
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
        if (this.equals(c))
            return false;

        if (getDistance(c) <= 1.5) {
            ilast = 0;
            if (this.getGoal() instanceof DummyCreature)
                this.setGoal(null);
            return true;
        } else {
            ilast++;
            if (ilast > 20)
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
        if (SyntheseFrame.onOccupedSpace(this, cl)) {
            System.out.println("Samespace");
            this.taille = t;
            this.setGoal(this.findTarget(cl));
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

    public void goAwayFrom(Creature c, CreatureList cl) {
        if (this.equals(c))
            return;

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

        Taille t = null;
        t = (Taille)this.taille.clone();

        this.taille.move(getTaille().getX() + px, getTaille().getY() + py);
        if (SyntheseFrame.onOccupedSpace(this, cl)) {
            System.out.println("Samespace");
            this.taille = t;
        }
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
        if (c == null || this.equals(c))
            this.goal = null;
        else
            this.goal = c;
    }

    public void setGoal() {
        this.setGoal(new DummyCreature(new Taille(Utils.randInt(this.getTaille().getX() - 1,
                                                                this.getTaille().getX() + 1),
                                                  Utils.randInt(this.getTaille().getY() - 1,
                                                                this.getTaille().getY() + 1))));
    }

    public Color getColor() {
        return this.color;
    }

    public Taille position() {
        return this.taille;
    }

    public String update(CreatureList cl) {
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

    Creature findTarget(CreatureList cl) {
        Iterator<Creature> cli = cl.listIterator();
        cli = cl.listIterator();
        while (cli.hasNext()) {
            Creature c = cli.next();
            if (this.mightAttack(c) && !this.equals(c) && !c.equals(this.getGoal())) {
                /* if (this.canAttack(c))
                    return c; */
                return c;
            }
        }
        return new DummyCreature(new Taille(Utils.randInt(this.getTaille().getX() - 5, this.getTaille().getX() + 5),
                                            Utils.randInt(this.getTaille().getY() - 5, this.getTaille().getY() + 5)));
    }

    Creature isTargeted(CreatureList cl) {
        Iterator<Creature> cli = cl.listIterator();
        cli = cl.listIterator();
        while (cli.hasNext()) {
            Creature c = cli.next();
            if (this.equals(c.getGoal())) {
                return c;
            }
        }
        return null;
    }

    BufferedImage getImage() {
        return this.img;
    }
}
