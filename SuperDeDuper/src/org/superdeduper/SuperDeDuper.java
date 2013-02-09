/**
 * The SuperDeDuper class is the main execution class of this project. Its
 * responsibilities are to set up the needed libraries, data stores, and
 * configurations. It will also read and interpret the command line options
 * and parameters.
 */
package org.superdeduper;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SuperDeDuper {
	private final static String CLASS_NAME = SuperDeDuper.class.getName();
	private final static Logger LOGGER = Logger.getLogger(CLASS_NAME);
	
	private String[] executionArguments = null;
	
	public SuperDeDuper(String[] args) {
		executionArguments = args;
	}
	
	public Object run() {
		String methodName = "run";
		LOGGER.entering(CLASS_NAME, methodName);
		if (executionArguments != null) {
			if (LOGGER.isLoggable(Level.FINEST)) {
				// TODO: replace with StringUtils.join() once library included
				StringBuilder sb = new StringBuilder("Execution Args = [");
				for (int i = 0; i < executionArguments.length; i++) {
					sb.append(executionArguments[i]).append(", ");
				}
				sb.append("]");
				LOGGER.logp(Level.FINEST, CLASS_NAME, methodName, sb.toString());
			}
		} else {
			if (LOGGER.isLoggable(Level.FINEST)) {
				LOGGER.logp(Level.FINEST, CLASS_NAME, methodName,"no Execution Args were passed in");
			}
		}
		LOGGER.exiting(CLASS_NAME, methodName);
		return null;
	}
	
	

	public String[] getExecutionArguments() {
		return executionArguments;
	}

	public void setExecutionArguments(String[] executionArguments) {
		this.executionArguments = executionArguments;
	}

	public static void main(String[] args) {
		// This creates an instance for easier testing and possible
		// future embedding of this class in other projects
		SuperDeDuper runner = new SuperDeDuper(args);
		runner.run();
	}

}
