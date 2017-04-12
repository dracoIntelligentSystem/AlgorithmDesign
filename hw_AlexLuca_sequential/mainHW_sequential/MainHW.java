package mainHW_sequential;
import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedQueue;

import mainHW_sequential.AccuracyComparator;
import mainHW_sequential.GridPoint;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class MainHW {

	public static void main(String[] args) {
//		System.out.println("Hello in HW Alex & Luca!!");
		DataSource source;
		ConcurrentLinkedQueue<GridPoint> grid, treeGrid;
		
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
			Integer count = 0;
			// Populate the grid
			grid = new ConcurrentLinkedQueue<GridPoint>();
			for (int m = minM ; m <= maxM; m += stepM) {
				for (double c = minC; c <= maxC; c += stepC) {
					grid.add(new GridPoint(m, c, data));
					count++;
				}
			}
			System.out.println(count.toString());
			//Evaluate
			treeGrid = new ConcurrentLinkedQueue<GridPoint>();
			while (!grid.isEmpty()) {
				GridPoint point = grid.poll();
				point.computeMetrics();
				treeGrid.add(point);
			}
			GridPoint max = Collections.max(treeGrid, new AccuracyComparator());
			
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
