package synthesejava;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import java.util.Iterator;

import javax.swing.JPanel;

public class MiniMap extends JPanel {
    private int sizeRect = 3;
    private CreatureList cl;
    private Rectangle b;
    private int maxx;
    private int maxy;
    private int maxUnitIncrement = 10;

    public MiniMap(int maxx, int maxy) {
        setPreferredSize(new Dimension(maxx * sizeRect, maxy * sizeRect));
        setBackground(new Color(255, 255, 255));
        this.maxx = maxx; // * sizeRect;
        this.maxy = maxy; // * sizeRect;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (this.cl == null || this.b == null)
            return;

        Graphics2D g2 = (Graphics2D)g.create();
        g2.setColor(new Color(255, 155, 0));
        
        g2.drawRect(b.x / 20 * 3, b.y / 20 * 3, b.width / 20 * 3, b.height / 20 * 3);

        g2.setColor(new Color(255, 255, 255));
//        g2.fillRect(0, 0, maxx, maxy);

        Iterator<Creature> cli = this.cl.iterator();
        while (cli.hasNext()) {
            Creature c = cli.next();
            g2.setColor(c.getColor());
            g2.fillRect(c.taille.getX() * sizeRect, c.taille.getY() * sizeRect,
                        (int)c.getTaille().getLargeur() * sizeRect, (int)c.getTaille().getHauteur() * sizeRect);
        }
    }

    void repaint(CreatureList cl, Rectangle b) {
        this.cl = cl;
        this.b = b;
        repaint();
    }
}
