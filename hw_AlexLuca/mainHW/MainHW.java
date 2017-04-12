package mainHW;
import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedQueue;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class MainHW {

	public static void main(String[] args) {
		System.out.println("Hello in HW Alex & Luca!!");
		DataSource source;
		ConcurrentLinkedQueue<GridPoint> grid, treeGrid;
		
		int minM = 1;
		int maxM = 10;
		double stepM = 1; 
		double minC = 0.2;
		double maxC = 2;
		double stepC = 0.1;
				
		try {
			source = new DataSource(args[0]);
			Instances data = source.getDataSet();
			
			// Populate the grid
			grid = new ConcurrentLinkedQueue<GridPoint>();
			for (int m = minM ; m <= maxM; m += stepM) {
				for (double c = minC; c <= maxC; c += stepC) {
					grid.add(new GridPoint(m, c, data));
				}
			}
			
			//Evaluate
			treeGrid = new ConcurrentLinkedQueue<GridPoint>();
			while (!grid.isEmpty()) {
				GridPoint point = grid.poll();
				point.computeMetrics();
				treeGrid.add(point);
			}
			
			GridPoint max = Collections.max(treeGrid, new AccuracyComparator());
			System.out.println("max: M = " + String.valueOf(max.m) + ", c = " + String.valueOf(max.c));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
