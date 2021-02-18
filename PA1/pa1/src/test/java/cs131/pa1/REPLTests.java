package cs131.pa1;

import cs131.pa1.filter.Message;
import cs131.pa1.filter.sequential.SequentialREPL;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class REPLTests {

	@Test
	public void testExit() throws IOException, FileNotFoundException {
		testInput("exit");
		SequentialREPL.main(null);
		assertOutput("");
	}
	
	@Test
	public void testNotACommand1() throws IOException, FileNotFoundException {
		testInput("not-a-command\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "The command [not-a-command] was not recognized.\n");
	}
	
	@Test
	public void testNotACommand2() throws IOException, FileNotFoundException {
		testInput("ls | gripe HELLO\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "The command [gripe HELLO] was not recognized.\n");
	}
	
	@Test
	public void testNotACommand3() throws IOException, FileNotFoundException {
		testInput("cathello.txt\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "The command [cathello.txt] was not recognized.\n");
	}
	
	@Test
	public void testNotACommand4() throws IOException, FileNotFoundException {
		testInput("cdsrc\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "The command [cdsrc] was not recognized.\n");
	}
	
	@Test
	public void testNotACommand5() throws IOException, FileNotFoundException {
		testInput("pwd | grepunixish\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "The command [grepunixish] was not recognized.\n");
	}
	
	@Test
	public void testCanContinueAfterError1() throws IOException, FileNotFoundException {
		testInput("cd dir1\n ls | gripe HELLO\nls | grep f1\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND.toString() + Message.NEWCOMMAND + "The command [gripe HELLO] was not recognized.\n> f1.txt\n");
	}
	
	@Test
	public void testCanContinueAfterError2() throws IOException, FileNotFoundException {
		testInput("cat fizz-buzz-100000.txt | grep 1 | wc\ncat fizz-buzz-10000.txt | grep 1 | wc\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "At least one of the files in the command [cat fizz-buzz-100000.txt] was not found.\n> 1931 1931 7555\n");
	}
	
	@Test
	public void testFileNotFound() throws IOException, FileNotFoundException {
		testInput("cat doesnt-exist.txt\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "At least one of the files in the command [cat doesnt-exist.txt] was not found.\n");
	}
	
	@Test
	public void testDirectoryNotFound() throws IOException, FileNotFoundException  {
		testInput("cd mystery-dir\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + Message.DIRECTORY_NOT_FOUND.with_parameter("cd mystery-dir"));
	}
    
    
    // ********** Input/Output Tests **********

	@Test
	public void testPwdCannotHaveInput() throws IOException, FileNotFoundException  {
		testInput("cat hello-world.txt | pwd\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + Message.CANNOT_HAVE_INPUT.with_parameter("pwd"));
	}
	
	@Test
	public void testLsCannotHaveInput() throws IOException, FileNotFoundException  {
		testInput("cat hello-world.txt | ls\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + Message.CANNOT_HAVE_INPUT.with_parameter("ls"));
	}
	
	@Test
	public void testCdCannotHaveInput() throws IOException, FileNotFoundException  {
		testInput("cat hello-world.txt | cd dir1\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + Message.CANNOT_HAVE_INPUT.with_parameter("cd dir1"));
	}
	
	@Test
    public void testCdCannotHaveOutput1() throws IOException, FileNotFoundException  {
    	testInput("cd dir1\nexit");
    	SequentialREPL.main(null);
    	assertOutput(Message.NEWCOMMAND.toString());
    }
	
	@Test
    public void testCdCannotHaveOutput2() throws IOException, FileNotFoundException  {
    	testInput("cd dir1 | wc\nexit");
    	SequentialREPL.main(null);
    	assertOutput(Message.NEWCOMMAND + Message.CANNOT_HAVE_OUTPUT.with_parameter("cd dir1"));
    }
	
	@Test
	public void testCdRequiresParameter() throws IOException, FileNotFoundException  {
		testInput("cd\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + Message.REQUIRES_PARAMETER.with_parameter("cd"));
	}

	@Test
	public void testCatCannotHaveInput() throws IOException, FileNotFoundException  {
    	testInput("pwd | cat hello-world.txt\nexit");
    	SequentialREPL.main(null);
    	assertOutput(Message.NEWCOMMAND + Message.CANNOT_HAVE_INPUT.with_parameter("cat hello-world.txt"));
    }
    
    @Test
    public void testCatRequiresParameter1() throws IOException, FileNotFoundException  {
    	testInput("cat\nexit");
    	SequentialREPL.main(null);
    	assertOutput(Message.NEWCOMMAND + Message.REQUIRES_PARAMETER.with_parameter("cat"));
    }
    
    @Test
    public void testCatFileNotFound() throws IOException, FileNotFoundException  {
    	testInput("cat iloveos hello-world.txt\nexit");
    	SequentialREPL.main(null);
    	assertOutput(Message.NEWCOMMAND + Message.FILE_NOT_FOUND.with_parameter("cat iloveos hello-world.txt"));
    }
    

	@Test
	public void testGrepRequiresInput() throws IOException, FileNotFoundException  {
		testInput("grep hahaha\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + Message.REQUIRES_INPUT.with_parameter("grep hahaha"));
	}
	
	@Test
	public void testGrepRequiresParameter() throws IOException, FileNotFoundException  {
		testInput("pwd | grep\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + Message.REQUIRES_PARAMETER.with_parameter("grep"));
	}
	
	@Test
	public void testWcRequiresInput() throws IOException, FileNotFoundException  {
		testInput("wc\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + Message.REQUIRES_INPUT.with_parameter("wc"));
	}
        
    @Test
    public void testRedirectionRequiresInput() throws IOException, FileNotFoundException {
    	testInput("> new-hello-world.txt\nexit");
    	SequentialREPL.main(null);
    	assertOutput(Message.NEWCOMMAND + Message.REQUIRES_INPUT.with_parameter("> new-hello-world.txt"));
    }
    
    @Test
    public void testRedirectionRequiresParameter() throws IOException, FileNotFoundException {
    	testInput("ls >\nexit");
    	SequentialREPL.main(null);
    	assertOutput(Message.NEWCOMMAND + Message.REQUIRES_PARAMETER.with_parameter(">"));
    }
    
    @Test
    public void testRedirectionNoOutput1() throws IOException, FileNotFoundException  {
    	testInput("cat hello-world.txt > new-hello-world.txt\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND.toString());
		AllSequentialTests.destroyFile("new-hello-world.txt");
    }
    
    @Test
    public void testRedirectionNoOutput2() throws IOException, FileNotFoundException  {
    	testInput("cat hello-world.txt > new-hello-world.txt|wc\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND.toString() + Message.CANNOT_HAVE_OUTPUT.with_parameter("> new-hello-world.txt"));
		AllSequentialTests.destroyFile("new-hello-world.txt");
    }
	
	private ByteArrayInputStream inContent;
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	
	public void testInput(String s){
		inContent = new ByteArrayInputStream(s.getBytes());
		System.setIn(inContent);
	}
	
	public void assertOutput(String expected){
		AllSequentialTests.assertOutput(expected, outContent);
	}
	
	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@After
	public void cleanUpStreams() {
		System.setIn(null);
	    System.setOut(null);
	    System.setErr(null);
	}
}
