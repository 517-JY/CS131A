package cs131.pa1.filter.sequential;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;

public class Cd extends SequentialFilter{
	
	public static String symbol = "cd";
	public static boolean hasPipedInput = false;
	public static boolean hasPipedOutput = false;
	private static String currFilePath = SequentialREPL.currentWorkingDirectory;
	
	
	private String para;
	
	
	
	public Cd(String para) {
		super();
		this.para = para;
		output = new LinkedList<String>();
		input = new LinkedList<String>();
	}

	
	private boolean checkDirectoryExist(String parameter) {
		if (parameter !=".") {
			String potentialPathStr = SequentialREPL.currentWorkingDirectory + 
					Filter.FILE_SEPARATOR + parameter;
			
			Path potentialPath = Paths.get(potentialPathStr);
			
			
			
			
			return  Files.isDirectory(potentialPath);
			
		}
		return true;
	}
	
	
	public String getParameter() {
		return this.para;
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
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	// pending for doc and editing
	public void process(){
		
		if(checkDirectoryExist(para)) {
			if(para.equals("..")) {
				SequentialREPL.currentWorkingDirectory = SequentialREPL.currentWorkingDirectory.substring(0, 
						SequentialREPL.currentWorkingDirectory.lastIndexOf(Filter.FILE_SEPARATOR));
			} else if (para.equals(".")){
				;
			} else {
				SequentialREPL.currentWorkingDirectory = SequentialREPL.currentWorkingDirectory 
						+ Filter.FILE_SEPARATOR + para;
			}
		} else {
			System.out.print(Message.DIRECTORY_NOT_FOUND.with_parameter(symbol + " " + para));	
		}
		
	}
	
	
	@Override
	public boolean isDone() {
		
		// pending for doc and editing
		return true;
	}
	
	@Override
	public String toString() {
		return "cd " + para;
	}
	
}