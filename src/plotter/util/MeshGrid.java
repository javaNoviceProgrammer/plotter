package plotter.util;

public class MeshGrid {

	int xDim, yDim ;
	double[] x, y ;

	public MeshGrid(double[] x, double[] y){
		this.x = x ;
		this.y = y ;
		this.xDim = x.length ;
		this.yDim = y.length ;
	}

	public double getX(int m, int n) {
		return x[n] ;
	}

	public double getY(int m, int n) {
		return y[m] ;
	}

	public int getXDim() {
		return xDim ;
	}

	public int getYDim() {
		return yDim ;
	}

	public int getRowDim() {
		return getYDim() ;
	}

	public int getColumnDim() {
		return getXDim() ;
	}

}
