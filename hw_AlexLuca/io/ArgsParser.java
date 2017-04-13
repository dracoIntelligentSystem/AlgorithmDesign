package io;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

/**
 * Class for parsing the command line arguments.
 */
public class ArgsParser {
	private Instances data;
	private Path datasetPath;
	
	public Instances getData() {
		return data;
	}

	public ArgsParser(String[] args) throws FileNotFoundException, IllegalArgumentException {
		if (args.length == 0) {
			throw new IllegalArgumentException("Insert dataset path");
		}
		for (int i = 0; i < args.length; i++) {
			
			String s = args[i];
			switch (i) {
			case 0:
				datasetPath = Paths.get(s);
				loadFile(s);
		        data.setClassIndex(data.numAttributes() - 1);
				break;
			case 1:
				this.setClassIndex(s);
				break;
			default:
				throw new IllegalArgumentException("Wrong number of arguments.");
			}
			
		}
	}
	
	private void loadFile(String path) throws FileNotFoundException{
		DataSource source;
		try {
			source = new DataSource(path);
			this.data = source.getDataSet();
		} catch (Exception e) {
			throw new FileNotFoundException();
		}
	}
	
	private void setClassIndex(String index) throws IllegalArgumentException {
		try {
	        data.setClassIndex(Integer.parseInt(index));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid class index");
		} 
	}
	
	public String getFileName(){
		return datasetPath.getFileName().toString();
	}
	
}
