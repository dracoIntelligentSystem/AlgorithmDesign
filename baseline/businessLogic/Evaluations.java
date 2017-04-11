package businessLogic;

import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

public class Evaluations {

	public static void evaluateModel(Classifier classifier, Instances data) throws Exception{
		Evaluation eval = new Evaluation(data);
		 eval.crossValidateModel(classifier, data, 10 , new Random(1));
	}
}
