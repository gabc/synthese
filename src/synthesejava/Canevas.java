package synthesejava;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import java.awt.Graphics2D;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

public class Canevas extends JPanel implements Scrollable {
    private int sizeRect = 20;
    private CreatureList cl;
    private int maxUnitIncrement = 10;

    public Canevas() {
        setBackground(new Color(0, 0, 0));
    }

    @Override
    public void paintComponent(Graphics g) {
        //        super.paintComponent(g);

        Rectangle drawHere = g.getClipBounds();

        Graphics2D g2 = (Graphics2D)g.create();
        g2.setColor(new Color(255, 255, 255));
        g2.fillRect(drawHere.x, drawHere.y, drawHere.width, drawHere.height);
        if (cl == null) {
            paintLines(g);
            return;
        }
        //        g2.fillRect(drawHere.x, drawHere.y, drawHere.width, drawHere.height);

        Iterator<Creature> cli = this.cl.iterator();
        while (cli.hasNext()) {
            Creature c = cli.next();
            //            g2.setColor(c.getColor());
            //            g2.fillRect(c.taille.getX() * sizeRect, c.taille.getY() * sizeRect,
            //                        (int)c.getTaille().getLargeur() * sizeRect, (int)c.getTaille().getHauteur() * sizeRect);
            g2.drawImage(c.getImage(), null, c.getTaille().getX() * 20, c.getTaille().getY() * 20);
        }
        paintLines(g);
    }

    void repaint(CreatureList cl) {
        this.cl = cl;
        repaint();
    }

    public void paintLines(Graphics g) {
        Graphics2D g2 = (Graphics2D)g.create();
        Rectangle drawHere = g.getClipBounds();


        g2.setColor(new Color(0, 0, 0));

        for (int i = 0; i < this.getHeight(); i++) {
            g2.drawLine(i * sizeRect, 0, i * sizeRect, this.getHeight() * sizeRect);
        }
        for (int i = 0; i < this.getWidth(); i++) {
            g2.drawLine(0, i * sizeRect, this.getWidth() * sizeRect, i * sizeRect);
        }
    }

    public void setSizeRect(int sizeRect) {
        this.sizeRect = sizeRect;
    }

    public int getSizeRect() {
        return sizeRect;
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return new Dimension(SyntheseFrame.maxX * 20, SyntheseFrame.maxY * 20); //return super.getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        //Get the current position.
        int currentPosition = 0;
        if (orientation == SwingConstants.HORIZONTAL) {
            currentPosition = visibleRect.x;
        } else {
            currentPosition = visibleRect.y;
        }

        //Return the number of pixels between currentPosition
        //and the nearest tick mark in the indicated direction.
        if (direction < 0) {
            int newPosition = currentPosition - (currentPosition / maxUnitIncrement) * maxUnitIncrement;
            return (newPosition == 0) ? maxUnitIncrement : newPosition;
        } else {
            return ((currentPosition / maxUnitIncrement) + 1) * maxUnitIncrement - currentPosition;
        }
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        if (orientation == SwingConstants.HORIZONTAL) {
            return visibleRect.width - maxUnitIncrement;
        } else {
            return visibleRect.height - maxUnitIncrement;
        }
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }

    public void setMaxUnitIncrement(int pixels) {
        maxUnitIncrement = pixels;
    }

    public void setPreferredSize(Dimension d) {
        d.setSize(d.width + 1, d.height + 1);
        d.setSize(d.width * 20, d.height * 20);
        super.setPreferredSize(d);
    }
}
