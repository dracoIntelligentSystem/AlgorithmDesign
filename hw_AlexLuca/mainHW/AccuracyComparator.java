package mainHW;

import java.util.Comparator;


public class AccuracyComparator implements Comparator<GridPoint> {
	
	// Epsilon
	private double eps = 1e-16;
	
	public AccuracyComparator(double eps) {
		this.eps = eps;
	}

	public AccuracyComparator() {
	}
	
	
	@Override
	public int compare(GridPoint o1, GridPoint o2) {
		Metrics m1, m2;
		m1 = o1.getMetrics();
		m2 = o2.getMetrics();
		if (Math.abs(m1.accuracy - m2.accuracy) < eps) {
			return 0;
		}
		else {
			return (int) Math.signum(m1.accuracy - m2.accuracy);
		}		
	}

}
