package mainHW;
import java.util.Random;

//import mainHW.Metrics;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;

public class GridPoint {
	public int m;
	public float c;
	private Instances data;
	public double accuracy;
	
	//private Metrics metrics = null;
	
	public GridPoint(int m, float c, Instances data){
		this.m = m;
		this.c = c;
		this.data = data;
	}

	public void computeMetrics() throws Exception {
		Evaluation ev = new Evaluation(this.data);
	
		ev.crossValidateModel(buildTree(), data, 10, new Random(1));
		
		//this.metrics = new Metrics();
		this.accuracy = ev.correct()/(ev.correct() + ev.incorrect());
	}
	
//	public Metrics getMetrics() {
//		return metrics;
//	}

	private J48 buildTree(){
		J48 tree = new J48();
		tree.setConfidenceFactor(c);
		tree.setMinNumObj(m);
		return tree;
	}
	
}
