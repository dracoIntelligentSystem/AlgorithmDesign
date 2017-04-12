package mainHW;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class MainHW {

	public static void main(String[] args) {
		DataSource source;
		
		int minM = 1;
		int maxM = 10;
		double stepM = 1; // 10 steps
		
		float minC = (float)0.1;
		double maxC = 0.5;
		double stepC = 0.008; // 50 steps
				
		try {
			source = new DataSource(args[0]);
			Instances data = source.getDataSet();
			try {
		        data.setClassIndex(Integer.parseInt(args[1]));
			} catch (ArrayIndexOutOfBoundsException e) {
		        data.setClassIndex(data.numAttributes() - 1);
			} catch (NumberFormatException e) {
				System.out.println("Incorrect value.");
				System.exit(1);
			} catch (IllegalArgumentException e) {
				System.out.println("Incorrect value.");
				System.exit(1);
			}
			
			Long start = System.currentTimeMillis();
			ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()); 			
			
			GridPoint p0 = new GridPoint(minM, minC, data);
			p0.computeMetrics();
			AtomicReference<GridPoint> maxPoint = new AtomicReference<GridPoint>(p0);
			AtomicLong maxVal = new AtomicLong();

			for (int m = minM ; m <= maxM; m += stepM) {
				for (float c = minC; c <= maxC; c += stepC) {
					Worker w = new Worker(new GridPoint(m, c, data), maxPoint, maxVal);
					executor.execute(w);
				}
			}
			// Wait until the grid has been explored
			executor.shutdown();
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			Long elapsed = System.currentTimeMillis() - start;
			
			p0 = maxPoint.get();
			System.out.println(p0.m);
			System.out.println(p0.c);
			System.out.println(((Double)(elapsed/1000.0)).toString());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
