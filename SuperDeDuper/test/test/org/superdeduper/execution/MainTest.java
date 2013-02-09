package test.org.superdeduper.execution;


import java.util.logging.Logger;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.superdeduper.SuperDeDuper;

public class MainTest extends TestCase {
	private final static String CLASS_NAME = MainTest.class.getName();
	private final static Logger LOGGER = Logger.getLogger(CLASS_NAME);
	
	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testSuperDeDuper() {
		SuperDeDuper testNullArgs = new SuperDeDuper(null);
		assertNull(testNullArgs.getExecutionArguments());
		
		SuperDeDuper testNonNullArgs = new SuperDeDuper(new String[] {"foo", "bar"});
		assertNotNull(testNonNullArgs.getExecutionArguments());
	}

	@Test
	public void testRun() {
		SuperDeDuper runner = new SuperDeDuper(null);
		assertNull("The expected result was for a null Object to be returned", runner.run());
	}
}
