package synthesejava;

import java.awt.Rectangle;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

public class DNAGraph extends JFrame {
    public ChartPanel cp;
    private String nom;
    private Hashtable<String, ChartData> data;

    public DNAGraph(String nom, Hashtable<String, ChartData> data) {
        this.nom = nom;
        this.data = data;
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setBounds(new Rectangle(0, 0, 300, 300));
        XYDataset ds = createDataset();
        JFreeChart fc =
            ChartFactory.createXYLineChart(this.nom, "temps", "valeurs", ds, PlotOrientation.VERTICAL, true, true,
                                           false);
        cp = new ChartPanel(fc);
        this.add(cp);
    }

    private XYDataset createDataset() {
        DefaultXYDataset ds = new DefaultXYDataset();

        Iterator<Map.Entry<String, ChartData>> it = data.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, ChartData> entry = it.next();
            double[][] data = { entry.getValue().x, entry.getValue().y };
            ds.addSeries(entry.getKey(), data);
        }

        return ds;
    }
}
