package businessLogic;

import weka.classifiers.Classifier;
import weka.classifiers.meta.CVParameterSelection;
import weka.core.Instances;
import weka.core.Utils;

public class Hyperparam {

	public static void runOptimizer(Classifier classifier, Instances data, int folds) throws Exception{

		CVParameterSelection ps = new CVParameterSelection();
		ps.setClassifier(classifier);
		ps.setNumFolds(folds);  // using 5-fold CV
		ps.addCVParameter("C 0.1 0.5 50");
		ps.addCVParameter("M 1 10 10");

		// build and output best options
		ps.buildClassifier(data);
		
		System.out.println(Utils.joinOptions(ps.getBestClassifierOptions()));
	}
}
