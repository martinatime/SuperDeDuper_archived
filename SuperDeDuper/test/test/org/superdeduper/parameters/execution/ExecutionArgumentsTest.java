/**
 * 
 */
package test.org.superdeduper.parameters.execution;

import junit.framework.TestCase;

import org.junit.Test;
import org.superdeduper.parameters.ExecutionArguments;
import org.superdeduper.parameters.Occurrence;
import org.superdeduper.parameters.Parameter;
import org.superdeduper.parameters.ValueInstance;
import org.superdeduper.parameters.ValueState;
import org.superdeduper.parameters.ValueType;
import org.superdeduper.parameters.exception.DuplicateParameterException;
import org.superdeduper.parameters.exception.InvalidParameterTypeException;
import org.superdeduper.parameters.exception.MissingParameterException;
import org.superdeduper.parameters.exception.ParameterException;
import org.superdeduper.parameters.exception.UnknownParameterException;
import org.superdeduper.parameters.exception.UnsupportedParameterException;

/**
 * @author evnjones
 *
 */
public class ExecutionArgumentsTest extends TestCase {
	/**
	 * 
	 */
	@Test
	public void testNoParameters() {
		try {
			ExecutionArguments execArgs = new ExecutionArguments();
			execArgs.parse(null, false);
		} catch(ParameterException pe) {
			assertNull(pe);
		}
	}
	
	/**
	 * 
	 */
	@Test
	public void testSingleInstanceParameters() {
		try {
			ExecutionArguments execArgs = new ExecutionArguments();
			execArgs.addParameterDefinition(Parameter.SCAN_DIRECTORY, Occurrence.REQUIRED, ValueState.VALUE_PRESENT, ValueType.STRING, ValueInstance.SINGLE);
			execArgs.parse(new String[]{Parameter.SCAN_DIRECTORY.getFlag(), "value1"}, false);
		} catch(ParameterException pe) {
			assertNull(pe);
		}

		try {
			ExecutionArguments execArgs = new ExecutionArguments();
			execArgs.addParameterDefinition(Parameter.SCAN_DIRECTORY, Occurrence.REQUIRED, ValueState.VALUE_PRESENT, ValueType.STRING, ValueInstance.SINGLE);
			execArgs.parse(new String[]{Parameter.SCAN_DIRECTORY.getFlag(), "value1", Parameter.SCAN_DIRECTORY.getFlag(), "value2"}, false);
		} catch(ParameterException pe) {
			assertNotNull(pe);
			assertEquals(DuplicateParameterException.class, pe.getClass());
		}
	}
	
	/**
	 * 
	 */
	@Test
	public void testMultipleInstanceParameters() {
		try {
			ExecutionArguments execArgs = new ExecutionArguments();
			execArgs.addParameterDefinition(Parameter.SCAN_DIRECTORY, Occurrence.REQUIRED, ValueState.VALUE_PRESENT, ValueType.STRING, ValueInstance.SINGLE);
			execArgs.parse(new String[]{Parameter.SCAN_DIRECTORY.getFlag(), "value1", Parameter.SCAN_DIRECTORY.getFlag(), "value2"}, false);
		} catch(ParameterException pe) {
			assertNotNull(pe);
			assertEquals(DuplicateParameterException.class, pe.getClass());
		}
		
		try {
			ExecutionArguments execArgs = new ExecutionArguments();
			execArgs.addParameterDefinition(Parameter.SCAN_DIRECTORY, Occurrence.REQUIRED, ValueState.VALUE_PRESENT, ValueType.STRING, ValueInstance.MULTIPLE);
			execArgs.parse(new String[]{Parameter.SCAN_DIRECTORY.getFlag(), "value1", Parameter.SCAN_DIRECTORY.getFlag(), "value2"}, false);
			assertEquals("value1", execArgs.getParsedParameterValue(Parameter.SCAN_DIRECTORY));
			assertEquals(2, execArgs.getParsedParameterValues(Parameter.SCAN_DIRECTORY).size());
			assertEquals("value1", execArgs.getParsedParameterValues(Parameter.SCAN_DIRECTORY).get(0));
			assertEquals("value2", execArgs.getParsedParameterValues(Parameter.SCAN_DIRECTORY).get(1));
		} catch(ParameterException pe) {
			assertNull(pe);
		}
		
		try {
			ExecutionArguments execArgs = new ExecutionArguments();
			execArgs.addParameterDefinition(Parameter.SCAN_DIRECTORY, Occurrence.REQUIRED, ValueState.VALUE_PRESENT, ValueType.STRING, ValueInstance.MULTIPLE);
			execArgs.parse(new String[]{Parameter.SCAN_DIRECTORY.getFlag(), "value1"}, false);
			assertEquals("value1", execArgs.getParsedParameterValue(Parameter.SCAN_DIRECTORY));
			assertEquals(1, execArgs.getParsedParameterValues(Parameter.SCAN_DIRECTORY).size());
			assertEquals("value1", execArgs.getParsedParameterValues(Parameter.SCAN_DIRECTORY).get(0));
		} catch(ParameterException pe) {
			assertNull(pe);
		}
	}
	
