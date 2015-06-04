package synthesejava;

public class Taille {
    private int largeur;
    private int hauteur;
    private int x;
    private int y;

    public Taille(int x, int y) {
        if(x < 0)
            x = 0;
        if(y < 0)
            y = 0;
        this.x = x;
        this.y = y;
        this.largeur = 1;
        this.hauteur = 1;
    }

    public Taille(int x, int y, int hauteur, int largeur) {
        this.x = x;
        this.y = y;
        this.largeur = largeur;
        this.hauteur = hauteur;
    }
    
    public Taille clone(){
        return new Taille(this.x, this.y, this.hauteur, this.largeur);    
    }
    
    public double distance(Taille t) {
        return Math.sqrt(Math.pow(this.x - t.getX(), 2) + Math.pow(this.y - t.getY(), 2));
    }

    public Boolean equals(Taille t) {
        if (getX() == t.getX() && getY() == t.getY())
            return true;
        else
            return false;
    }

    public void move(int nx, int ny) {
        if (nx >= 0)
            this.x = nx;
        if (ny >= 0)
            this.y = ny;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    public int getLargeur() {
        return largeur;
    }

    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }

    public int getHauteur() {
        return hauteur;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }
}
