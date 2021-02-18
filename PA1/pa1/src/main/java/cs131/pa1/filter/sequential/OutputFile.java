package cs131.pa1.filter.sequential;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;

import cs131.pa1.filter.Filter;

// pending doc
// It reads from the input line by line, and 
// only returns lines that match the given pattern(keyword)

public class OutputFile extends SequentialFilter{
	
	private static String symbol;
	private static String para;
	public static boolean pipedInput = true;
	public static boolean pipedOutput = false;
	private String filePath;
	private BufferedWriter bw = null;
	private FileWriter fw = null;
	private File f = null;
	
	
	public OutputFile(String symbol, String para) {
		
		
		
		
		super();
		this.symbol = symbol;
		this.para = para;
		output = new LinkedList<String>();
		input = new LinkedList<String>();
		
		//System.out.println("whether a outputfile filter is created : ");
	}
	

	@Override
	public void process() throws FileNotFoundException, IOException{
		
		
		
		filePath = SequentialREPL.currentWorkingDirectory + 
				Filter.FILE_SEPARATOR + para;
		
		// System.out.println("File path is " + filePath);
		
		f = new File(filePath); 
		
		if(!f.exists()) {
			//System.out.println("Wehter needs to be created : Yes ");
			f.createNewFile();
		}

		
		fw = new FileWriter(f);
		bw = new BufferedWriter(fw);
		

		
		//System.out.println("at this stage, the input for outputFile filter is " + input);
				
		
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
		
		// Needs to pay attention to close the bw
		bw.close();

	}
	



	@Override
	// pending for doc and editing
	protected String processLine(String line) throws IOException {
		
		bw.write(line);

		
		return null;
	}
	

	
	

	
}
