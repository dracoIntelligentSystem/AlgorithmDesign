package mainBaseline;

import java.io.IOException;

import businessLogic.Evaluations;
import businessLogic.LoadingDataset;
import businessLogic.UseDecisionTree;
import weka.classifiers.trees.J48;
import weka.core.Instances;


public class Main {

	public static void main(String[] args) throws IOException {

		Instances data = LoadingDataset.LoadDataset(args[0]);
		J48 learnedTree = UseDecisionTree.C4dot5(data);
		 try {
			Evaluations.evaluateModel(learnedTree, data);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
