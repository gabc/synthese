package synthesejava;

import java.util.List;

public class ChartData {
    public double[] x;
    public double[] y;
    
    public ChartData(){
        x = new double[]{};
        y = new double[]{};
    }
    
    public void append(int tick, double n){
        double[] nx = new double[x.length+1];
        nx[x.length] = tick;
        double[] ny = new double[y.length+1];
        ny[y.length] = n;
        
        x = nx;
        y = ny;
    }
}
