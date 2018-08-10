package tutorial;

import java.awt.EventQueue;

import plotter.chart.ColorMapPlot;
import plotter.util.MeshGrid;
import plotter.util.MoreMath;
import plotter.util.ColorMap.ColorMapName;

public class Tutorial2 {
    public static void main(String[] args) {
    	double[] x = MoreMath.linspace(1.0, 10.0, 1000) ;
    	double[] y = MoreMath.linspace(10.0, 20.0, 1000) ;
    	MeshGrid mesh = new MeshGrid(x, y) ;
    	double[][] func = new double[y.length][x.length] ;
    	for(int i=0; i<y.length; i++){
    		for(int j=0; j<x.length; j++){
    			func[i][j] = 2*Math.sin(mesh.getX(i, j))*Math.sin(Math.PI*mesh.getY(i, j)) ;
    		}
    	}
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ColorMapPlot(mesh, func, ColorMapName.Rainbow).run(true);;
            }
        });
    }
}
