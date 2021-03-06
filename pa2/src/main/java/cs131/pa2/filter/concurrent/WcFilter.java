package cs131.pa2.filter.concurrent;
/**
 * The filter for wc command
 * @author cs131a
 *
 */
public class WcFilter extends ConcurrentFilter {
	/**
	 * The count of lines found
	 */
	private int linecount;
	/**
	 * The count of words found
	 */
	private int wordcount;
	/**
	 * The count of characters found
	 */
	private int charcount;
	
	public WcFilter() {
		super();
	}
	
	public void process() {
		if(isDone()) {
			output.add(processLine(null));
		} else {
			super.process();
		}
	}
	/**
	 * Counts the number of lines, words and characters from the input queue
	 * @param line the line as got from the input queue
	 * @return the number of lines, words, and characters when finished, null otherwise
	 */
	public String processLine(String line) {
		//prints current result if ever passed a null
		if(line == null) {
			return linecount + " " + wordcount + " " + charcount;
		}
		
		if(isDone()) {
			String[] wct = line.split(" ");
			wordcount += wct.length;
			String[] cct = line.split("|");
			charcount += cct.length;
			return ++linecount + " " + wordcount + " " + charcount;
		} else {
			linecount++;
			String[] wct = line.split(" ");
			wordcount += wct.length;
			String[] cct = line.split("|");
			charcount += cct.length;
			return null;
		}
	}
}
