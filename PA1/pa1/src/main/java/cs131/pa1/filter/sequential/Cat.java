package cs131.pa1.filter.sequential;

import cs131.pa1.filter.Filter;

import java.io.BufferedReader;
//import java.io.ByteArrayOutputStream;
import java.io.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

// pending doc
// It opens the file, reads the files line by line, and then passes the output to the next command.

public class Cat extends SequentialFilter{
	
	
	// pending doc
	private static String symbol = "cat";
	public static boolean hasPipedInput = false;
	public static boolean hasPipedOutput = true;
	
	
	
	public static boolean getHasPipedInput() {
		return hasPipedInput;
	}
	
	
	public static boolean getHasPipedOutput() {
		return hasPipedOutput;
	}
	
	
	
	
	// Parameter for this filter
	// private boolean hasPara = true;
	private String para;
	
	public Cat(String para) {
		super();
		this.para = para;
		output = new LinkedList<String>();
		input = new LinkedList<String>();
	}

	
	public String getParameter() {
		return this.para;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	

	

	@Override
	// pending for doc and editing
	protected String processLine(String line) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	// pending for doc and editing
	public void process() throws IOException, NoSuchFileException {

		
		String filePath = SequentialREPL.currentWorkingDirectory + 
				Filter.FILE_SEPARATOR + para;
		
		
		
		// Needs to read file line by line 
		
		BufferedReader reader = Files.newBufferedReader(Paths.get(filePath));
		
		Stream<String> tempStore = reader.lines();
		
		
		
		
		tempStore.forEach(line -> output.add(line + "\n"));
	
        
		 
		reader.close();
		
	}
	


	@Override
	public boolean isDone() {
		
		// pending for doc and editing
		return true;
	}
	
	
	@Override
	public String toString() {
		return "cat " + para;
	}
}
