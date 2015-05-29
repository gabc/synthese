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

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class SyntheseFrame extends JFrame {
    public CreatureList liste;
    private Ecouteur ec;
    private Canevas c;
    private DNAChanger dc;
    private JButton jButton1 = new JButton();
    private JButton btnAjout = new JButton();
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
        //this.getContentPane().setLayout(null);
        //this.setSize(new Dimension(600, 400));
        this.setTitle("Synthese");
        this.drawArea = new Dimension(100, 100);
        ec = new Ecouteur();
        this.c = new Canevas();
        this.c.setPreferredSize(new Dimension(1000, 1000));
        this.scrollpane = new JScrollPane(this.c, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.scrollpane.setPreferredSize(this.drawArea);
                
        
        jButton1.setText("jButton1");
        //jButton1.setBounds(new Rectangle(35, 330, 75, 21));
        jButton1.addActionListener(ec);
        btnAjout.setText("jButton2");
        //btnAjout.setBounds(new Rectangle(195, 315, 75, 21));
        btnAjout.addActionListener(ec);
        this.liste = new CreatureList();
        this.liste.add(new MonShit(0, 0));
        this.liste.add(new MonShit(1, 4));

        this.getContentPane().add(btnAjout, BorderLayout.NORTH);
        this.getContentPane().add(jButton1, BorderLayout.SOUTH);

        /*         Hashtable<String, Component> t = new Hashtable<String, Component>();
        JButton b = new JButton();
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("ASDF");
            }
        });
        t.put("Hi!", b);
        t.put("Yo", new JButton());
        t.put("ALALA", new JSlider());
        this.dc = new DNAChanger(t);
        this.dc.setSize(600, 400);
        this.dc.setLocationRelativeTo(this);
        this.dc.setVisible(true); */

        this.add(this.scrollpane, BorderLayout.CENTER);
        this.scrollpane.repaint();

        this.c.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                SyntheseFrame.this.c.scrollRectToVisible(new Rectangle(e.getX(), e.getY(), 1, 1));
                int x = e.getX() / SyntheseFrame.this.c.getSizeRect();
                int y = e.getY() / SyntheseFrame.this.c.getSizeRect();
                try {
                    liste.getCreature(x, y).showDNAChart();
                } catch (Exception ex) {
                    System.out.println("Y'a rien la");
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
                    } else {
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
                new Thread(new Runnable() {
                    public void run() {
                        Thread.currentThread();
                        while (!Thread.currentThread().isInterrupted()) {
                            try {
                                Thread.sleep(1000);
                                mainLoop();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
                }).start();
            } else if (e.getSource() == btnAjout) {
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(SyntheseFrame.this);
                File file = fc.getSelectedFile();
                System.out.println(file.getAbsolutePath());
                //                LibHandler lh = new LibHandler(file, "macreature");
                try {
                    ClassHackThing.addFile(file);
                } catch (IOException f) {
                    f.printStackTrace();
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
