package synthesejava;

import java.awt.Color;

import java.awt.Component;
import java.awt.Image;

import java.awt.image.BufferedImage;

import java.io.File;

import java.io.IOException;

import java.util.Hashtable;

import java.util.Iterator;

import javax.imageio.ImageIO;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Lapin extends Animal {
    private int force;

    public Lapin(int x, int y) {
        super();
        this.taille = new Taille(x, y);
        this.health = 20;
        this.force = 15;
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
        if (c instanceof Lapin && c.reproductionCooldown <= 0 && this.getDistance(c) <= 1.5) {
            return true;
        }
        return false;
    }

    @Override
    public Creature reproduceWith(Creature c) {
        if(!(c instanceof Lapin)){
            return null;
        }
        Lapin o = (Lapin)c;
        Lapin n = new Lapin(this.taille.getX() + Utils.randInt(-1, +1), this.taille.getY() + Utils.randInt(-1, +1));
        if (this.canReproduceWith(c)) {
            return n;
        }
        return null;
    }

    @Override
    public String update(CreatureList cl) {
        super.update(cl);
        this.reproductionCooldown--;
        Creature c = null;
        System.out.println(this.taille.getX());
        if (this.goal == null)
            this.goal = this.findTarget(cl);

        c = this.isTargeted(cl);
        if (c != null) {
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

        if (this.reproductionCooldown <= 0) {
            Iterator<Creature> cli = cl.listIterator();
            cli = cl.listIterator();
            while (cli.hasNext()) {
                c = cli.next();
                Creature nc = this.reproduceWith(c);
                if (nc != null && cl.onOccupedSpace(nc)) {
                    cl.append(nc);
                    this.setReproductionCooldown(15);
                    c.setReproductionCooldown(15);
                }
            }
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
    public void changeDNA() {
        Hashtable<String, Component> t = new Hashtable<String, Component>();
        JSpinner fspin = new JSpinner(new SpinnerNumberModel(this.faim, 0, 100, 0.5));
        fspin.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSpinner mySpinner = (JSpinner)(e.getSource());
                faim = Double.parseDouble(mySpinner.getValue().toString());
            }
        });
        JSpinner forcespin = new JSpinner(new SpinnerNumberModel(this.force, 0, 100, 1));
        forcespin.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSpinner mySpinner = (JSpinner)(e.getSource());
                force = Integer.parseInt(mySpinner.getValue().toString());
            }
        });
        JSpinner healths = new JSpinner(new SpinnerNumberModel(this.health, 0, 100, 1));
        healths.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSpinner mySpinner = (JSpinner)(e.getSource());
                health = Integer.parseInt(mySpinner.getValue().toString());
            }
        });
        t.put("faim!", fspin);
        t.put("force", forcespin);
        t.put("health", healths);
        DNAChanger dc = new DNAChanger(t);
        dc.setSize(600, 400);
        dc.setVisible(true);
    }
}
