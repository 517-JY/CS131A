package cs131.pa2.filter.concurrent;

/**
 * The filter for printing in the console
 * @author cs131a
 *
 */
public class PrintFilter extends ConcurrentFilter {
	public PrintFilter() {
		super();
	}
	
	public void process() {
		while(!isDone()) {
			processLine(input.poll());
		}
	}
	
	public String processLine(String line) {
		System.out.println(line);
		return null;
	}
}
