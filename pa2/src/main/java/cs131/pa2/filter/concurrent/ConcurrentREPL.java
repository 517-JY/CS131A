package cs131.pa2.filter.concurrent;

import java.util.LinkedList;
<<<<<<< HEAD
=======
import java.util.Objects;
>>>>>>> 31e863f19c3ce78fe1ea3dfb52450001c9d10a7e
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Predicate;

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
<<<<<<< HEAD
		
		Queue<ConcurrentFilter> filters = new LinkedList<ConcurrentFilter>();
		
=======

		Queue<ConcurrentFilter> filters = new LinkedList<ConcurrentFilter>();
		
		// Stores the number of background command
		int BGCommandCount = 0;
		// Stores all the running BackgroundCommands
		LinkedList<String> validBackgroundCommands = new LinkedList<String>();
				
>>>>>>> 31e863f19c3ce78fe1ea3dfb52450001c9d10a7e
		while(true) {
			// Indicates whether the command should be executed background
			boolean isBGCommand = false;
			//obtaining the command from the user
			System.out.print(Message.NEWCOMMAND);
			command = s.nextLine();
			LinkedList<Thread> threads = new LinkedList<Thread>();
			
			if(command.equals("exit")) {
				break;
<<<<<<< HEAD
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
=======
			} else if(!command.trim().equals("")) {				
				//Marks command following "&" symbol as background command
				if(command.substring(command.length() - 1).equals("&")) {
					isBGCommand = true;
					BGCommandCount++;
					validBackgroundCommands.add(BGCommandCount + ". " + command);
				}	
				
				//System.out.println("Current validBackgroundCommands status " + validBackgroundCommands);
				
				//Non-filter single command -- 'repl_jobs'
				if(command.equals("repl_jobs")) {
					int size = validBackgroundCommands.size();
					//System.out.println("Current size of validBackgroundCommands is " + size);
					for(int i = 0; i < size; i++) {
						System.out.printf("\t" + validBackgroundCommands.get(i) + "\n");
					}
				
				} else if(command.indexOf("kill") == 0) { 
					// Non-filter single command -- 'kill'
					// A valid kill command should has integer type parameter that is not greater than BGCommandCount
					String[] kills = command.split("\\s+");
					if(kills.length == 1) {
						System.out.printf(Message.REQUIRES_PARAMETER.toString(), kills[0]);
					} else if(!kills[1].matches("[0-9]+") || Integer.parseInt(kills[1]) > BGCommandCount){
						System.out.printf(Message.INVALID_PARAMETER.toString(), command);		
					} else {				
						// kill threads per request
						int pendingRemoveIndex = Integer.parseInt(kills[1]);						
						String pendingRemove = pendingRemoveIndex+". ";
						int size = validBackgroundCommands.size();
//						for(int i = 0; i < size; i++) {
//							if(validBackgroundCommands.get(i).startsWith(pendingRemove)) {
//								 validBackgroundCommands.remove(i);
//								 break;
//							}
//						}											
					}					
				} else {
					//Builds the filters list from the command
					ConcurrentFilter filterlist = ConcurrentCommandBuilder.createFiltersFromCommand(command);
					
					//Collects all filters before starting any of them 
					while(filterlist != null) {
						// Adds all valid filters into a Queue container
						filters.add(filterlist);
						filterlist = (ConcurrentFilter) filterlist.getNext();
					}
					
//					// Waiting for deletion
//					System.out.println("Whether current command is a background command : " + isBGCommand);
					
														
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
						threads.add(t);
						
						
						// It is a background command, there is no need for the main thread to wait for finishing
						if(!isBGCommand) {
							try {
								// Waits for all threads to finish before going to the next command request
								t.join();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}							
					}		
					
				}			
				
>>>>>>> 31e863f19c3ce78fe1ea3dfb52450001c9d10a7e
			}
		}
		
		s.close();
		System.out.print(Message.GOODBYE);
	}

}
