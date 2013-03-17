package test.org.superdeduper.execution;


import java.util.Arrays;
import java.util.logging.Logger;

import junit.framework.TestCase;

import org.apache.commons.cli.Option;
import org.junit.Before;
import org.junit.Test;
import org.superdeduper.SuperDeDuper;
import org.superdeduper.parameters.Parameter;
import org.superdeduper.parameters.exception.ParameterException;

public class MainTest extends TestCase {
	private final static String CLASS_NAME = MainTest.class.getName();
	private final static Logger LOGGER = Logger.getLogger(CLASS_NAME);
	
	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	@Test
	public void testScanDirectory() {
		SuperDeDuper testScanDirShort = new SuperDeDuper(new String[] {"-s", "scan-directory"});
		assertNull(testScanDirShort.run());
		assertNotNull(testScanDirShort.getExecutionArguments());
		Option testOpt = new Option("s", "directory", true, "Scan directory - Directory to scan for audio files to process");
		Option[] comparison = new Option[] {testOpt};
		assertTrue(Arrays.equals(comparison, testScanDirShort.getExecutionArguments().getOptions()));
		Option[] actuals = testScanDirShort.getExecutionArguments().getOptions();
		assertEquals("scan-directory", actuals[0].getValue());
		assertTrue(Arrays.equals(new String[0], testScanDirShort.getExecutionArguments().getArgs()));
		// cleanup
		testScanDirShort = null;
		
		SuperDeDuper testScanDirLong = new SuperDeDuper(new String[] {"--directory", "scan-directory"});
		assertNull(testScanDirLong.run());
		assertNotNull(testScanDirLong.getExecutionArguments());
		assertTrue(Arrays.equals(comparison, testScanDirLong.getExecutionArguments().getOptions()));
		Option[] actualsLong = testScanDirLong.getExecutionArguments().getOptions();
		assertEquals("scan-directory", actualsLong[0].getValue());
		assertTrue(Arrays.equals(new String[0], testScanDirLong.getExecutionArguments().getArgs()));
		
		// TODO: add the actual logic for validating that the argument to the option is
		// 1. a valid directory
		// 2. exists
		// 3. can be read
	}

	@Test
	public void testSuperDeDuper() {
		SuperDeDuper testNullArgs = new SuperDeDuper(null);
		assertNull(testNullArgs.run());
		assertNotNull(testNullArgs.getExecutionArguments());
		assertTrue(Arrays.equals(new Option[0], testNullArgs.getExecutionArguments().getOptions()));
		assertTrue(Arrays.equals(new String[0], testNullArgs.getExecutionArguments().getArgs()));
		// clean up
		testNullArgs = null;
		
		SuperDeDuper testNonNullArgs = new SuperDeDuper(new String[] {"foo", "bar"});
		assertNull(testNonNullArgs.run());
		assertNotNull(testNonNullArgs.getExecutionArguments());
		assertTrue(Arrays.equals(new Option[0], testNonNullArgs.getExecutionArguments().getOptions()));
		String[] comparison = {"foo", "bar"};
		assertTrue(Arrays.equals(comparison, testNonNullArgs.getExecutionArguments().getArgs()));
	}

	@Test
	public void testRun() {
		SuperDeDuper runner = new SuperDeDuper(null);
		assertNull("The expected result was for a null Object to be returned", runner.run());
	}
}
