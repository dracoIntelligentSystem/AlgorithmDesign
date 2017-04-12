package mainHW;

import java.util.concurrent.ConcurrentLinkedQueue;

import mainHW.GridPoint;

public class WorkerThread implements Runnable {

	private GridPoint point;
	private ConcurrentLinkedQueue<GridPoint> outputQueue;
	
	public WorkerThread(GridPoint point,
			ConcurrentLinkedQueue<GridPoint> out) {
		this.point = point;
		this.outputQueue = out;
	}
	
	@Override
	public void run() {
		try {
			point.computeMetrics();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		outputQueue.add(point);
	}

}
