/**
 * The SuperDeDuper class is the main execution class of this project. Its
 * responsibilities are to set up the needed libraries, data stores, and
 * configurations. It will also read and interpret the command line options
 * and parameters.
 */
package org.superdeduper;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.superdeduper.parameters.ExecutionArguments;
import org.superdeduper.parameters.Occurrence;
import org.superdeduper.parameters.Parameter;
import org.superdeduper.parameters.ValueInstance;
import org.superdeduper.parameters.ValueState;
import org.superdeduper.parameters.ValueType;
import org.superdeduper.parameters.exception.ParameterException;

/**
 * 
 * @author evnjones
 */
public class SuperDeDuper {
	private final static String CLASS_NAME = SuperDeDuper.class.getName();
	private final static Logger LOGGER = Logger.getLogger(CLASS_NAME);
	
	private ExecutionArguments executionArguments = null;

	/**
	 * 
	 * @param args
	 */
	public SuperDeDuper(String[] args) throws ParameterException {
		executionArguments = new ExecutionArguments();
		executionArguments.addParameterDefinition(Parameter.SCAN_DIRECTORY, Occurrence.REQUIRED, ValueState.VALUE_PRESENT, ValueType.STRING, ValueInstance.MULTIPLE);
		executionArguments.addParameterDefinition(Parameter.PROPERTIES_OVERRIDE, Occurrence.OPTIONAL, ValueState.VALUE_PRESENT, ValueType.STRING, ValueInstance.SINGLE);
		executionArguments.parse(args, false);
	}
	
	/**
	 * 
	 * @return
	 */
	public Object run() {
		String methodName = "run";
		LOGGER.entering(CLASS_NAME, methodName);
		if (executionArguments != null) {
			if (LOGGER.isLoggable(Level.FINEST)) {
				LOGGER.logp(Level.FINEST, CLASS_NAME, methodName, "Execution arguments: {0}", executionArguments);
			}
		} else {
			if (LOGGER.isLoggable(Level.FINEST)) {
				LOGGER.logp(Level.FINEST, CLASS_NAME, methodName,"no Execution Args were passed in");
			}
		}
		LOGGER.exiting(CLASS_NAME, methodName);
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public ExecutionArguments getExecutionArguments() {
		return executionArguments;
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String methodName = "main";
		// This creates an instance for easier testing and possible
		// future embedding of this class in other projects
		SuperDeDuper runner = null;
		try {
			runner = new SuperDeDuper(args);
			runner.run();
		} catch(ParameterException pe) {
			if(LOGGER.isLoggable(Level.SEVERE)) {
				LOGGER.logp(Level.SEVERE, CLASS_NAME, methodName, pe.getMessage(), pe);
			}
		}
	}
}
