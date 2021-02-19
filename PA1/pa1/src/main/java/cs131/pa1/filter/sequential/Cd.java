package cs131.pa1.filter.sequential;

import cs131.pa1.filter.Filter;

public class Cd extends SequentialFilter{
	
	public static String symbol = "cd";
	public static boolean hasPipedInput = false;
	public static boolean hasPipedOutput = false;
	
	public static boolean getHasPipedInput() {
		return hasPipedInput;
	}
	
	
	public static boolean getHasPipedOutput() {
		return hasPipedOutput;
	}
	
	

	@Override
	// pending for doc and editing
	protected String processLine(String line) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	// pending for doc and editing
	public void process(){

	}
	
	
	@Override
	public boolean isDone() {
		
		// pending for doc and editing
		return true;
	}
	
	
}