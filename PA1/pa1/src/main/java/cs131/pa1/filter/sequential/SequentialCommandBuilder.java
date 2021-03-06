package cs131.pa1.filter.sequential;



import java.util.*;

import cs131.pa1.filter.Message;
import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Filters;

import java.io.*;
import java.nio.file.NoSuchFileException;
import java.text.MessageFormat;



/**
 * This class manages the parsing and execution of a command.
 * It splits the raw input into separated subcommands, 
 * creates subcommand filters, and links them into a list. 
 * @author cs131a
 *
 */
public class SequentialCommandBuilder {
	
	 private static Set<Filters> filterCollection = new HashSet<Filters>();
	 
	 private static Set<String> filterSymbolCollection = new HashSet<String>();
	 

		
		
	 // tracks what commands has been executed
	 private static List<String> symbolTraversed = new LinkedList<String>();
	 

	 
	 
	 // Collections that store initial user input command as sub strings
	 private static Queue<String> subStrings = new LinkedList<String>();
	 
	 
	 private static Queue<String> currentFlowInfo;
	 
	 
	 
	 private static List<SequentialFilter> finnalCustomCommand = new LinkedList<SequentialFilter>();
	 
	 
	 private static List<SequentialFilter> filterTraversed = new LinkedList<SequentialFilter>();
	 
	 
	 private String commandLine;
	 private String updatedCommandLine;
	 
	 private static boolean Error_COMMAND_NOT_FOUND = false;
	 private static boolean Error_FILE_NOT_FOUND = false;
	 private static boolean Error_DIRECTORY_NOT_FOUND = false;
	 private static boolean Error_REQUIRES_INPUT = false;
	 private static boolean Error_CANNOT_HAVE_OUTPUT = false;
	 private static boolean Error_REQUIRES_PARAMETER = false;
	 private static boolean Error_INVALID_PARAMETER = false;
	 private static boolean Error_CANNOT_HAVE_INPUT = false;
	 
	 
	 
	 
	 // Pending doc
	 // Constructor 
	 public SequentialCommandBuilder(String commandLine) { 	 
    	 this.commandLine = commandLine;
	 }
	 
	 
	 
	 // Methods that split initial user input command as sub strings
	 private static void splitInitialUserCommand(String commandLine) throws NoSuchFileException, IOException{
		 
		 // get an updated Command String with "|" properly added 
		 String updatedCommand = processCommandString(commandLine);
		 
		 String[] temp = updatedCommand.split("\\|");
		 
		 for(int i = 0 ; i < temp.length; i++) {
			 subStrings.add(temp[i].trim());
		 }
	 }
	 
	 
	 
	 // pending doc
	 // Return subStrings
	 public static Queue<String> getSubStrings() {
		 return subStrings;
	 }
	 
	 


	
	 // Pending doc
     public static Set<Filters> getFilterCollection() {
    	 return filterCollection;	 
     }
	
     
	 // Pending doc
     public static Set<String> getFilterSymbolCollection() {
    	 return filterSymbolCollection;	 
     }
     
     

	
	

    // Pending doc
     private static void buildFiltersCollection(){
    	 filterSymbolCollection.add("pwd");
    	 filterSymbolCollection.add("ls");
    	 filterSymbolCollection.add("cd");
    	 filterSymbolCollection.add("cat");
    	 filterSymbolCollection.add("grep");
    	 filterSymbolCollection.add("wc");
    	 filterSymbolCollection.add("uniq");
    	 filterSymbolCollection.add(">");
    	 filterSymbolCollection.add("exit");
    	 filterSymbolCollection.add("cd");
    	 filterSymbolCollection.add("");
     }
	
	

