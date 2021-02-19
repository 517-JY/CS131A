package cs131.pa1.filter.sequential;

import java.util.LinkedList;
import java.util.*;

import cs131.pa1.filter.Filter;

// pending doc
// It reads from the input line by line, and 
// only returns lines that match the given pattern(keyword)

public class Grep extends SequentialFilter{

	
	
	public static String symbol = "grep";
	private static boolean hasPipedInput = true;
	private static boolean hasPipedOutput = true;
	
	public static boolean getHasPipedInput() {
		return hasPipedInput;
	}
	
	
	public static boolean getHasPipedOutput() {
		return hasPipedOutput;
	}
	
	
	// Parameter for this filter
	private String para;
	
	
	public Grep(String para) {
		super();
		this.para = para;
		output = new LinkedList<String>();
		input = new LinkedList<String>(); 
	}
	
	public String getSymbol() {
		return symbol;
	}

	
	public String getParameter() {
		return this.para;
	}

	@Override
	// pending for doc and editing
	protected String processLine(String line) {
		
		//System.out.println("Whether processline of grep is executted ");
		boolean check = line.contains(para);
		
		if(check) {
			//System.out.println("Whether this line is popoed up : Yes " + line);
			return line;
		}
		
		
		return null;
	}
	
	
	@Override
	public String toString() {
		return "grep " + para;
	}
}
