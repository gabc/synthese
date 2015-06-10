package synthesejava;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Loup extends Animal {
    private int force;
    private Hashtable<String, ChartData> dna;

    public Loup(int x, int y) {
        super();
        this.taille = new Taille(x, y);
        this.health = 40;
        this.force = 10;
        this.color = new Color(255, 0, 0);
        this.faim = 10;
        this.maxFaim = 20;

        try {
            this.img = ImageIO.read(((new File("img/loup.jpg")).toURI()).toURL());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int aggressivite() {
        return (int)(this.faim - this.maxFaim);
    }

    @Override
    public void statistiques() {
    }

    @Override
    public boolean canSee(Creature c) {
        if (c == null)
            return false;
        return true;
    }

    @Override
    public boolean canReproduce() {
        return true;
    }

    @Override
    public boolean canReproduceWith(Creature c) {
        if (c instanceof Loup) {
            return true;
        } else
            System.out.println("wat");
        return false;
    }

    @Override
    public Creature reproduceWith(Creature c) {
        Loup o = (Loup)c;
        Loup n = new Loup(this.taille.getX() + 1, this.taille.getY());
        if (this.canReproduceWith(c)) {
            n.setForce((this.force + o.getForce()) / 2);
            return n;
        }
        return null;
    }

    @Override
    public boolean canAttack(Creature c) {
        if (this.equals(c))
            return false;

        if (c.isAnimal() == false)
            return false;
        if (canSee(c) && getDistance(c) <= 1.5)
            return true;
        else {
            goal = c;
        }
        return false;
    }

    @Override
    public boolean mightAttack(Creature c) {
        if (c.isAnimal())
            return true;
        else
            return false;
    }

    @Override
    public String update(CreatureList cl) {
        super.update(cl);
        System.out.println(this.faim);
        Creature c = null;

        if (this.goal == null)
            this.goal = this.findTarget(cl);

        c = this.isTargeted(cl);
        if (c != null)
            this.goAwayFrom(c, cl);

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
        return true;
    }

    @Override
    public boolean isAnimal() {
        return true;
    }

    public void construct() {
        System.out.println("LOLILOL");
    }

    @Override
    public Creature interactWith(Creature creature) {
        if (creature.equals(this)) {
            return null;
        }
        //System.out.println(this.getDistance(creature));
        return reproduceWith(creature);
        /*  if (this.getDistance(creature) <= 3) {
            this.attack(creature);
        } else {
            // go towards
        } */
    }

    @Override
    public void showDNAChart() {
        DNAGraph dc = new DNAGraph("Loupidou", dna);
        dc.setSize(600, 400);
        dc.setLocationRelativeTo(null);
        dc.setVisible(true);
    }

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

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setForce(int force) {
        this.force = force;
    }

    public int getForce() {
        return force;
    }
}