	/**
	 * Creates and returns a list of filters from the specified command
	 * @param command the command to create filters from
	 * @return the list of SequentialFilter that represent the specified command
	 * @throws IOException 
	 */
	public static List<SequentialFilter> createFiltersFromCommand(String command) throws NoSuchFileException, IOException  {
		
		
		buildFiltersCollection();
		
		
		splitInitialUserCommand(command);
		
		
		
		String lastCommand= "";

	
		
		SequentialFilter currFilter = null;
		SequentialFilter prevFilter = null;
		
		// System.out.println("SubStrings size is : " + subStrings.size());
		

		filterTraversed.clear();
		while(!subStrings.isEmpty()) {
			//System.out.println("substrings " + subStrings);
			String subCommand = subStrings.poll().trim();		
			
            
			
	
			currFilter = constructFilterFromSubCommand(subCommand);

			filterTraversed.add(currFilter);
			
			
			//System.out.println("filters add" + currFilter);
			
			if (prevFilter !=null) {
				prevFilter.setNextFilter(currFilter);
			}

//			System.out.println("\n\nCurrent filter's input is null  " + (currFilter.input== null));
//			System.out.println("Current filter's input is " + currFilter.input);
//			
//			System.out.println("Current filter's output is null  " + (currFilter.output== null));
//			System.out.println("Current filter's output is " + currFilter.output);
//
//			System.out.println("Current filter traversed size is " + filterTraversed.size());
//			System.out.println("Current filter traversed size is " + filterTraversed);
			
			if (currFilter !=null) {
				currFilter.process();
				
				if(currFilter.isDone()) {
					if (currFilter != null && prevFilter!=null) {
						currFilter.setPrevFilter(prevFilter);
				    } 
				}
				
				
//
//			System.out.println("\n\nCurrent filter's input is null  " + (currFilter.input== null));
//			System.out.println("Current filter's input is " + currFilter.input);
//				
//			System.out.println("Current filter's output is null  " + (currFilter.output== null));
//			System.out.println("Current filter's output is " + currFilter.output);
			}
			
			
			prevFilter = currFilter;
			
		
			
			lastCommand = subCommand;
			
		}
		
		// conducts final filter 
		// whether print to screen or write
		
//		System.out.println("Current executed filter collections is " + filterTraversed);
//		
//		System.out.println("last Command is " + lastCommand);
		
		
		// boolean linkedCheck = linkFilters(filterTraversed);
		
	
		
		// System.out.println("Whether is here after linkFilters check " + linkedCheck);
		
		// Needs further modifying
		if (linkFilters(filterTraversed)) {
			SequentialFilter finalFilter = determineFinalFilter(lastCommand);
			
			
			if (filterTraversed != null) {
				SequentialFilter penultimateFilter  = filterTraversed.get(filterTraversed.size() - 1);
//				System.out.println("penultimateFilter input  is  " + penultimateFilter.input);
//				System.out.println("penultimateFilter output is  " + penultimateFilter.output);
				
				if (penultimateFilter != null && finalFilter != null) {
					penultimateFilter.setNextFilter(finalFilter);
					finalFilter.setPrevFilter(penultimateFilter);
				}
				
				if (finalFilter != null) {
					finalFilter.process();
				}
			}
		}
		return filterTraversed;
	}
	
	
	
	
	
	// Pending doc
	// try to add "|" to every command starts with ">"
	private static String processCommandString(String command) throws NoSuchFileException, IOException{
		int OutputFilterCount = 0 ;
        int start = 0;

        ArrayList<Integer> OFindices = new ArrayList<Integer>();

        int flag = command.indexOf(">", start);

        StringBuilder ns = new StringBuilder(command);
        
        while(start < command.length() && flag != -1) {
            OFindices.add(flag);
            start = flag + 1;
            flag = command.indexOf(">", start);
            OutputFilterCount++;
        }

        int j = 0;
        if(OFindices != null) {
            for (int i = 0; i <OutputFilterCount; i++ ) {
            	
            	if(OFindices.get(i)== 0) {
            		ns.insert(0, "|");
            	}
            	else {
                	// inserts "|" before every ">" filter
                    ns.insert(OFindices.get(i)-(j+1), "|");
            	}

                j--;
                
            }

        }
                
       return ns.toString().trim();
          
	}
	
	
	

	
	
	/**
	 * Returns the filter that appears last in the specified command
	 * @param command the command to search from
	 * @return the SequentialFilter that appears last in the specified command
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private static SequentialFilter determineFinalFilter(String command) throws NoSuchFileException, IOException{
		SequentialFilter finalFilter = null;
		
		String[] splitLastCommand = command.split("\\s+");
		String finalSymbol = "";
		String outputFilePara = "";
		
		if(splitLastCommand != null && splitLastCommand.length != 0) {
			finalSymbol = splitLastCommand[0].toLowerCase().trim();
		}
			
		if(finalSymbol.equals("cat")|| finalSymbol.equals("pwd") 
				|| finalSymbol.equals("ls")|| finalSymbol.equals("grep")
				|| finalSymbol.equals("wc")|| finalSymbol.equals("uniq") ) {
			finalFilter = new OutputScreen("outputScreen");
		} else if (finalSymbol.equals(">")) {
			
			if(splitLastCommand != null && splitLastCommand.length >1) {
				outputFilePara = splitLastCommand[1].trim();
			}
			finalFilter = new OutputFile("outputFile",outputFilePara);
		}

		
		return finalFilter;
	}
	
	
	
	/**
	 * Returns a string that contains the specified command without the final filter
	 * @param command the command to parse and remove the final filter from 
	 * @return the adjusted command that does not contain the final filter
	 */
	private static String adjustCommandToRemoveFinalFilter(String command){
		return null;
	}
	
