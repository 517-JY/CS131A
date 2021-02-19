package cs131.pa1.filter.sequential;

import java.util.LinkedList;

import cs131.pa1.filter.Filter;

// pending doc
// It reads from the input line by line, and 
// only returns lines that match the given pattern(keyword)

public class BiggerThan extends SequentialFilter{
	
	private static String symbol = ">";
	public static boolean hasPipedInput = true;
	public static boolean hasPipedOutput = false;
	
	
	
	public static boolean getHasPipedInput() {
		return hasPipedInput;
	}
	
	
	public static boolean getHasPipedOutput() {
		return hasPipedOutput;
	}

	
	public BiggerThan(String para) {
		super();
		this.para = para;
		output = new LinkedList<String>();
		input = new LinkedList<String>(); 
	}
	

	public String getSymbol() {
		return symbol;
	}
	

	
	// Parameter for this filter
	private String para;
	
	
	
	public String getParameter() {
		return this.para;
	}
	
	@Override
	public String toString() {
		return "> " + para;
	}
	
	
	
	
	
	@Override
	// pending for doc and editing
	protected String processLine(String line) {
		
		return line;
	}
	
	
}