	/**
	 * 
	 */
	@Test
	public void testStringValueParameter() {
		try {
			ExecutionArguments execArgs = new ExecutionArguments();
			execArgs.addParameterDefinition(Parameter.SCAN_DIRECTORY, Occurrence.REQUIRED, ValueState.VALUE_PRESENT, ValueType.STRING, ValueInstance.SINGLE);
			execArgs.parse(new String[]{Parameter.SCAN_DIRECTORY.getFlag(), "9"}, false);
			assertEquals("9", execArgs.getParsedParameterValue(Parameter.SCAN_DIRECTORY));
		} catch(ParameterException pe) {
			assertNull(pe);
		}
		
		try {
			ExecutionArguments execArgs = new ExecutionArguments();
			execArgs.addParameterDefinition(Parameter.SCAN_DIRECTORY, Occurrence.REQUIRED, ValueState.VALUE_PRESENT, ValueType.STRING, ValueInstance.SINGLE);
			execArgs.parse(new String[]{Parameter.SCAN_DIRECTORY.getFlag(), "not a long"}, false);
			assertEquals("not a long", execArgs.getParsedParameterValue(Parameter.SCAN_DIRECTORY));
		} catch(ParameterException pe) {
			assertNull(pe);
		}
	}
	
	/**
	 * 
	 */
	@Test
	public void testIntegerValueParameter() {
		try {
			ExecutionArguments execArgs = new ExecutionArguments();
			execArgs.addParameterDefinition(Parameter.SCAN_DIRECTORY, Occurrence.REQUIRED, ValueState.VALUE_PRESENT, ValueType.INTEGER, ValueInstance.SINGLE);
			execArgs.parse(new String[]{Parameter.SCAN_DIRECTORY.getFlag(), "9"}, false);
			assertEquals("9", execArgs.getParsedParameterValue(Parameter.SCAN_DIRECTORY));
		} catch(ParameterException pe) {
			assertNull(pe);
		}
		
		try {
			ExecutionArguments execArgs = new ExecutionArguments();
			execArgs.addParameterDefinition(Parameter.SCAN_DIRECTORY, Occurrence.REQUIRED, ValueState.VALUE_PRESENT, ValueType.INTEGER, ValueInstance.SINGLE);
			execArgs.parse(new String[]{Parameter.SCAN_DIRECTORY.getFlag(), "not an integer"}, false);
			assertEquals("not an integer", execArgs.getParsedParameterValue(Parameter.SCAN_DIRECTORY));
		} catch(ParameterException pe) {
			assertNotNull(pe);
			assertEquals(InvalidParameterTypeException.class, pe.getClass());
		}
	}
	
	/**
	 * 
	 */
	@Test
	public void testLongValueParameter() {
		try {
			ExecutionArguments execArgs = new ExecutionArguments();
			execArgs.addParameterDefinition(Parameter.SCAN_DIRECTORY, Occurrence.REQUIRED, ValueState.VALUE_PRESENT, ValueType.LONG, ValueInstance.SINGLE);
			execArgs.parse(new String[]{Parameter.SCAN_DIRECTORY.getFlag(), "9"}, false);
			assertEquals("9", execArgs.getParsedParameterValue(Parameter.SCAN_DIRECTORY));
		} catch(ParameterException pe) {
			assertNull(pe);
		}
		
		try {
			ExecutionArguments execArgs = new ExecutionArguments();
			execArgs.addParameterDefinition(Parameter.SCAN_DIRECTORY, Occurrence.REQUIRED, ValueState.VALUE_PRESENT, ValueType.LONG, ValueInstance.SINGLE);
			execArgs.parse(new String[]{Parameter.SCAN_DIRECTORY.getFlag(), "not a long"}, false);
			assertEquals("not a long", execArgs.getParsedParameterValue(Parameter.SCAN_DIRECTORY));
		} catch(ParameterException pe) {
			assertNotNull(pe);
			assertEquals(InvalidParameterTypeException.class, pe.getClass());
		}
	}
	
	/**
	 * 
	 */
	@Test
	public void testDoubleValueParameter() {
		try {
			ExecutionArguments execArgs = new ExecutionArguments();
			execArgs.addParameterDefinition(Parameter.SCAN_DIRECTORY, Occurrence.REQUIRED, ValueState.VALUE_PRESENT, ValueType.DOUBLE, ValueInstance.SINGLE);
			execArgs.parse(new String[]{Parameter.SCAN_DIRECTORY.getFlag(), "9.99"}, false);
			assertEquals("9.99", execArgs.getParsedParameterValue(Parameter.SCAN_DIRECTORY));
		} catch(ParameterException pe) {
			assertNull(pe);
		}
		
		try {
			ExecutionArguments execArgs = new ExecutionArguments();
			execArgs.addParameterDefinition(Parameter.SCAN_DIRECTORY, Occurrence.REQUIRED, ValueState.VALUE_PRESENT, ValueType.DOUBLE, ValueInstance.SINGLE);
			execArgs.parse(new String[]{Parameter.SCAN_DIRECTORY.getFlag(), "not a double"}, false);
			assertEquals("not a double", execArgs.getParsedParameterValue(Parameter.SCAN_DIRECTORY));
		} catch(ParameterException pe) {
			assertNotNull(pe);
			assertEquals(InvalidParameterTypeException.class, pe.getClass());
		}
	}
	
