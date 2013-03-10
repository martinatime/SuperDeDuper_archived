/**
 * 
 */
package org.superdeduper.parameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.superdeduper.parameters.exception.DuplicateParameterException;
import org.superdeduper.parameters.exception.InvalidParameterTypeException;
import org.superdeduper.parameters.exception.MissingParameterException;
import org.superdeduper.parameters.exception.UnknownParameterException;
import org.superdeduper.parameters.exception.UnsupportedParameterException;

/**
 * @author evnjones
 *
 */
public class ExecutionArguments {
	private final static String CLASS_NAME = ExecutionArguments.class.getName();
	private final static Logger LOGGER = Logger.getLogger(CLASS_NAME);
	private Map<Parameter, ParameterDefinition> parameterDefinitions = new HashMap<Parameter, ParameterDefinition>();
	private Map<Parameter, List<String>> parsedParameters = new HashMap<Parameter, List<String>>();
	
	/**
	 * Container class to hold a full parameter definition
	 * 
	 * @author evnjones
	 */
	private class ParameterDefinition {
		private Parameter parameter = null;
		private Occurrence occurrence = null;
		private ValueState valueState = null;
		private ValueType valueType = null;
		private ValueInstance valueInstance = null;
		
		/**
		 * 
		 * @param parameter
		 * @param occurrence
		 * @param valueState
		 * @param valueType
		 * @param valueInstance
		 */
		public ParameterDefinition(Parameter parameter, Occurrence occurrence, ValueState valueState, ValueType valueType, ValueInstance valueInstance) {
			setParameter(parameter);
			setOccurrence(occurrence);
			setValueState(valueState);
			setValueType(valueType);
			setValueInstance(valueInstance);
		}

		/**
		 * @return the parameter
		 */
		public Parameter getParameter() {
			return parameter;
		}

		/**
		 * @param parameter the parameter to set
		 */
		public void setParameter(Parameter parameter) {
			this.parameter = parameter;
		}

		/**
		 * @return the occurrence
		 */
		public Occurrence getOccurrence() {
			return occurrence;
		}

		/**
		 * @param occurrence the occurrence to set
		 */
		public void setOccurrence(Occurrence occurrence) {
			this.occurrence = occurrence;
		}

		/**
		 * @return the valueState
		 */
		public ValueState getValueState() {
			return valueState;
		}

		/**
		 * @param valueState the valueState to set
		 */
		public void setValueState(ValueState valueState) {
			this.valueState = valueState;
		}

		/**
		 * @return the valueType
		 */
		public ValueType getValueType() {
			return valueType;
		}

		/**
		 * @param valueType the valueType to set
		 */
		public void setValueType(ValueType valueType) {
			this.valueType = valueType;
		}

		/**
		 * @return the valueInstance
		 */
		public ValueInstance getValueInstance() {
			return valueInstance;
		}

		/**
		 * @param valueInstance the valueInstance to set
		 */
		public void setValueInstance(ValueInstance valueInstance) {
			this.valueInstance = valueInstance;
		}
		
		/**
		 * 
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return new ToStringBuilder(this).
					append("parameter", parameter).
					append("occurrence", occurrence).
					append("valueState", valueState).
					append("valueType", valueType).
					append("valueInstance", valueInstance).
					toString();
		}
	}
	
	/**
	 * Add a parameter definition to check for during the command line parsing.
	 * 
	 * @param parameter
	 * @param occurrence
	 * @param valueState
	 * @param valueType
	 * @param valueInstance
	 */
	public void addParameterDefinition(Parameter parameter, Occurrence occurrence, ValueState valueState, ValueType valueType, ValueInstance valueInstance) {
		parameterDefinitions.put(parameter, new ParameterDefinition(parameter, occurrence, valueState, valueType, valueInstance));
	}
	
	/**
	 * Parse the supplied command-line arguments using the set parameter definitions.  If the strict parameter is true, 
	 * any command-line parameter that doesn't exist in the parameter definitionsz (by match on flag), an exception is thrown, 
	 * otherwise a warning is logged and parsing continues. 
	 * 
	 * All parsed values are stored and returned as strings (parameters that do not take a value are stored with a value of empty string).
	 * The value type specified in the parameter definitions is used only to validate the parsed value.
	 * 
	 * @param args
	 * @param strict
	 * @throws UnknownParameterException
	 * @throws UnsupportedParameterException
	 * @throws InvalidParameterTypeException
	 * @throws DuplicateParameterException
	 * @throws MissingParameterException
	 */
	public void parse(String[] args, boolean strict) throws UnknownParameterException, UnsupportedParameterException, InvalidParameterTypeException, DuplicateParameterException, MissingParameterException {
		final String methodName = "parse()";
		
		LOGGER.entering(CLASS_NAME, methodName);
		
		// do some work only if there are parameter definitions and command line arguments present
		if(!parameterDefinitions.isEmpty()) {
			if(args != null && args.length > 0) {
				Parameter param = null;
				ParameterDefinition paramDef = null;
				for(int i = 0; i < args.length; i++) {
					// lookup the parameter flag
					try {
						param = Parameter.getValueOfByFlag(args[i]);
					} catch(IllegalArgumentException iae) {
						param = null;
						handleUnknownParameter(args[i], strict);
					}
					
					// continue if a parameter was found
					if(param != null) {
						if((paramDef = parameterDefinitions.get(param)) != null) {
							i = processParameter(paramDef, args, i);
						} else {
							handleUnsupportedParameter(args[i], strict);
						}
					}
				}
			}
			
			// check for any required parameter definitions that a parameter was not found for
			for(Map.Entry<Parameter, ParameterDefinition> entry : parameterDefinitions.entrySet()) {
				if(entry.getValue().getOccurrence() == Occurrence.REQUIRED && !parsedParameters.containsKey(entry.getKey())) {
					throw new MissingParameterException("Missing required parameter: " + entry.getKey());
				}
			}
		}
		
		LOGGER.exiting(CLASS_NAME, methodName);
	}
	
