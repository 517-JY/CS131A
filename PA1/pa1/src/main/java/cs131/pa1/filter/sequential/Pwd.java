package cs131.pa1.filter.sequential;

import java.util.LinkedList;

import cs131.pa1.filter.Filter;

public class Pwd extends SequentialFilter{
	
	public static String symbol = "pwd";
	public static boolean hasPipedInput = false;
	public static boolean hasPipedOutput = true;

	
	
	public Pwd() {
		super();
		output = new LinkedList<String>();
		input = new LinkedList<String>(); 
	}
	
	
	public static boolean getHasPipedInput() {
		return hasPipedInput;
	}
	
	
	public static boolean getHasPipedOutput() {
		return hasPipedOutput;
	}
	

	@Override
	// pending for doc and editing
	protected String processLine(String line) {	
		output.add(line + "\n");
		return line;

	}
	
	@Override
	// pending for doc and editing
	public void process(){
			processLine(SequentialREPL.currentWorkingDirectory);
	}
	
	
	@Override
	public boolean isDone() {
		
		// pending for doc and editing
		return true;
	}
	
	
	@Override
	public String toString() {
		return "pwd";
	}
	
}
