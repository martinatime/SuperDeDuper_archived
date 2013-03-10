package test.org.superdeduper.execution;


import java.util.logging.Logger;

import junit.framework.TestCase;

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
	public void testSuperDeDuper() throws ParameterException {
		SuperDeDuper testNullArgs = new SuperDeDuper(null);
		assertEquals(0, testNullArgs.getExecutionArguments().getParsedParameters().size());
		
		SuperDeDuper testNonNullArgs = new SuperDeDuper(new String[] {Parameter.SCAN_DIRECTORY.getFlag(), "bar"});
		assertEquals(1, testNonNullArgs.getExecutionArguments().getParsedParameters().size());
	}

	@Test
	public void testRun() throws ParameterException {
		SuperDeDuper runner = new SuperDeDuper(null);
		assertNull("The expected result was for a null Object to be returned", runner.run());
	}
}
