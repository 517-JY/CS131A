package cs131.pa1.filter.sequential;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import cs131.pa1.filter.Filter;

// pending doc
// It reads from the input line by line, and 
// only returns lines that match the given pattern(keyword)

public class OutputScreen extends SequentialFilter{
	
	private static String symbol;
	public static boolean pipedInput = true;
	public static boolean pipedOutput = false;
	
	

	@Override
	public void process() throws FileNotFoundException, IOException{
		
		if(input!=null) {
			while (!input.isEmpty()){
				String line = input.poll();
				String processedLine = processLine(line);
				if (processedLine != null){
					if (output!=null ) {
						output.add(processedLine);
					}
				}
			}	
		}
	}
	

	@Override
	// pending for doc and editing
	protected String processLine(String line) {
		System.out.print(line);
		line = "";
		//System.out.println("Whether this has been printed ");
		return line;
	}
	
	
	public OutputScreen(String symbol) {
		this.symbol = symbol;
	}
	
}
