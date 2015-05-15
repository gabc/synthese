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
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class SyntheseFrame extends JFrame {
    public CreatureList liste;
    private Ecouteur ec;
    private Canevas c;
    private DNAChanger dc;
    private JButton jButton1 = new JButton();
    private JButton btnAjout = new JButton();

    public static int tick = 0;

    public SyntheseFrame() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(null);
        this.setSize(new Dimension(600, 400));
        this.setTitle("Synthese");
        ec = new Ecouteur();
        this.c = new Canevas(new Rectangle(0, 0, 475, 275));
        jButton1.setText("jButton1");
        jButton1.setBounds(new Rectangle(35, 330, 75, 21));
        jButton1.addActionListener(ec);
        btnAjout.setText("jButton2");
        btnAjout.setBounds(new Rectangle(195, 315, 75, 21));
        btnAjout.addActionListener(ec);
        this.liste = new CreatureList();
        this.liste.add(new MonShit(0, 0));
        this.liste.add(new MonShit(1, 1));

        this.getContentPane().add(btnAjout, null);
        this.getContentPane().add(jButton1, null);

        Hashtable<String, Component> t = new Hashtable<String, Component>();
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
        this.dc.setVisible(true);

        this.add(this.c);
        this.c.repaint();

        this.c.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                int x = e.getX() / SyntheseFrame.this.c.getSizeRect();
                int y = e.getY() / SyntheseFrame.this.c.getSizeRect();
                try {
                    System.out.println(liste.getCreature(x, y).getTaille().getX());
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
                return false;
            }
        }
        return true;
    }

    private void mainLoop() {
        CreatureList toAdd = new CreatureList();
        Iterator<Creature> cli = liste.iterator();
        while (cli.hasNext()) {
            Creature c = cli.next();
            if (!c.isAlive()) {
                cli.remove();
            }
        }

        Iterator<Creature> clj = liste.iterator();
        cli = liste.iterator();
        while (cli.hasNext()) {
            Creature c = cli.next();
            if (c.update()) {
                while (clj.hasNext()) {
                    Creature d = clj.next();
                    Creature temp;
                    temp = c.interactWith(d);
                    if (temp != null && onOccupedSpace(temp, liste))
                        toAdd.add(temp);
                }
            }
            if (c.getTaille().getX() == 1 && c.getTaille().getY() == 0) {
                System.out.println("What the fuck");
                System.out.println(c);
            }
        }
        liste.addAll(toAdd);
        System.out.println(liste.size());
        this.c.repaint(liste);
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
