package cs131.pa1.filter.sequential;

import java.util.LinkedList;

import cs131.pa1.filter.Filter;

// pending doc
// It reads from the input line by line, and 
// only returns lines that match the given pattern(keyword)

public class Uniq extends SequentialFilter{
	
	public static String symbol = "uniq";
	public static boolean hasPipedInput = true;
	public static boolean hasPipedOutput = true;


	
	public static boolean getHasPipedInput() {
		return hasPipedInput;
	}
	
	
	public static boolean getHasPipedOutput() {
		return hasPipedOutput;
	}
	
	

	
	public Uniq() {
		super();
		output = new LinkedList<String>();
		input = new LinkedList<String>(); 
	}
	
	public String getSymbol() {
		return symbol;
	}

	


	
	
	@Override
	// pending for doc and editing
	protected String processLine(String line) {
		
		if(!output.contains(line)) {
			return line;
		}

		return null;
	}
}
