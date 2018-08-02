package tutorial;

import static java.lang.Math.PI;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

import plotter.chart.MatlabChart;
import plotter.util.MoreMath;

public class Tutorial1 {
	public static void main(String[] args) throws UnsupportedLookAndFeelException {
		// 0. setting the UI look and feel
		UIManager.setLookAndFeel(new WindowsLookAndFeel());
		//1. define the x and y variables
		double[] x = MoreMath.linspace(-3*PI, 3*PI, 1000) ;
		double[] y = MoreMath.Arrays.Functions.sin(x) ;
		//2. create the matlab chart using jfreechart
		MatlabChart fig = new MatlabChart() ;
		//3. add as many variables to the figure
		fig.plot(x, y);
		//4. after adding the plots, render the chart
		fig.RenderPlot();
		//5. set the x and y lables
		fig.xlabel("X variable");
		fig.ylabel("Y variable");
		//6. show the plot
		fig.run(true);
	}
}
