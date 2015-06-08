package synthesejava;

import java.awt.Color;

import java.io.File;
import java.io.IOException;

import java.util.Iterator;

import javax.imageio.ImageIO;

public class Foin extends Vegetal {
    public Foin(int x, int y) {
        super();
        this.taille = new Taille(x, y);
        this.health = 5;
        this.color = new Color(218, 184, 16);
        this.faim = 10;
        this.maxFaim = 20;
        this.reproductionCooldown = 10;
        try {
            this.img = ImageIO.read(((new File("img/grass.jpg")).toURI()).toURL());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public boolean mightAttack(Creature c) {
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
        if (c instanceof Foin && this.reproductionCooldown <= 0) {
            return true;
        }
        return false;
    }

    @Override
    public Creature reproduceWith(Creature c) {
        if (!(c instanceof Foin))
            return null;
        Foin o = (Foin)c;
        Foin n = new Foin(this.taille.getX() + Utils.randInt(-1, 1), this.taille.getY() + Utils.randInt(-1, 1));
        if (this.canReproduceWith(c)) {
            this.reproductionCooldown = 20;
            return n;
        }
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
    public boolean isCarnivore() {
        return false;
    }

    @Override
    public boolean isAnimal() {
        return false;
    }

    @Override
    public Creature interactWith(Creature creature) {
        if (creature.equals(this)) {
            return null;
        }
        //System.out.println(this.getDistance(creature));
        return reproduceWith(creature);
    }

    @Override
    public void showDNAChart() {
    }

    public String update(CreatureList cl) {
        this.reproductionCooldown--;
        if (this.reproductionCooldown <= 0) {
            Iterator<Creature> cli = cl.listIterator();
            cli = cl.listIterator();
            while (cli.hasNext()) {
                Creature c = cli.next();
                c = this.reproduceWith(c);
                if (c != null)
                    cl.append(c);
            }
        }
        return null;
    }

    @Override
    public void changeDNA() {
    }
}
