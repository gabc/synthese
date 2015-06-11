package synthesejava;

public class DummyCreature extends Creature {
    DummyCreature(Taille taille) {
        this.taille = taille;
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
        return false;
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
    public boolean mightAttack(Creature creature) {
        return false;
    }

    @Override
    public void changeDNA() {
    }
}