	/**
	 * Creates a single filter from the specified subCommand
	 * @param subCommand the command to create a filter from
	 * @return the SequentialFilter created from the given subCommand
	 * @throws IOException 
	 */
	private static SequentialFilter constructFilterFromSubCommand(String subCommand) throws IOException, NoSuchFileException {
		SequentialFilter target = null;
		String[] filterAndPara = subCommand.split("\\s+");
		String symbol = "";
		String para = "";
		
		symbolTraversed.add(filterAndPara[0]);
	
		Error_COMMAND_NOT_FOUND   = checkCommandNotFound(filterAndPara);
		if (!Error_COMMAND_NOT_FOUND) {
			Error_REQUIRES_PARAMETER  = checkRequiresParameter(filterAndPara);
			
			// Some errors can only be checked after get previous executed
//			if(!Error_REQUIRES_PARAMETER) {
//				Error_REQUIRES_INPUT      = checkRequiresInput(filterAndPara);
//			}
		}
		

		
		
		
		if (!Error_COMMAND_NOT_FOUND && !Error_REQUIRES_PARAMETER ) 
		{
			symbol = filterAndPara[0].toLowerCase();
			
			//System.out.println("symbol is "+ symbol);
			
			if (symbol.equals("cat")) {
				para = filterAndPara[1];
				
				// create a cat filter object
				target = new Cat(para);
				
				// System.out.println("Here means a cat filer has been created.");
				
				// Cat filter does not have Piped_input
				// target.input = null;

			} else if (symbol.equals("grep")) {
				para = filterAndPara[1];
				
				//System.out.println("Whether is here grep object creation");
				
				// create a grep filter object 
				//System.out.println("Grep's para is " + para);
				target = new Grep(para);
				
				
			} else if (symbol.equals("uniq")) {
	
				target = new Uniq();
				
			} else if (symbol.equals("wc")) {
				
				target = new Wc();
			} else if (symbol.equals(">")) {
				para = filterAndPara[1];

				target = new BiggerThan(para);
			} else if (symbol.equals("pwd")) {
				target = new Pwd();
			} else if (symbol.equals("ls")) {
				target = new Ls();
			} else if (symbol.equals("cd")) {
				para = filterAndPara[1];

				target = new Cd(para);
				
			}
					
			

		}
		
		return target;
	}
	
	
	
	// Methods check if command could be found
	// pending doc
	// complete
	private static boolean checkCommandNotFound(String[] commands) throws NoSuchFileException, IOException {
		boolean checkErrorFound = false;
		String symbol= "";
		String output= commands[0];
		
		if(commands !=null && commands.length !=0) {
			symbol = commands[0];
			checkErrorFound = !filterSymbolCollection.contains(symbol.toLowerCase());

		}
		
		for(int i = 1; i < commands.length; i++) {
			output += " ";
			output += commands[i];
		}
		
		
		// if CommandNotFound Error found, print relevant information
		if(checkErrorFound) {
			System.out.print(Message.COMMAND_NOT_FOUND.with_parameter(output));	
		}
		
		return checkErrorFound;
	}
	
	
	// checks if there is any parameters missing
	// pending doc
	// complete
	private static boolean checkRequiresParameter(String[] commands) throws NoSuchFileException, IOException {
		boolean checkErrorFound = false;
		String symbol= "";
		
		if(commands !=null && commands.length !=0) {
			symbol = commands[0];
		}
		
		String testSymbol = symbol.toLowerCase();
		
		if(testSymbol.equals("cat") || testSymbol.equals("grep") || 
				testSymbol.equals("cd") || testSymbol.equals(">")) {
			checkErrorFound = (commands.length==1);
		}
		
		
		
		// if CommandNotFound Error found, print relevant information
		if(checkErrorFound) {
			System.out.print(Message.REQUIRES_PARAMETER.with_parameter(symbol));	
		}
		
		
		return checkErrorFound;
	}
	
	
	
