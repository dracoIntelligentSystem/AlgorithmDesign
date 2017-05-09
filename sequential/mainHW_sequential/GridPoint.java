package mainHW_sequential;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;

public class GridPoint {
	public int m;
	public double c;
	public double accuracy;
	private Instances data;
	
	public GridPoint(int m, double c, Instances data){
		this.m = m;
		this.c = c;
		this.data = data;
        data.setClassIndex(data.numAttributes() - 1);
	}

	public void computeMetrics() throws Exception {
		Evaluation ev = new Evaluation(this.data);
		ev.crossValidateModel(buildTree(), data, 10, new Random(1));
		this.accuracy = ev.correct()/(ev.correct() + ev.incorrect());
	}
	

	private J48 buildTree(){
		J48 tree = new J48();
		tree.setUnpruned(false);
		tree.setConfidenceFactor((float) c);
		tree.setMinNumObj(m);
		return tree;
	}
	
}
