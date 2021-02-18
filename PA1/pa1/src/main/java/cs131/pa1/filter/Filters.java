package cs131.pa1.filter;


public enum Filters {
	CAT("cat",false,true),
	PWD("pwd",false,true),
	LS("ls",false,true),
	GREP("grep",true,true),
	WC("wc",true,true),
	UNIQ("uniq",true,true),
	OUTPUTFILE(">",true,false),
	EXIT("exit",false,false);
	
	
	private final String symbol;
	private final boolean pipedInput;
	private final boolean pipedOutput;
	
	private Filters(String symbol, boolean pipedInput, boolean pipedOutput) {
		this.symbol = symbol;
		this.pipedInput = pipedInput;
		this.pipedOutput = pipedOutput;
		
	}
	
	public String toString() {
		return symbol;
	}
	
	public boolean getPipedInput() {
		return pipedInput;
	}
	
	public boolean getPipedOutput() {
		return pipedOutput;
	}

}
