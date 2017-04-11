package mainHW;

public class GridPoint {
	public double m;
	public double c;
	private Metrics metrics = null;
	
	public GridPoint(double m, double c){
		this.m = m;
		this.c = c;
	}

	public void computeMetrics() {
		this.metrics = new Metrics();
	}
	public Metrics getMetrics() {
		return metrics;
	}

}
