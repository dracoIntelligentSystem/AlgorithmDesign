package mainHW;
import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class MainHW {

	public static void main(String[] args) {
//		System.out.println("Hello in HW Alex & Luca!!");
		DataSource source;
		ConcurrentLinkedQueue<GridPoint> grid;
		
		int minM = 1;
		int maxM = 10;
		double stepM = 1; // 10 steps
		
		double minC = 0.1;
		double maxC = 0.5;
		double stepC = 0.008; // 50 steps
				
		try {
			source = new DataSource(args[0]);
			Instances data = source.getDataSet();
			
			Long start = System.currentTimeMillis();
			// Populate the grid
			ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()); 
			
			grid = new ConcurrentLinkedQueue<GridPoint>();
			for (int m = minM ; m <= maxM; m += stepM) {
				for (double c = minC; c <= maxC; c += stepC) {
					WorkerThread w = new WorkerThread(new GridPoint(m, c, data), grid);
					executor.execute(w);
				}
			}
			// Wait until the grid has been explored
			executor.shutdown();
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			
			GridPoint max = Collections.max(grid, new AccuracyComparator());
			
			Long elapsed = System.currentTimeMillis() - start;
			System.out.println(((Double)(elapsed/1000.0)).toString());
			
//			System.out.println(
//					"max: M = " + String.valueOf(max.m) + 
//					", c = " + String.valueOf(max.c) +
//					"accuracy = " + String.valueOf(max.getMetrics().accuracy));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
