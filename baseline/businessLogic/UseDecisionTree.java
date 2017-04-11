package businessLogic;

import weka.classifiers.trees.J48;
import weka.core.Instances;

public class UseDecisionTree {

	static String[] options = new String[1];
	
	public UseDecisionTree() {
		// TODO Auto-generated constructor stub
	}
	
	public static J48 C4dot5(Instances data){
		
		 options[0] = "-U";            // unpruned tree
		 J48 tree = new J48();         // new instance of tree
		 try {
			tree.setOptions(options);
			 tree.buildClassifier(data);   // build classifier
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     // set the options
		return tree;
	}
}
