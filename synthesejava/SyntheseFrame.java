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
    private PauseThread pauseThread;

    private boolean runningState;
    private JScrollPane scrollpane;
    public static int tick = 0;
    private Dimension drawArea;

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
        this.c.setPreferredSize(new Dimension(1000, 1000));
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
        this.liste.add(new MonShit(0, 0));
        this.liste.add(new Lapin(1, 4));

        this.getContentPane().add(b, BorderLayout.NORTH);
        this.getContentPane().add(jButton1, BorderLayout.SOUTH);

        this.add(this.scrollpane, BorderLayout.CENTER);
        this.scrollpane.repaint();

        pauseThread = new PauseThread();
        playThread = new PlayThread(1);

        pauseThread.start();
        playThread.start();

        this.c.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                int x = e.getX() / SyntheseFrame.this.c.getSizeRect();
                int y = e.getY() / SyntheseFrame.this.c.getSizeRect();

                if (ajoutShit.isSelected()) {
                    SyntheseFrame.this.liste.add(new MonShit(x, y));
                    return;
                } else if (ajoutLapin.isSelected()) {
                    SyntheseFrame.this.liste.add(new Lapin(x, y));
                    return;
                } else if (ajoutFoin.isSelected()) {
                    SyntheseFrame.this.liste.add(new Foin(x, y));
                    return;
                }

                try {
                    liste.getCreature(x, y).showDNAChart();
                } catch (Exception ex) {
                    //                    System.out.println("Y'a rien la");
                }
            }
        });
    }

    private Boolean onOccupedSpace(Creature c, CreatureList cl) {
        Iterator<Creature> cli = cl.iterator();
        while (cli.hasNext()) {
            Creature t = cli.next();
            if (c.getTaille().equals(t.getTaille())) {
                return true;
            }
        }
        return false;
    }

    private void mainLoop() {
        String action;
        System.out.println(this.liste.size());
        CreatureList toAdd = new CreatureList();
        Iterator<Creature> cli = liste.iterator();
        while (cli.hasNext()) {
            Creature c = cli.next();
            if (!c.isAlive()) {
                cli.remove();
            }
        }

        Iterator<Creature> clj = liste.listIterator();
        cli = liste.listIterator();
        while (cli.hasNext()) {
            Creature c = cli.next();
            if ((action = c.update()) != null) {
                clj = liste.listIterator();
                while (clj.hasNext()) {
                    Creature d = clj.next();
                    Creature temp;
                    if (action.equals("attack")) {
                        if (c.canAttack(d)) {
                            c.attack(d);
                        }
                    } else if (action.equals("reproduce")) {
                        temp = c.interactWith(d);
                        if (temp != null && !onOccupedSpace(temp, liste) && !onOccupedSpace(temp, toAdd) &&
                            liste.size() < 20) {
                            toAdd.add(temp);
                        }
                    } else if (action.equals("goto")) {
                        Taille oldc = c.getTaille();
                        Taille oldd = d.getTaille();
                        c.goTowards(d);
                        if (c.getDistance(d) == 0) {
                            c.setTaille(oldc);
                            d.setTaille(oldd);
                        }
                    } else if (action.equals("wander")) {
                        c.canAttack(d);
                        if (c.getGoal() == null)
                            c.goTowards(new Taille(Utils.randInt(0, 1000), Utils.randInt(0, 1000)));
                        else {
                            c.goTowards(d);
                        }
                    } else if (action.equals("fuir")) {
                        c.goAwayFrom(d);
                    }
                }
            }
        }

        liste.addAll(toAdd);
        this.c.invalidate();
        this.c.repaint(liste);

        this.scrollpane.repaint();
        tick++;
    }

    protected class Ecouteur implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == jButton1) {
                if (!runningState) {
                    pauseThread.interrupt();
                    playThread.interrupt();
                } else {
                    playThread.interrupt();
                    pauseThread.interrupt();
                }
                runningState = !runningState;
            }
        }
    }

    public class PlayThread extends Thread {
        private boolean state;
        private int i;
        public PlayThread(int i) {
            state = false;
            this.i = i;
        }

        public synchronized void run() {
            if(i == 1){
                i = 0;
                state = true;
            }
            while (true) {
                try {
                    Thread.currentThread().sleep(400);

                    while (state)
                        wait();
                } catch (InterruptedException e) {
                    System.out.println("playThread interupt");
                    state = !state;
                }
                mainLoop();
            }
        }
    }

    public class PauseThread extends Thread {
        private boolean state;

        public PauseThread() {
            state = false;
        }

        public synchronized void run() {
            while (true) {
                try {
                    Thread.currentThread().sleep(400);

                    while (state)
                        wait();
                } catch (InterruptedException e) {
                    System.out.println("PauseThread interupt");
                    state = !state;
                }
                c.invalidate();
                c.repaint(liste);
                scrollpane.repaint();
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