	/**
	 * Process the found parameter using the matched definition.  Performs (if required) a duplicate check and value check before adding
	 * the parameter and it's value to the parsed parameters map.
	 * 
	 * @param paramDef
	 * @param args
	 * @param argsIdx
	 * @return
	 * @throws DuplicateParameterException
	 * @throws InvalidParameterTypeException
	 */
	private int processParameter(ParameterDefinition paramDef, String[] args, int argsIdx) throws DuplicateParameterException, InvalidParameterTypeException {
		final String methodName = "processParameter()";
		int retval = argsIdx;
		
		LOGGER.entering(CLASS_NAME, methodName);
		
		// check the number of instances already encountered, if needed
		List<String> values = parsedParameters.get(paramDef.getParameter());
		if(paramDef.getValueInstance() == ValueInstance.SINGLE && values != null) {
			throw new DuplicateParameterException("Duplicate parameter found: " + paramDef.getParameter());
		}
		
		// get the value of this parameter, if needed
		String value = null;
		if(paramDef.getValueState() == ValueState.VALUE_PRESENT) {
			value = args[++retval];
			
			// verify the type of the value
			switch(paramDef.getValueType()) {
				case INTEGER:
					try {
						Integer.valueOf(value);
					} catch(Exception e) {
						throw new InvalidParameterTypeException("Exception encountered validating " + value + " as an integer", e);
					}
					break;
				case LONG:
					try {
						Long.valueOf(value);
					} catch(Exception e) {
						throw new InvalidParameterTypeException("Exception encountered validating " + value + " as a long", e);
					}
					break;
				case DOUBLE:
					try {
						Double.valueOf(value);
					} catch(Exception e) {
						throw new InvalidParameterTypeException("Exception encountered validating " + value + " as a double", e);
					}
					break;
				case STRING:
				case NOT_SPECIFIED:
				default:
					if(LOGGER.isLoggable(Level.FINE)) {
						LOGGER.logp(Level.FINE, CLASS_NAME, methodName, "Skipping validation for type: {0}", paramDef.getValueType());
					}
					break;
			}
		} else {
			value = "";
		}
		
		// add the value to parsed parameters map
		if(values == null) {
			values = new ArrayList<String>();
			parsedParameters.put(paramDef.getParameter(), values);
		}
		values.add(value);
		
		LOGGER.exiting(CLASS_NAME, methodName);
		
		return retval;
	}
	
	/**
	 * Perform error handling when an unknown parameter is found.
	 * 
	 * @param paramArg
	 * @param strict
	 * @throws UnknownParameterException
	 */
	private void handleUnknownParameter(String paramArg, boolean strict) throws UnknownParameterException {
		final String methodName = "handleUnknownParameter()";
		
		LOGGER.entering(CLASS_NAME, methodName);
		
		if(!strict) {
			if(LOGGER.isLoggable(Level.WARNING)) {
				LOGGER.logp(Level.WARNING, CLASS_NAME, methodName, "Unknown parameter: {0}", paramArg);
			}
		} else {
			throw new UnknownParameterException("Unknwon parameter: " + paramArg);
		}
		
		LOGGER.exiting(CLASS_NAME, methodName);
	}
	
	/**
	 * Perform error handling when an unsupproted parameter is found.
	 * 
	 * @param paramArg
	 * @param strict
	 * @throws UnsupportedParameterException
	 */
	private void handleUnsupportedParameter(String paramArg, boolean strict) throws UnsupportedParameterException {
		final String methodName = "handleUnsupportedParameter()";
		
		LOGGER.entering(CLASS_NAME, methodName);
		
		if(!strict) {
			if(LOGGER.isLoggable(Level.WARNING)) {
				LOGGER.logp(Level.WARNING, CLASS_NAME, methodName, "Unsupported parameter: {0}", paramArg);
			}
		} else {
			throw new UnsupportedParameterException("Unsupported parameter: " + paramArg);
		}
		
		
		LOGGER.exiting(CLASS_NAME, methodName);
	}
	
	/**
	 * Return the set of parsed parameters.
	 * 
	 * @return
	 */
	public Map<Parameter, List<String>> getParsedParameters() {
		return parsedParameters;
	}
	
	/**
	 * Return a flag indicating if the supplied parameter exists in the set of parsed parameters.
	 * 
	 * @param param
	 * @return
	 */
	public boolean isParsedParameter(Parameter param) {
		return parsedParameters.containsKey(param);
	}
	
	/**
	 * Return the specified parsed parameter's value.  If the specified parameter supports multiple instances, 
	 * returns the first instance. 
	 * 
	 * @param param
	 * @return
	 */
	public String getParsedParameterValue(Parameter param) {
		String retval = null;
		
		List<String> values = getParsedParameterValues(param);
		if(values != null && !values.isEmpty()) {
			retval = values.get(0);
		}
		
		return retval;
	}
	
	/**
	 * Return all the specified parsed parameter's values.
	 * 
	 * @param param
	 * @return
	 */
	public List<String> getParsedParameterValues(Parameter param) {
		List<String> retval = null;
		
		if(parameterDefinitions.containsKey(param)) {
			List<String> values = parsedParameters.get(param);
			if(values != null && !values.isEmpty()) {
				retval = values;
			}
		}
		
		return retval;
	}
}
