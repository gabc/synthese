package synthesejava;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import java.awt.FlowLayout;
import java.awt.Graphics;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridBagLayoutInfo;
import java.awt.Image;
import java.awt.Insets;
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

import javax.imageio.ImageIO;

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
    public CreatureMap map;
    private Ecouteur ec;
    private Canevas c;
    private MiniMap minimap;
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

    static public int maxX = 50;
    static public int maxY = 50;

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

        this.minimap = new MiniMap(SyntheseFrame.maxX, SyntheseFrame.maxY);
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

        this.add(this.scrollpane, BorderLayout.CENTER);

        JPanel botPan = new JPanel(new FlowLayout());
        botPan.add(jButton1);
        botPan.add(this.minimap);

        this.getContentPane().add(botPan, BorderLayout.SOUTH);

        this.scrollpane.repaint();

        playThread = new PlayThread();

        playThread.start();

        this.c.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                int x = e.getX() / SyntheseFrame.this.c.getSizeRect();
                int y = e.getY() / SyntheseFrame.this.c.getSizeRect();

                if (SwingUtilities.isRightMouseButton(e)) {
                    Creature c = liste.getCreature(x, y);
                    liste.remove(c);
                    return;
                }

                if (SwingUtilities.isMiddleMouseButton(e)) {
                    SyntheseFrame.this.clear();
                    return;
                }

                Creature c;
                if (ajoutShit.isSelected()) {
                    c = new Loup(x, y);
                    SyntheseFrame.this.liste.append(c);
                    map.insert(c, x, y);
                    return;
                } else if (ajoutLapin.isSelected()) {
                    c = new Lapin(x, y);
                    SyntheseFrame.this.liste.append(c);
                    map.insert(c, x, y);
                    return;
                } else if (ajoutFoin.isSelected()) {
                    c = new Foin(x, y);
                    SyntheseFrame.this.liste.append(c);
                    map.insert(c, x, y);
                    return;
                }

                try {
                    liste.getCreature(x, y).changeDNA();
                } catch (Exception ex) {

                }
            }
        });

        this.minimap.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                int x = e.getX() / 3;
                int y = e.getY() / 3;

                scrollpane.getHorizontalScrollBar().setValue((x * 20) - c.getVisibleRect().width / 2);
                scrollpane.getVerticalScrollBar().setValue((y * 20) - c.getVisibleRect().height / 2);
            }
        });
    }

    public void clear() {
        JPanel contentPane = (JPanel)this.getContentPane();

        contentPane.removeAll();
        contentPane.add(new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = null;
                try {
                    img = ImageIO.read(((new File("img/oops.png")).toURI()).toURL());
                } catch (IOException e) {
                    System.out.println("non");
                }
                g.drawImage(img, 0, 0, null);
            }
        });
        contentPane.revalidate();
        contentPane.repaint();
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
        this.minimap.repaint(liste, scrollpane.getViewport().getBounds());
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
                    minimap.repaint(liste, c.getVisibleRect());
                }
            }
        }
    }

    public static void main(String[] args) {
        SyntheseFrame frame = new SyntheseFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}
