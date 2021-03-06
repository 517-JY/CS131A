package cs131.pa2.filter.concurrent;
/**
 * The filter for pwd command
 * @author cs131a
 *
 */
public class PwdFilter extends ConcurrentFilter {
	public PwdFilter() {
		super();
	}
	
	public void process() {
		output.add(processLine(""));
	}
	
	public String processLine(String line) {
		return ConcurrentREPL.currentWorkingDirectory;
	}
}
