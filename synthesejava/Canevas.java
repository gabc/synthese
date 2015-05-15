package synthesejava;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.Graphics2D;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Canevas extends JPanel {
    private BufferedImage buffer;
    private Rectangle bounds;
    private int sizeRect = 20;

    public Canevas(Rectangle bounds) {
        setBackground(new Color(0, 0, 0));
        this.bounds = bounds;
        setBounds(bounds);
        buffer = new BufferedImage(bounds.width, bounds.height, BufferedImage.TYPE_INT_ARGB);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g.create();
        Graphics2D gb = buffer.createGraphics();
        gb.setColor(new Color(255, 255, 255));
        for (int i = 0; i < this.getHeight() + 1; i++) {
            gb.drawLine(i * sizeRect, 0, i * sizeRect, this.getHeight() * sizeRect);
        }
        for (int i = 0; i < this.getWidth() + 1; i++) {
            gb.drawLine(0, i * sizeRect, this.getWidth() * sizeRect, i * sizeRect);
        }
        g2.drawImage(buffer, 0, 0, this);
    }

    void repaint(CreatureList cl) {
        buffer = new BufferedImage(bounds.width, bounds.height, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < cl.size(); i++) {
            Creature c = (Creature)cl.get(i);
            Graphics2D g = (Graphics2D)this.buffer.getGraphics();
            g.setColor(c.getColor());
            g.fillRect(c.taille.getX() * sizeRect, c.taille.getY() * sizeRect,
                       (int)c.getTaille().getLargeur() * sizeRect, (int)c.getTaille().getHauteur() * sizeRect);
            repaint();
        }
    }

    public void setSizeRect(int sizeRect) {
        this.sizeRect = sizeRect;
    }

    public int getSizeRect() {
        return sizeRect;
    }
}
