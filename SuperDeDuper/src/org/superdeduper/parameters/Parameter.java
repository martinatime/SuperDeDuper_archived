/**
 * 
 */
package org.superdeduper.parameters;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author evnjones
 *
 */
public enum Parameter {
	SCAN_DIRECTORY("-s", "Scan directory", "Directory to scan for audio files to process"),
	PROPERTIES_OVERRIDE("-p", "Settings override", "Location and filename of the file containing setting overrides");
	
	/**
	 * Return the first parameter found that has a flag matching the supplied flag.
	 * 
	 * @param flag
	 * @return
	 */
	public final static Parameter getValueOfByFlag(String flag) {
		Parameter retval = null;
		
		for(Parameter param : Parameter.values()) {
			if(param.getFlag().equals(flag)) {
				retval = param;
				break;
			}
		}
		
		if(retval == null) {
			throw new IllegalArgumentException("Unable to find parameter with flag: " + flag);
		}
		
		return retval;
	}
	
	/**
	 * Return the first parameter found that has a name matching the supplied name.
	 * 
	 * @param name
	 * @return
	 */
	public final static Parameter getValueOfByName(String name) {
		Parameter retval = null;
		
		for(Parameter param : Parameter.values()) {
			if(param.getName().equals(name)) {
				retval = param;
				break;
			}
		}
		
		if(retval == null) {
			throw new IllegalArgumentException("Unable to find parameter with name: " + name);
		}
		
		return retval;
	}

	private final String flag;
	private final String name;
	private final String description;
	
	/**
	 * Create and initialize a parameter with its command-line flag representation, name, and description.
	 * 
	 * @param flag
	 * @param name
	 * @param description
	 */
	private Parameter(String flag, String name, String description) {
		this.flag = flag;
		this.name = name;
		this.description = description;
	}
	
	/**
	 * Return the parameter's flag.
	 * 
	 * @return
	 */
	public String getFlag() {
		return flag;
	}
	
	/**
	 * Return the parameter's name.
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Return the parameter's description.
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Return the string representation of the parameter.
	 * 
	 * @see java.lang.Enum#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).
				append("flag", flag).
				append("name", name).
				append("description", description).
				toString();
	}
}
