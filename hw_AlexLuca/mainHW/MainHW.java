package mainHW;

import java.util.concurrent.ConcurrentLinkedQueue;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;


public class MainHW {

	public static void main(String[] args) {
		System.out.println("Hello in HW Alex & Luca!!");
		DataSource source;
		ConcurrentLinkedQueue<GridPoint> grid;
		ConcurrentLinkedQueue<EvaluationResults> results;
		
		int minM = 0;
		int maxM = 2;
		double step = 0.2; 
		int minC = 0;
		int maxC = 2;
		
		try {
			source = new DataSource("/Users/luca/wekafiles/data/iris.arff");
			Instances data = source.getDataSet();
			
			// Populate the grid
			grid = new ConcurrentLinkedQueue<GridPoint>();
			for (double m = minM ; m <= maxM; m += step) {
				for (double c = minC; c <= maxC; c += step) {
					grid.add(new GridPoint(m, c));
				}
			}
			
			//Evaluate
			results = new ConcurrentLinkedQueue<EvaluationResults>();
			while (!grid.isEmpty()) {
				GridPoint point = grid.poll();
				results.add(evaluateParams(point, data));
			}
			
			while (!results.isEmpty()) {
				EvaluationResults r = results.poll();
				System.out.println(r.metrics.accuracy);
				
			}
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static EvaluationResults evaluateParams(GridPoint point, Instances data) {
		// Stub
		EvaluationResults result = new EvaluationResults();
		result.point = point;
		result.metrics = new Metrics();
		result.metrics.accuracy = point.m * point.c;
		
		return result;
		
	}

}
