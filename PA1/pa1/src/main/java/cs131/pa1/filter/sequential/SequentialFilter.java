package cs131.pa1.filter.sequential;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.LinkedList;
import java.util.Queue;

import cs131.pa1.filter.Filter;

/**
 * An abstract class that extends the Filter and implements the basic functionality of all filters. Each filter should
 * extend this class and implement functionality that is specific for this filter. 
 * You should not modify this class.
 * @author cs131a
 *
 */
public abstract class SequentialFilter extends Filter {
	/**
	 * The input queue for this filter
	 */
	protected Queue<String> input;
	/**
	 * The output queue for this filter
	 */
	protected Queue<String> output;
	
	@Override
	public void setPrevFilter(Filter prevFilter) {
		prevFilter.setNextFilter(this);
	}
	
	@Override
	public void setNextFilter(Filter nextFilter) {
		if (nextFilter instanceof SequentialFilter){
			SequentialFilter sequentialNext = (SequentialFilter) nextFilter;
			this.next = sequentialNext;
			sequentialNext.prev = this;
			if (this.output == null){
				this.output = new LinkedList<String>();
			}
			sequentialNext.input = this.output;
		} else {
			throw new RuntimeException("Should not attempt to link dissimilar filter types.");
		}
	}
	/**
	 * Processes the input queue and passes the result to the output queue
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 */
	public void process() throws NoSuchFileException, IOException{
		while (!input.isEmpty()){
			String line = input.poll();
			String processedLine = processLine(line);
			if (processedLine != null){
				output.add(processedLine);
			}
		}	
	}
	
	@Override
	public boolean isDone() {
		return input.size() == 0;
	}
	
	/**
	 * Called by the {@link #process()} method for every encountered line in the input queue.
	 * It then performs the processing specific for each filter and returns the result.
	 * Each filter inheriting from this class must implement its own version of processLine() to
	 * take care of the filter-specific processing.
	 * @param line the line got from the input queue
	 * @return the line after the filter-specific processing
	 * @throws IOException 
	 */
	protected abstract String processLine(String line) throws IOException;
	
}
