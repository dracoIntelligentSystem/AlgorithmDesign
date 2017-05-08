package mainBaseline;

import java.io.IOException;

import businessLogic.Hyperparam;
import businessLogic.LoadingDataset;
import businessLogic.UseDecisionTree;
import weka.classifiers.trees.J48;
import weka.core.Instances;


public class Main {

	public static void main(String[] args) throws IOException {

		Instances data = LoadingDataset.LoadDataset(args[0]);
		J48 learnedTree = UseDecisionTree.C4dot5(data);
		 try {
			//Evaluations.evaluateModel(learnedTree, data);

			 Hyperparam.runOptimizer(learnedTree, data, 10);

			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
