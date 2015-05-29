package synthesejava;

import java.awt.Color;

public class Foin extends Vegetal{
    public Foin(int x, int y) {
        super();
        this.taille = new Taille(x, y);
        this.health = 5;
        this.color = new Color(218,184,16);
        this.faim = 10;
        this.maxFaim = 20;
    }

    @Override
    public int aggressivite() {
        return 0;
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
        return false;
    }

    @Override
    public boolean canReproduce() {
        return true;
    }

    @Override
    public boolean canReproduceWith(Creature c) {
        return false;
    }

    @Override
    public Creature reproduceWith(Creature c) {
        return null;
    }

    @Override
    public void changeStats() {
    }

    @Override
    public int health() {
        return 0;
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
        return false;
    }

    @Override
    public Creature interactWith(Creature creature) {
        return null;
    }

    @Override
    public void showDNAChart() {
    }
}
