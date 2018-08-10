package plotter.chart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Paint;

import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.PaintScale;
import org.jfree.chart.renderer.xy.XYBlockRenderer;
import org.jfree.chart.title.PaintScaleLegend;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYZDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;

import plotter.util.MeshGrid;
import plotter.util.MoreMath;

public class ColorMapPlot {

	JFreeChart chart ;
	MeshGrid mesh ;
	double[][] func ;
	float dx, dy ;

    public ColorMapPlot(MeshGrid mesh, double[][] func) {
    	this.mesh = mesh ;
    	this.func = func ;
        chart = createChart(createDataset()) ;
    }

    public void run(boolean systemExit){
        JFrame f = new JFrame();
        if(systemExit)
        	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        else
        	f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        ChartPanel chartPanel = new ChartPanel(chart) {

			private static final long serialVersionUID = -8231653378192714530L;

			@Override
            public Dimension getPreferredSize() {
                return new Dimension(640, 480);
            }
        };
        chartPanel.setMouseZoomable(true, false);
        f.add(chartPanel);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    private JFreeChart createChart(XYDataset dataset) {
        NumberAxis xAxis = new NumberAxis("x Axis");
        NumberAxis yAxis = new NumberAxis("y Axis");
        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, null);
        XYBlockRenderer r = new XYBlockRenderer();
        double[] range = getFuncMinMax() ;
        SpectrumPaintScale ps = new SpectrumPaintScale(range[0], range[1]);
        r.setPaintScale(ps);
        r.setBlockHeight(dy);
        r.setBlockWidth(dx);
        plot.setRenderer(r);
        JFreeChart chart = new JFreeChart("",
            JFreeChart.DEFAULT_TITLE_FONT, plot, false);
        NumberAxis scaleAxis = new NumberAxis("Scale");
        scaleAxis.setAxisLinePaint(Color.white);
        scaleAxis.setTickMarkPaint(Color.white);
        PaintScaleLegend legend = new PaintScaleLegend(ps, scaleAxis);
        legend.setSubdivisionCount(128);
        legend.setAxisLocation(AxisLocation.TOP_OR_RIGHT);
        legend.setPadding(new RectangleInsets(10, 10, 10, 10));
        legend.setStripWidth(20);
        legend.setPosition(RectangleEdge.RIGHT);
        legend.setBackgroundPaint(Color.WHITE);
        chart.addSubtitle(legend);
        chart.setBackgroundPaint(Color.white);
        return chart;
    }

    private XYZDataset createDataset() {
    	dx = (float) (mesh.getX(0, 1) - mesh.getX(0, 0)) ;
    	dy = (float) (mesh.getY(1, 0) - mesh.getY(0, 0)) ;
        DefaultXYZDataset dataset = new DefaultXYZDataset();
        for (int i = 0; i < mesh.getYDim(); i++) {
            double[][] data = new double[3][mesh.getXDim()];
            for (int j = 0; j < mesh.getXDim(); j++) {
                data[0][j] = mesh.getX(i, j);
                data[1][j] = mesh.getY(i, j);
                data[2][j] = func[i][j];
            }
            dataset.addSeries("Series" + i, data);
        }
        return dataset;
    }

    private double[] getFuncMinMax(){
    	int rows = func.length ;
    	int columns = func[0].length ;
    	double min = Double.MAX_VALUE ;
    	double max = Double.MIN_VALUE ;
    	for(int i=0; i<rows; i++) {
    		for(int j=0; j<columns; j++){
    			if(min>func[i][j]) min = func[i][j] ;
    			if(max<func[i][j]) max = func[i][j] ;
    		}
    	}
    	return new double[] {min, max} ;
    }

    private static class SpectrumPaintScale implements PaintScale {

        private static final float H1 = 0f;
        private static final float H2 = 1f;
        private final double lowerBound;
        private final double upperBound;

        public SpectrumPaintScale(double lowerBound, double upperBound) {
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
        }

        @Override
        public double getLowerBound() {
            return lowerBound;
        }

        @Override
        public double getUpperBound() {
            return upperBound;
        }

        @Override
        public Paint getPaint(double value) {
            float scaledValue = (float) (value / (getUpperBound() - getLowerBound()));
            float scaledH = H1 + scaledValue * (H2 - H1);
            return Color.getHSBColor(scaledH, 1f, 1f);
        }
    }


    // for test
    public static void main(String[] args) {
    	double[] x = MoreMath.linspace(-10.0, 10.0, 1000) ;
    	double[] y = MoreMath.linspace(-10.0, 10.0, 1000) ;
    	MeshGrid mesh = new MeshGrid(x, y) ;
    	double[][] func = new double[y.length][x.length] ;
    	for(int i=0; i<y.length; i++){
    		for(int j=0; j<x.length; j++){
    			func[i][j] = 2*Math.sin(mesh.getX(i, j))*Math.sin(mesh.getY(i, j)) ;
    		}
    	}
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ColorMapPlot(mesh, func).run(true);;
            }
        });
    }
}
