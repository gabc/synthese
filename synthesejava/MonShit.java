package synthesejava;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MonShit extends Animal {
    private int health;
    private int force;
    private static Hashtable<String, ChartData> dna;

    public MonShit(int x, int y) {
        super();
        this.taille = new Taille(x, y);
        this.health = 40;
        this.force = 10;
        dna = new Hashtable<String, ChartData>();
        dna.put("force", new ChartData());
    }

    @Override
    public int aggressivite() {
        return 10;
    }

    @Override
    public void statistiques() {
    }

    @Override
    public Boolean canSee(Creature c) {
        return null;
    }

    @Override
    public Boolean canReproduce() {
        return null;
    }

    @Override
    public Boolean canReproduceWith(Creature c) {
        if(c instanceof MonShit){
//            System.out.println("yaye");
            return true;
        } else
            System.out.println("wat");
        return false;
    }

    @Override
    public Creature reproduceWith(Creature c) {
        MonShit o = (MonShit)c;
        MonShit n = new MonShit(this.taille.getX()+1, this.taille.getY());
        if(this.canReproduceWith(c)){
            n.setForce((this.force + o.getForce()) / 2);
            return n;
        }
        return null;
    }

    @Override
    public Boolean update() {
        updateDNA();
        force += 10;
        /* this.taille.move(this.taille.getX() + 1, this.taille.getY() + 1); */
        return true;
    }

    @Override
    public Boolean isAlive() {
        Boolean alivep = true;
        if (this.health <= 0) {
            alivep = false;
            System.out.println("I died");
        }
        return alivep;
    }

    @Override
    public Taille position() {
        return this.taille;
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
    public int getId() {
        return 0;
    }

    @Override
    public Boolean isCarnivore() {
        return true;
    }

    @Override
    public Boolean isAnimal() {
        return true;
    }

    @Override
    public Boolean takeDommage(int force) {
        this.health -= force;
        return this.isAlive();
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
    public Color getColor() {
        return new Color(255, 0, 0);
    }

    @Override
    public void showDNAChart() {
        DNAGraph dc = new DNAGraph("MonTruc", dna);
        dc.setSize(600, 400);
        dc.setLocationRelativeTo(null);
        dc.setVisible(true);
    }

    public void updateDNA() {
        Iterator<Map.Entry<String, ChartData>> it = dna.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, ChartData> entry = it.next();
            entry.getValue().append(SyntheseFrame.tick, force);
        }
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
