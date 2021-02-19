package cs131.pa1.filter.sequential;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sun.tools.javac.util.List;

import cs131.pa1.filter.Filter;

public class Ls extends SequentialFilter{
	
	public static String symbol = "ls";
	public static boolean hasPipedInput = false;
	public static boolean hasPipedOutput = true;
	private Queue<String> filesAndDirectories = new LinkedList<String>();
	//private Queue<String> filesAndDirectories = new LinkedList<String>();
	
	public Ls() {
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
		return line;
	}
	
	@Override
	// pending for doc and editing
	public void process() throws IOException{
		
		
		File directory = new File(SequentialREPL.currentWorkingDirectory);
		LinkedList<File> rawResultFiels = new LinkedList<File>();
		
		File[] files = directory.listFiles();
		rawResultFiels.addAll(Arrays.asList(files)); 
		
		for(File file : files) {
			if (file.isFile()) {
				filesAndDirectories.add(file.getName() + "\n");
                
            } else if (file.isDirectory()) {
            	filesAndDirectories.add(file.getName());
            }
		}
		
		
		
		
		
//		
//	    Stream<Path> currPath = Files.walk(Paths.get(SequentialREPL.currentWorkingDirectory));
//		
//		
//		java.util.List<String> files = currPath.filter(Files::isRegularFile).map(x->x.toString()).collect(Collectors.toList());
//		
////		currPath = Files.walk(Paths.get(SequentialREPL.currentWorkingDirectory));
////		java.util.List<String> directories =  currPath.filter(Files::isDirectory).map(x->x.toString()).collect(Collectors.toList());
//		
//		for(int i = 0; i< files.size();i++) {
//			filesAndDirectories.add(files.get(i));
//		}
////		for(int i = 0; i< directories.size();i++) {
////			filesAndDirectories.add(directories.get(i));
////		}
		
		while(!isDone()) {
			String line = filesAndDirectories.poll();
			String processedLine = processLine(line);
			if (processedLine != null){
				output.add(processedLine);
			}
		}
	
		
		

	}
	

	
	@Override
	public boolean isDone() {
		
		return filesAndDirectories.size() == 0;
	}
	
	
	@Override
	public String toString() {
		return "ls";
	}
}
