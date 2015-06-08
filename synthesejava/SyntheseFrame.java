package synthesejava;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import java.awt.Graphics;

import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.File;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import java.util.Random;

import java.util.concurrent.locks.ReentrantLock;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class SyntheseFrame extends JFrame {
    public CreatureList liste;
    private Ecouteur ec;
    private Canevas c;
    private DNAChanger dc;
    private JButton jButton1 = new JButton();
    private JToggleButton ajoutLapin = new JToggleButton();
    private JToggleButton ajoutShit = new JToggleButton();
    private JToggleButton ajoutFoin = new JToggleButton();
    private JToggleButton clearAjout = new JToggleButton();

    private PlayThread playThread;

    private boolean runningState;
    private JScrollPane scrollpane;
    public static int tick = 0;
    private Dimension drawArea;

    static public int maxX = 20;
    static public int maxY = 20;

    public SyntheseFrame() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setTitle("Synthese");
        this.drawArea = new Dimension(100, 100);
        ec = new Ecouteur();
        this.c = new Canevas();
        this.c.setPreferredSize(new Dimension(SyntheseFrame.maxX, SyntheseFrame.maxY));
        this.scrollpane =
                new JScrollPane(this.c, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.scrollpane.setPreferredSize(this.drawArea);

        jButton1.setText("go");
        jButton1.addActionListener(ec);

        ButtonGroup bg = new ButtonGroup();
        Box b = Box.createHorizontalBox();

        ajoutLapin.setText("Lapin");
        ajoutLapin.addActionListener(ec);
        bg.add(ajoutLapin);
        b.add(ajoutLapin);

        ajoutShit.setText("Shit");
        ajoutShit.addActionListener(ec);
        bg.add(ajoutShit);
        b.add(ajoutShit);

        ajoutFoin.setText("Foin");
        ajoutFoin.addActionListener(ec);
        bg.add(ajoutFoin);
        b.add(ajoutFoin);

        clearAjout.setText("Clear");
        clearAjout.addActionListener(ec);
        bg.add(clearAjout);
        b.add(clearAjout);

        this.liste = new CreatureList();

        this.getContentPane().add(b, BorderLayout.NORTH);
        this.getContentPane().add(jButton1, BorderLayout.SOUTH);

        this.add(this.scrollpane, BorderLayout.CENTER);
        this.scrollpane.repaint();

        playThread = new PlayThread();

        playThread.start();

        this.c.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                int x = e.getX() / SyntheseFrame.this.c.getSizeRect();
                int y = e.getY() / SyntheseFrame.this.c.getSizeRect();

                if (ajoutShit.isSelected()) {
                    SyntheseFrame.this.liste.append(new MonShit(x, y));
                    return;
                } else if (ajoutLapin.isSelected()) {
                    SyntheseFrame.this.liste.append(new Lapin(x, y));
                    return;
                } else if (ajoutFoin.isSelected()) {
                    SyntheseFrame.this.liste.append(new Foin(x, y));
                    return;
                }

                try {
                    liste.getCreature(x, y).changeDNA();
                } catch (Exception ex) {
                    //                    System.out.println("Y'a rien la");
                }
            }
        });
    }

    public static boolean onOccupedSpace(Creature c, CreatureList cl) {
        Iterator<Creature> cli = cl.iterator();
        while (cli.hasNext()) {
            Creature c2 = cli.next();
            if (c2.taille.getX() == c.taille.getX() && c2.taille.getY() == c.taille.getY() && !c.equals(c2)) {
                return true;
            }
        }
        return false;
    }

    private Creature canFindToAttack(Creature c, CreatureList lst) {
        Iterator<Creature> cli = lst.listIterator();
        cli = lst.listIterator();
        while (cli.hasNext()) {
            Creature d = cli.next();
            if (c.mightAttack(d)) {
                return d;
            }
        }
        return null;
    }

    private void mainLoop() {
        Iterator<Creature> cli = liste.iterator();
        while (cli.hasNext()) {
            Creature c = cli.next();
            if (!c.isAlive()) {
                cli.remove();
            }
        }

        cli = liste.listIterator();
        while (cli.hasNext()) {
            Creature c = cli.next();
            c.update(liste);
        }

        liste.update();
        this.c.invalidate();
        this.c.repaint(liste);

        this.scrollpane.repaint();
        tick++;
    }


    protected class Ecouteur implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == jButton1) {
                if (!runningState) {
                    playThread.interrupt();
                } else {
                    playThread.interrupt();
                }
                runningState = !runningState;
            }
        }
    }

    public class PlayThread extends Thread {
        private boolean state;

        public PlayThread() {
            state = false;
        }

        public synchronized void run() {
            while (true) {
                try {
                    Thread.currentThread().sleep(400);
                } catch (InterruptedException e) {
                    System.out.println("playThread interupt");
                    state = !state;
                }

                if (state)
                    mainLoop();
                else {
                    c.invalidate();
                    liste.update();
                    c.repaint(liste);
                    scrollpane.repaint();
                }
            }
        }
    }

    public static void main(String[] args) {
        SyntheseFrame frame = new SyntheseFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}