	/**
	 * 
	 */
	@Test
	public void testNonTypedValueParameter() {
		try {
			ExecutionArguments execArgs = new ExecutionArguments();
			execArgs.addParameterDefinition(Parameter.SCAN_DIRECTORY, Occurrence.REQUIRED, ValueState.VALUE_PRESENT, ValueType.NOT_SPECIFIED, ValueInstance.SINGLE);
			execArgs.parse(new String[]{Parameter.SCAN_DIRECTORY.getFlag(), "9"}, false);
			assertEquals("9", execArgs.getParsedParameterValue(Parameter.SCAN_DIRECTORY));
		} catch(ParameterException pe) {
			assertNull(pe);
		}
		
		try {
			ExecutionArguments execArgs = new ExecutionArguments();
			execArgs.addParameterDefinition(Parameter.SCAN_DIRECTORY, Occurrence.REQUIRED, ValueState.VALUE_PRESENT, ValueType.NOT_SPECIFIED, ValueInstance.SINGLE);
			execArgs.parse(new String[]{Parameter.SCAN_DIRECTORY.getFlag(), "not a long"}, false);
			assertEquals("not a long", execArgs.getParsedParameterValue(Parameter.SCAN_DIRECTORY));
		} catch(ParameterException pe) {
			assertNull(pe);
		}
	}
	
	/**
	 * 
	 */
	@Test
	public void testMissingParameter() {
		try {
			ExecutionArguments execArgs = new ExecutionArguments();
			execArgs.addParameterDefinition(Parameter.SCAN_DIRECTORY, Occurrence.REQUIRED, ValueState.VALUE_PRESENT, ValueType.STRING, ValueInstance.SINGLE);
			execArgs.parse(null, false);
		} catch(ParameterException pe) {
			assertNotNull(pe);
			assertEquals(MissingParameterException.class, pe.getClass());
		}
	}
	
	/**
	 * 
	 */
	@Test
	public void testStrictMode() {
		try {
			ExecutionArguments execArgs = new ExecutionArguments();
			execArgs.addParameterDefinition(Parameter.SCAN_DIRECTORY, Occurrence.REQUIRED, ValueState.VALUE_PRESENT, ValueType.STRING, ValueInstance.SINGLE);
			execArgs.addParameterDefinition(Parameter.PROPERTIES_OVERRIDE, Occurrence.REQUIRED, ValueState.VALUE_PRESENT, ValueType.STRING, ValueInstance.SINGLE);
			execArgs.parse(new String[]{Parameter.SCAN_DIRECTORY.getFlag(), "scan dir", Parameter.PROPERTIES_OVERRIDE.getFlag(), "prop override"}, true);
			assertEquals("scan dir", execArgs.getParsedParameterValue(Parameter.SCAN_DIRECTORY));
			assertEquals("prop override", execArgs.getParsedParameterValue(Parameter.PROPERTIES_OVERRIDE));
		} catch(ParameterException pe) {
			assertNull(pe);
		}
		
		try {
			ExecutionArguments execArgs = new ExecutionArguments();
			execArgs.addParameterDefinition(Parameter.SCAN_DIRECTORY, Occurrence.REQUIRED, ValueState.VALUE_PRESENT, ValueType.STRING, ValueInstance.SINGLE);
			execArgs.parse(new String[]{Parameter.SCAN_DIRECTORY.getFlag(), "scan dir", "--whatParam", "whatValue"}, true);
			assertEquals("scan dir", execArgs.getParsedParameterValue(Parameter.SCAN_DIRECTORY));
		} catch(ParameterException pe) {
			assertNotNull(pe);
			assertEquals(UnknownParameterException.class, pe.getClass());
		}
		
		try {
			ExecutionArguments execArgs = new ExecutionArguments();
			execArgs.addParameterDefinition(Parameter.SCAN_DIRECTORY, Occurrence.REQUIRED, ValueState.VALUE_PRESENT, ValueType.STRING, ValueInstance.SINGLE);
			execArgs.parse(new String[]{Parameter.SCAN_DIRECTORY.getFlag(), "scan dir", Parameter.PROPERTIES_OVERRIDE.getFlag(), "whatValue"}, true);
			assertEquals("scan dir", execArgs.getParsedParameterValue(Parameter.SCAN_DIRECTORY));
		} catch(ParameterException pe) {
			assertNotNull(pe);
			assertEquals(UnsupportedParameterException.class, pe.getClass());
		}
	}
}
