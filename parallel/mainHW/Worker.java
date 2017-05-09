package mainHW;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class Worker implements Runnable {

	private GridPoint point;
	private AtomicLong maxVal;
	private AtomicReference<GridPoint> maxPoint;
	
	public Worker(GridPoint point,
			AtomicReference<GridPoint> maxPoint,
			AtomicLong maxVal) {
		this.point = point;
		this.maxPoint = maxPoint;
		this.maxVal = maxVal;
	}
	
	@Override
	public void run() {
		try {
			point.computeMetrics();
			// accuracy is between 0 and 1, however we 
			// want to store it as a long. Therefore we
			// multiply & cast.
			long accuracy = (long) (point.accuracy * 1e6);
			// old is the value of maxVal *before* the update
			long old = maxVal.getAndUpdate(x -> x < accuracy ? accuracy : x);
			if (old < accuracy) {
				maxPoint.set(point);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
