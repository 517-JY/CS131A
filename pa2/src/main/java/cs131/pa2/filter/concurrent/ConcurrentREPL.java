package cs131.pa2.filter.concurrent;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import cs131.pa2.filter.Message;

/**
 * The main implementation of the REPL loop (read-eval-print loop).
 * It reads commands from the user, parses them, executes them and displays the result.
 * @author cs131a, Jiayin Li
 *
 */
public class ConcurrentREPL {
	/**
	 * the path of the current working directory
	 */
	static String currentWorkingDirectory;
	
	
	/**
	 * The main method that will execute the REPL loop
	 * @param args not used
	 */
	public static void main(String[] args){
		currentWorkingDirectory = System.getProperty("user.dir");
		Scanner s = new Scanner(System.in);
		System.out.print(Message.WELCOME);
		String command;
		
		Queue<ConcurrentFilter> filters = new LinkedList<ConcurrentFilter>();
		
		while(true) {
			//obtaining the command from the user
			System.out.print(Message.NEWCOMMAND);
			command = s.nextLine();
			if(command.equals("exit")) {
				break;
			} else if(!command.trim().equals("")) {
				//Builds the filters list from the command
				ConcurrentFilter filterlist = ConcurrentCommandBuilder.createFiltersFromCommand(command);
				
				//Collects all filters before starting any of them 
				while(filterlist != null) {
					// Adds all valid filters into a Queue container
					filters.add(filterlist);
					filterlist = (ConcurrentFilter) filterlist.getNext();
				}
				
								
				while(!filters.isEmpty())
				{
					// Generates thread that follows the FIFO principle
					ConcurrentFilter filter =  filters.poll();
					Thread t = new Thread(filter);
					
					try {
						// Makes a short break here to ensure that filter (sub-command) comes late also starts late
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
	                
					t.start();
					
					try {
						// Waits for all threads to finish before going to the next command request
						t.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}							
				}							
			}
		}
		
		s.close();
		System.out.print(Message.GOODBYE);
	}

}
