package synthesejava;

import java.awt.Color;

import java.util.Hashtable;

public class Lapin extends Animal{
    private int force;
    private Hashtable<String, ChartData> dna;
    
    public Lapin(int x, int y) {
        super();
        this.taille = new Taille(x, y);
        this.health = 20;
        this.force = 5;
        this.color = new Color(123,123,123);
        this.faim = 10;
        this.maxFaim = 20;
    }

    @Override
    public int aggressivite() {
        return (int)(this.faim - this.maxFaim);
    }

    @Override
    public boolean canAttack(Creature c) {
        return false;
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
        } else
            System.out.println("wat lapin");
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
    public String update() {
        super.update();
        return "fuir";
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
        return 0;
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
}
