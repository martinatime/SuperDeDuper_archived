/**
 * The SuperDeDuper class is the main execution class of this project. Its
 * responsibilities are to set up the needed libraries, data stores, and
 * configurations. It will also read and interpret the command line options
 * and parameters.
 */
package org.superdeduper;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
//import org.superdeduper.parameters.ExecutionArguments;
//import org.superdeduper.parameters.Occurrence;
//import org.superdeduper.parameters.Parameter;
//import org.superdeduper.parameters.ValueInstance;
//import org.superdeduper.parameters.ValueState;
//import org.superdeduper.parameters.ValueType;
import org.superdeduper.parameters.exception.ParameterException;

/**
 * 
 * @author evnjones
 */
public class SuperDeDuper {
	private final static String CLASS_NAME = SuperDeDuper.class.getName();
	private final static Logger LOGGER = Logger.getLogger(CLASS_NAME);
	
	private CommandLine executionArguments = null;
	private String[] rawCommandLineInput = null;

	/**
	 * 
	 * @param args
	 */
	public SuperDeDuper(String[] args) {
		rawCommandLineInput = args;
		
		//executionArguments = new ExecutionArguments();
		//executionArguments.addParameterDefinition(Parameter.SCAN_DIRECTORY, Occurrence.REQUIRED, ValueState.VALUE_PRESENT, ValueType.STRING, ValueInstance.MULTIPLE);
		//executionArguments.addParameterDefinition(Parameter.PROPERTIES_OVERRIDE, Occurrence.OPTIONAL, ValueState.VALUE_PRESENT, ValueType.STRING, ValueInstance.SINGLE);
		//executionArguments.parse(args, false);
	}
	
	/**
	 * This is the main method's logic repository so that the main can be tested. There is an expectation
	 * that the arguments (the main's String[]) will be set in the constructor so that this method can use
	 * them. (Tentative)This method will return null if no arguments are passed in. The assumption is that
	 * triggers the help output. Other output may be returned based on the input. This is TO BE DETERMINED.
	 *  
	 * @return null if the input arguments are null/empty
	 */
	public Object run() {
		String methodName = "run";
		LOGGER.entering(CLASS_NAME, methodName);
		
		try {
			this.executionArguments = parseCommandLine();
		} catch (ParseException pe) {
			LOGGER.logp(Level.SEVERE, CLASS_NAME, methodName, "There was an exception while parsing the Command Line. Exception=" + pe);
			// skipping the rest and returning null
		}
		/*
		if (executionArguments != null) {
			if (LOGGER.isLoggable(Level.FINEST)) {
				LOGGER.logp(Level.FINEST, CLASS_NAME, methodName, "Execution arguments: {0}", executionArguments);
			}
		} else {
			if (LOGGER.isLoggable(Level.FINEST)) {
				LOGGER.logp(Level.FINEST, CLASS_NAME, methodName,"no Execution Args were passed in");
			}
		}
		*/
		LOGGER.exiting(CLASS_NAME, methodName);
		return null;
	}
	
	private CommandLine parseCommandLine() throws ParseException {
		Options commandLineOptions = new Options();
		commandLineOptions.addOption("s", "directory", true, "Scan directory - Directory to scan for audio files to process");
		commandLineOptions.addOption("p", "settings", true, "Settings override - Location and filename of the file containing setting overrides");
		CommandLineParser parser = new BasicParser();
		return parser.parse(commandLineOptions, rawCommandLineInput);
	}

	/**
	 * 
	 * @return
	 */
	public CommandLine getExecutionArguments() {
		return executionArguments;
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String methodName = "main";
		LOGGER.entering(CLASS_NAME, methodName);
		// This creates an instance for easier testing and possible
		// future embedding of this class in other projects
		SuperDeDuper runner = new SuperDeDuper(args);
		if (LOGGER.isLoggable(Level.FINE)) {
			LOGGER.logp(Level.FINE, CLASS_NAME, methodName, "About to call run with arguments=[" + StringUtils.join(args, ',') + "]");
		}
		runner.run();
		LOGGER.exiting(CLASS_NAME, methodName);
	}
}
