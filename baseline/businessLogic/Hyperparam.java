package businessLogic;

import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.CVParameterSelection;
import weka.core.Instances;

public class Hyperparam {
	
	

	public static void runOptimizer(Classifier classifier, Instances data, int folds) throws Exception{

		CVParameterSelection ps = new CVParameterSelection();
		ps.setClassifier(classifier);
		ps.setNumFolds(folds);  // using 5-fold CV
		ps.addCVParameter("C 0.1 0.5 50");
		ps.addCVParameter("M 1 10 10");

		// build and output best options
		ps.buildClassifier(data);
		 
		
		Evaluation eval = new Evaluation(data);
		eval.crossValidateModel(ps.getClassifier(), data, folds , new Random(1));
		System.out.println(//Utils.joinOptions(ps.getBestClassifierOptions()) +" \n"+ 
				ps.getBestClassifierOptions()[1]+","+
				ps.getBestClassifierOptions()[3]+","+
				eval.pctCorrect()/100.0
				//ps.getClassifier().toString()
				//Utils.joinOptions(ps.getClassifier().getOptions())
		);
	} //Utils.joinOptions(ps.getOptions())
}