	// checks if there is information in the currentFlow
	// pending doc
	// wc test does not paas
	private static boolean checkRequiresInput(List<SequentialFilter> filters) throws NoSuchFileException, IOException {
		boolean checkErrorFound = false;
		String currFilterSymbol="";
		String currToStringSymbol="";
		String currFilterPara ="";
		String prevFilterSymbol=null;
		SequentialFilter currFilter = null;
		SequentialFilter prevFilter = null;
		
		if(filters!=null) {
			for(int i = 0; i< filters.size(); i++) {
				currFilter = filters.get(i);
				
				if(currFilter!= null) {
					String[] temp = currFilter.toString().split("\\s+");
				
					if(temp != null) {
						currFilterSymbol = temp[0].toLowerCase();
						currToStringSymbol = currFilterSymbol;
						if(temp.length > 1) {
							currToStringSymbol += " " + temp[1];
						}
					}
					
					
					if(currFilterSymbol !=null) {
						if(currFilterSymbol.equals("wc") ||currFilterSymbol.equals("grep") ||
								currFilterSymbol.equals(">") ||currFilterSymbol.equals("uniq")) {
							if(prevFilterSymbol == null || prevFilterSymbol.equals("cd") || prevFilterSymbol.equals(">")) {
								
								
								checkErrorFound = true;
							}
						}
						
						if(checkErrorFound) {

							System.out.print(Message.REQUIRES_INPUT.with_parameter(currToStringSymbol));	
						}
						
						
					}
					
					prevFilterSymbol = currFilterSymbol;

				}
				
				

			}
		}
		
		
		
		return checkErrorFound;
	}
	
	
	
	
	
	private static boolean checkCanNotHaveInput(List<SequentialFilter> filters) throws NoSuchFileException, IOException {
		boolean checkErrorFound = false;
		String currFilterSymbol="";
		String currToStringSymbol="";
		String currFilterPara ="";
		String prevFilterSymbol=null;
		SequentialFilter currFilter = null;
		SequentialFilter prevFilter = null;
		
		if(filters!=null) {
			for(int i = 0; i< filters.size(); i++) {
				currFilter = filters.get(i);
				
				if(currFilter!= null) {
					String[] temp = currFilter.toString().split("\\s+");
					
				
					if(temp != null) {
						currFilterSymbol = temp[0].toLowerCase();
						currToStringSymbol = currFilterSymbol;
						if(temp.length > 1) {
							currToStringSymbol = temp[0]+ " " + temp[1];
						}
					}
					
					if(currFilterSymbol !=null) {
						if(currFilterSymbol.equals("cat") ||currFilterSymbol.equals("ls") ||
								currFilterSymbol.equals("pwd") ||currFilterSymbol.equals("cd")) {
							if(prevFilterSymbol != null) {
								 
								checkErrorFound = true;

							}
						}
						
						if(checkErrorFound) {

							System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter(currToStringSymbol));	
						}
						
						
					}
					
					prevFilterSymbol = currFilterSymbol;

				}
				
				

			}
		}
		
		
		
		return checkErrorFound;
	}
	
	
	
	/**
	 * links the given filters with the order they appear in the list
	 * @param filters the given filters to link
	 * @return true if the link was successful, false if there were errors encountered.
	 * Any error should be displayed by using the Message enum.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	
	// All filters before the last one 
	private static boolean linkFilters(List<SequentialFilter> filters) throws NoSuchFileException, IOException{
		SequentialFilter currFilter = null;
		SequentialFilter prevFilter = null;
		
		
		Error_REQUIRES_INPUT = checkRequiresInput(filters);
		Error_CANNOT_HAVE_INPUT = checkCanNotHaveInput(filters);
		
		//System.out.println("Filters is " + filters);
		
		
		// System.out.println("Whether is linkFilters get");
		
//		if (filters!= null) {
//			for(int i = 0 ;i < filters.size(); i++) {
//				System.out.println("current filters is " + filters.get(i).toString());
//			}
//		}

		
		
		
		
//		if(filters != null) {
//			
//			for (int i = 0; i < filters.size(); i++) {
//				currFilter = filters.get(i);
//				
//				// Attention : Before this , everything is fine 
//				
//				//System.out.println("current directory " + SequentialREPL.currentWorkingDirectory);
//				if (currFilter !=null) {
//					currFilter.process();
//					//System.out.println("Current filter's output is " + currFilter.output);
//				}
//
//				if (currFilter !=null && prevFilter !=null) {
//					currFilter.setPrevFilter(prevFilter);
//					prevFilter.setNextFilter(currFilter);
//				}
//				
//				prevFilter = currFilter;
//				
//			}
//		}
		
		
		boolean sofarLinkedCheck = !Error_COMMAND_NOT_FOUND && !Error_REQUIRES_PARAMETER 
				&& !Error_REQUIRES_INPUT && !Error_CANNOT_HAVE_INPUT;
		
		return sofarLinkedCheck;
	}
	

}