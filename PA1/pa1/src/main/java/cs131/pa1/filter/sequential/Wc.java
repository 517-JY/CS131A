package cs131.pa1.filter.sequential;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.NoSuchFileException;
import java.util.LinkedList;

import cs131.pa1.filter.Filter;

// pending doc
// It reads from the input line by line, and 
// only returns lines that match the given pattern(keyword)

public class Wc extends SequentialFilter{
	
	public static String symbol = "wc";
	private static boolean hasPipedInput = true;
	private static boolean hasPipedOutput = true;
	
	private int lineCount = 0;
	private int wordCount = 0;
	private int charCount = 0;

	
	
	public static boolean getHasPipedInput() {
		return hasPipedInput;
	}

	
	public static boolean getHasPipedOutput() {
		return hasPipedOutput;
	}
	
	
	
	public Wc() {
		super();
		output = new LinkedList<String>();
		input = new LinkedList<String>(); 
	}
	
	
	public String getSymbol() {
		return symbol;
	}


	/**
	 * Processes the input queue and passes the result to the output queue
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 */
	public void process() throws NoSuchFileException, IOException{
		
		// Special case for an empty file
		if (input.isEmpty()) {
			output.add("0 0 0\n");
		}
		while (!input.isEmpty()){
			String line = input.poll();
			String processedLine = processLine(line);
			if (processedLine != null){
				output.add(processedLine);
			}
		}	
	}
	
	
	
	@Override
	// pending for doc and editing
	protected String processLine(String line) throws IOException {
		// Lines Count
		lineCount++;
		
		// Words Count
		String[] words = line.split("\\s+");
	    wordCount += words.length;
	    
	    
	    // Char Count
	    
	    // Convert a string as a InputStream object 
	    InputStream stream = new ByteArrayInputStream(line.getBytes(StandardCharsets.UTF_8));
	    
	    int data = stream.read();
	    
	    while(data != -1 ) {
	    	// Per the discussion forum, newline character should not be counted 
	    	if(data != 10) {
		    	charCount++;
	    	}

	    	data = stream.read();
	    }
		
		
		if(input == null || isDone()) {
			
			String result = lineCount + " " + wordCount + " "+ 
					charCount + "\n";
			
			return result;	
			
		}

		
		return null;
	}
	
	
}
