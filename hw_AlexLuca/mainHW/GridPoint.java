package mainHW;
import java.util.Random;

//import mainHW.Metrics;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;


/**
 * Represents a point in the search space.
 * <p>
 * Also contain the logic to instantiate/evaluate
 * a C4.5 tree at the given coordinates, with the
 * given test set.
 * </p>
 */
public class GridPoint {
	public int m;
	public float c;
	private Instances data;
	public double accuracy;
		
	/**
	 * Creates a new GridPoint.
	 * @param m Minimum number of instances per leaf.
	 * @param c Confidence threshold for pruning.
	 * @param data Test set to evaluate the tree. 
	 */
	public GridPoint(int m, float c, Instances data){
		this.m = m;
		this.c = c;
		this.data = data;
	}

	public void computeMetrics() throws Exception {
		Evaluation ev = new Evaluation(this.data);
		ev.crossValidateModel(buildTree(), data, 10, new Random(1));
		this.accuracy = ev.correct()/(ev.correct() + ev.incorrect());
	}

	/**
	 * Builds a C4.5 tree for the current coordinates.
	 * 
	 * <p>
	 * <code>J48</code> is the Weka implementation of the
	 * C4.5 algorithm.
	 * </p>
	 * @return A tree whose parameters correspond to the
	 * coordinates of <code>this</code>.
	 */
	private J48 buildTree(){
		J48 tree = new J48();
		tree.setConfidenceFactor(c);
		tree.setMinNumObj(m);
		return tree;
	}
	
}
