package mainBaseline;

import java.io.IOException;

import businessLogic.LoadingDataset;
import businessLogic.UseDecisionTree;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;


public class Main {

	public static void main(String[] args) throws IOException {

		Instances data = LoadingDataset.LoadDataset("C:\\Program Files\\Weka-3-6\\data\\iris.arff");
		J48 learnedTree = UseDecisionTree.C4dot5(data);
		 try {
			System.out.println(Evaluation.evaluateModel(learnedTree, data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
