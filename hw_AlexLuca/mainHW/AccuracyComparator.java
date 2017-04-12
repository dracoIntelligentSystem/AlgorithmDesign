package mainHW;

import java.util.Comparator;

import mainHW.GridPoint;
import mainHW.Metrics;


/**
 * A comparator that only takes accuracy into account.
 * When two elements have the same accuracy, the one
 * with the smaller confidence interval is preferred.
 *
 */
public class AccuracyComparator implements Comparator<GridPoint> {
	
	// Epsilon
	private double eps = 1e-16;
	
	/***
	 * Specify a custom epsilon for the comparison.
	 * @param eps If the difference in accuracy is below this value,
	 * the two GridPoints are compared by their confidence interval.
	 */
	public AccuracyComparator(double eps) {
		this.eps = eps;
	}

	public AccuracyComparator() {
	}
	
	@Override
	public int compare(GridPoint o1, GridPoint o2) {
//		Metrics m1, m2;
//		m1 = o1.getMetrics();
//		m2 = o2.getMetrics();
		if (Math.abs(o1.accuracy - o2.accuracy) < eps) {
			return  ((Float)o1.c).compareTo(o2.c);
		}
		else {
			return (int) Math.signum(o1.accuracy - o2.accuracy);
		}		
	}

}
