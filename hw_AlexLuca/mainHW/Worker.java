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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long accuracy = (long) (point.accuracy * 1e6);
		if (maxVal.getAndUpdate(x -> x < accuracy ? accuracy : x) == accuracy) {
			maxPoint.set(point);
		}
	}

}
