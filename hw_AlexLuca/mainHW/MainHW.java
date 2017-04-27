package mainHW;

import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import io.ArgsParser;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

/**
 * Parallelized grid search for C4.5
 *
 */
public class MainHW {

	/**
	 * Parallelized grid search for C4.5 trees.
	 * <p>
	 * Find the optimal parameters for 
	 * <code>MinNumObj</code> and <code>ConfidenceFactor</code>
	 * (respectively, the minimum number of instances per leaf
	 * and the confidence threshold for pruning). The accuracy
	 * is computed via 10-fold cross-validation.
	 * </p>
	 * @param args 
	 * <ul>
	 * <li>args[0] = path to .arff file </li> 
	 * <li>args[1] = index of the class parameter (optional:
	 * if omitted, will default to the last column)</li>
	 * </ul>
	 */
	public static void main(String[] args) {
		
		int minM = 1;
		int maxM = 10;
		double stepM = 1; // 10 steps
		
		float minC = 0.1f;
		float maxC = 0.5f;
		float stepC = 0.01f; // 50 steps
		ArgsParser parser = null;
		try {
			parser = new ArgsParser(args);

		} catch (Exception e) {
			System.out.println(e.toString());
			System.exit(1);
		}
		
		Instances data = parser.getData();

			Long start = System.currentTimeMillis();
			ExecutorService executor = Executors.newFixedThreadPool(16); 			
			
			GridPoint p0 = new GridPoint(minM, minC, data);
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
			try {
				executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Long elapsed = System.currentTimeMillis() - start;
			
			// Retrieve max value
			p0 = maxPoint.get();
			
			System.out.println(
					String.format(
							Locale.ROOT,
							"%s,%f,%f,%f",
							p0.m, p0.c, p0.accuracy, (Double)(elapsed/1000.0))
					);
	}
}
