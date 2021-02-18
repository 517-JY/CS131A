package cs131.pa1.filter.sequential;


import java.util.*;

import cs131.pa1.filter.Message;
import cs131.pa1.filter.Filters;

import java.io.*;
import java.nio.file.NoSuchFileException;
import java.text.MessageFormat;


/**
 * The main implementation of the REPL loop (read-eval-print loop).
 * It reads commands from the user, parses them, executes them and displays the result.
 * @author cs131a
 *
 */
public class SequentialREPL {
	/**
	 * the path of the current working directory
	 */
	static String currentWorkingDirectory;
	/**
	 * The main method that will execute the REPL loop
	 * @param args not used
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, NoSuchFileException {
		// Pending doc
		
		currentWorkingDirectory = System.getProperty("user.dir");
		
		printWelcomeMessage();		
		
		System.out.print(Message.NEWCOMMAND.with_parameter(Message.NEWCOMMAND.toString()));	
		
		Scanner console = new Scanner(System.in);	
		
		String nextline= "";
		
		if(console.hasNextLine()) {
			nextline = console.nextLine();
		}
		

		

		while(!nextline.equals("exit")) {
			

		
			//SequentialCommandBuilder scb = new SequentialCommandBuilder(nextline);
			

			
			SequentialCommandBuilder.createFiltersFromCommand(nextline);
			
			// Queue<String> testSubStrings = SequentialCommandBuilder.getSubStrings();
			

			
			
			// List<SequentialFilter> rawSequenceFilter = SequentialCommandBuilder.createFiltersFromCommand(nextline);	
			
			
			
			
			//Set<Filters> result = SequentialCommandBuilder.getFilterCollection();

			// Boolean success = SequentialCommandBuilder.linkFilters();
	
			
			System.out.print(Message.NEWCOMMAND.with_parameter(Message.NEWCOMMAND.toString()));
			

			
			nextline = console.nextLine();
		}
		


   		// Pending doc
		console.close();
   		printGoodByeMessage();
	}
	
	// Pending doc
	public static void printWelcomeMessage() {		
		System.out.print(Message.WELCOME.with_parameter(Message.WELCOME.toString()));	

	}
	
	
	// Pending doc
	public static void printGoodByeMessage() {	
		System.out.print(Message.GOODBYE.with_parameter(Message.GOODBYE.toString()));		
	}

}
