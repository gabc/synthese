package synthesejava;

import java.awt.Color;

import java.awt.Image;

import java.awt.image.BufferedImage;

import java.io.File;

import java.io.IOException;

import java.util.Hashtable;

import javax.imageio.ImageIO;

public class Lapin extends Animal {
    private int force;
    private Hashtable<String, ChartData> dna;

    public Lapin(int x, int y) {
        super();
        this.taille = new Taille(x, y);
        this.health = 20;
        this.force = 5;
        this.color = new Color(123, 123, 123);
        this.faim = 10;
        this.maxFaim = 20;
        try {
            this.img = ImageIO.read(((new File("img/lapin.jpg")).toURI()).toURL());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int aggressivite() {
        return (int)(this.faim - this.maxFaim);
    }

    @Override
    public boolean canAttack(Creature c) {
        if (c.isAnimal())
            return false;
        else {
            return getDistance(c) <= 1.5;
        }
    }

    @Override
    public boolean mightAttack(Creature c) {
        if (c.isAnimal())
            return false;
        else {
            if (this.faim < 5)
                return true;
            else
                return false;
        }
    }

    @Override
    public void statistiques() {
    }

    @Override
    public boolean canSee(Creature c) {
        return true;
    }

    @Override
    public boolean canReproduce() {
        return false;
    }

    @Override
    public boolean canReproduceWith(Creature c) {
        if (c instanceof Lapin) {
            return true;
        }
        return false;
    }

    @Override
    public Creature reproduceWith(Creature c) {
        Lapin o = (Lapin)c;
        Lapin n = new Lapin(this.taille.getX() + 1, this.taille.getY());
        if (this.canReproduceWith(c)) {
            return n;
        }
        return null;
    }

    @Override
    public String update(CreatureList cl) {
        super.update(cl);
        Creature c = null;
        System.out.println(this.taille.getX());
        if (this.goal == null)
            this.goal = this.findTarget(cl);

        c = this.isTargeted(cl);
        if (c != null){
            this.goAwayFrom(c, cl);
            return null;
        }
        
        if (this.faim < 5 && !(this.goal instanceof DummyCreature)) {
            if (this.goal == null)
                System.out.println("shit");
            if (this.canAttack(this.goal))
                this.attack(this.goal);
            else
                this.goTowards(this.goal, cl);
        } else {
            if (this.goal == null)
                System.out.println("shit");
            if (this.goTowards(this.goal, cl) && !(this.goal instanceof DummyCreature))
                this.attack(this.goal);
        }
        return null;
    }

    @Override
    public void changeStats() {
    }

    @Override
    public int health() {
        return this.health;
    }

    @Override
    public int hitPower() {
        return this.force;
    }

    @Override
    public int defence() {
        return 0;
    }

    @Override
    public boolean isCarnivore() {
        return false;
    }

    @Override
    public boolean isAnimal() {
        return true;
    }

    @Override
    public Creature interactWith(Creature c) {
        return null;
    }

    @Override
    public void showDNAChart() {
    }

    @Override
    public void updateDNA() {
    }
}
