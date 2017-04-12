package mainHW_sequential;

import java.util.concurrent.ConcurrentLinkedQueue;

import mainHW.GridPoint;

public class WorkerThread implements Runnable {

	private ConcurrentLinkedQueue<GridPoint> inputQueue, outputQueue;
	
	public WorkerThread(ConcurrentLinkedQueue<GridPoint> in,
			ConcurrentLinkedQueue<GridPoint> out) {
		this.inputQueue = in;
		this.outputQueue = out;
	}
	
	@Override
	public void run() {
		GridPoint point = inputQueue.poll();
		try {
			point.computeMetrics();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		outputQueue.add(point);
	}

}
