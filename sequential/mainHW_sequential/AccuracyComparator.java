package mainHW_sequential;

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
		if (Math.abs(o1.accuracy - o2.accuracy) < eps) {
			return 0;
		}
		else {
			return (int) Math.signum(o1.accuracy - o2.accuracy);
		}		
	}

}
