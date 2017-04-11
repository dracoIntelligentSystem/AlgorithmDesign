package businessLogic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import weka.core.Instances;

public class LoadingDataset {
	
	public static Instances LoadDataset(String dataset) {
		BufferedReader reader;
		Instances data = null;
		try {
			reader = new BufferedReader(
					new FileReader(dataset));
			data = new Instances(reader);
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// setting class attribute
		data.setClassIndex(data.numAttributes() - 1);
		return data;
	}
}
