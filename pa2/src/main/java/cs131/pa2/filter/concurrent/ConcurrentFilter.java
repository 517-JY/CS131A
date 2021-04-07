package cs131.pa2.filter.concurrent;
import java.util.concurrent.LinkedBlockingQueue;

import cs131.pa2.filter.Filter;

/**
 * An abstract class that extends the Filter and implements the basic functionality of all filters. Each filter should
 * extend this class and implement functionality that is specific for that filter.
 * In addition, the class will implement the Runnable interface to allow
 *      1. different filters to be started as separate threads
 *      2. different filters to run concurrently
 * @author cs131a, Jiayin Li
 *
 */
public abstract class ConcurrentFilter extends Filter implements Runnable{
	
	/**
	 * The input LinkedBlockingQueue for this filter
	 * Reason why choosing LinkedBlockingQueue
	 * 		1. using internal locks to keep thread-safe 
	 *      2. only conducts add(put()) and remove(take()) implementations when it makes sense
	 */
	protected LinkedBlockingQueue<String> input = new LinkedBlockingQueue<String>();
	/**
	 * The output LinkedBlockingQueue for this filter
	 */
	protected LinkedBlockingQueue<String> output= new LinkedBlockingQueue<String>();
	
		
	@Override
	public void setPrevFilter(Filter prevFilter) {
		prevFilter.setNextFilter(this);
	}
	
	@Override
	public void setNextFilter(Filter nextFilter) {
		if (nextFilter instanceof ConcurrentFilter){
			ConcurrentFilter concurrentNext = (ConcurrentFilter) nextFilter;
			this.next = concurrentNext;
			concurrentNext.prev = this;
			if (this.output == null){
				this.output = new LinkedBlockingQueue<String>();
			}
			concurrentNext.input = this.output;
		} else {
			throw new RuntimeException("Should not attempt to link dissimilar filter types.");
		}
	}
	
	/**
	 * Gets the next filter
	 * @return the next filter
	 */
	public Filter getNext() {
		return next;
	}
	
	
	/**
	 * Lets the sequence of filters run concurrently  
	 * Creates separate threads for each filter respectively
	 */
	public void process(){		
		while (!input.isEmpty()){
			String line;
			try {
				// Uses API method for BlockingQueue
				// Only takes line when there are available items to remove 
				line = input.take();
				String processedLine = processLine(line);
				if (processedLine != null){
					// Only puts item into output when the LinkedBlockingQueue is not full
					output.put(processedLine);
				}
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}	
	}
	
	
	/**
	 * Checks whether those filters have finished their jobs
	 * Mainly use within the printFilter and RedirectFilter
	 * Current filter is only marked as finished if the previous filter is finished
	 */
	@Override
	public boolean isDone() {		
		if (prev != null) {
			return prev.isDone() && input.isEmpty();
		}		
		return input.isEmpty();	
	}
	

	/**
	 * The method that inherits from the Runnable interface
	 * Calls the process() method which let the sequence of filters run concurrently
	 */	
	public void run() {
//		// Prints running thread information for sub-phases checking
//		System.out.println(Thread.currentThread().toString() + " is running ....");
		process();		
	}	
	
	
	protected abstract String processLine(String line);
	
}
