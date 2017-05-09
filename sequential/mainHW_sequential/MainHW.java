package mainHW_sequential;

import java.util.Collections;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;

import io.ArgsParser;
import weka.core.Instances;

public class MainHW {

	public static void main(String[] args) throws Exception {
		ConcurrentLinkedQueue<GridPoint> grid, treeGrid;

		ArgsParser parser = null;
		try {
			parser = new ArgsParser(args);

		} catch (Exception e) {
			System.out.println(e.toString());
			System.exit(1);
		}

		Instances data = parser.getData();
		int minM = parser.minM;
		int maxM = parser.maxM;
		double stepM = parser.stepM;

		float minC = parser.minC;
		float maxC = parser.maxC;
		float stepC = parser.stepC;

		Integer count = 0;
		// Populate the grid
		grid = new ConcurrentLinkedQueue<GridPoint>();
		for (int m = minM; m <= maxM; m += stepM) {
			for (double c = minC; c <= maxC; c += stepC) {
				grid.add(new GridPoint(m, c, data));
				count++;
			}
		}
		// Evaluate
		treeGrid = new ConcurrentLinkedQueue<GridPoint>();
		while (!grid.isEmpty()) {
			GridPoint point = grid.poll();
			point.computeMetrics();
			treeGrid.add(point);
		}
		GridPoint max = Collections.max(treeGrid, new AccuracyComparator());
		System.out.println(String.format(Locale.ROOT, "%s,%f,%f", max.m, max.c, max.accuracy));

	}
}
