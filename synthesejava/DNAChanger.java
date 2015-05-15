package synthesejava;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Rectangle;

import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class DNAChanger extends JFrame {
    private Hashtable<String, Component> things = new Hashtable<String, Component>();
    private GridLayout gridLayout1;

    public DNAChanger(Hashtable<String, Component> things) {
        try {
            this.things = things;
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.gridLayout1 = new GridLayout(things.size(), 2);
        this.setLayout(gridLayout1);
        
        this.setBounds(new Rectangle(0, 0, things.size()*100, things.size()*50));
        
        CellConstraints cc = new CellConstraints();
        int x = 1;
        int y = 1;
        for (String key : things.keySet()) {
            this.add(new JLabel(key), cc.xy(x, y++));
            this.add(things.get(key), cc.xy(x++, y));
        }
    }
}
