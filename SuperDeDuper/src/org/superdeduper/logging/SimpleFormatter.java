/**
 * 
 */
package org.superdeduper.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.LogRecord;

/**
 * @author evnjones
 *
 */
public class SimpleFormatter extends java.util.logging.Formatter {
	private final static String NEW_LINE = System.getProperty("line.separator", "\n");
	private final static Map<String, String> LEVELS = new HashMap<String, String>();
	private final SimpleDateFormat DATE_FMT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
	
	static {
		LEVELS.put("ALL",     "ALL     ");
		LEVELS.put("CONFIG",  "CONFIG  ");
		LEVELS.put("SEVERE",  "SEVERE  ");
		LEVELS.put("WARNING", "WARNING ");
		LEVELS.put("INFO",    "INFO    ");
		LEVELS.put("FINE",    "FINE    ");
		LEVELS.put("FINER",   "FINER   ");
		LEVELS.put("FINEST",  "FINEST  ");
	}
	
	/**
	 * @see java.util.logging.Formatter#format(java.util.logging.LogRecord)
	 */
	@Override
	public String format(LogRecord record) {
		StringBuilder sb = new StringBuilder();
		
		// [2012-09-05 23:23:23.000] thread-id loggername level class method message
		sb.append("[").append(DATE_FMT.format(new Date(record.getMillis()))).append("] ");
		//sb.append(formatThreadId(record.getThreadID())).append(" ");
		sb.append(formatLoggerName(record.getLoggerName())).append(" ");
		sb.append(formatLevel(record.getLevel().getName())).append(" ");
		sb.append(record.getSourceClassName()).append(" ");
		sb.append(record.getSourceMethodName()).append(" ");
		
		if(record.getResourceBundle() == null) {
			if(record.getParameters() == null) {
				sb.append(record.getMessage());
			} else {
				sb.append(MessageFormat.format(record.getMessage(), record.getParameters()));
			}
		} else {
			if(record.getParameters() == null) {
				sb.append(record.getResourceBundle().getString(record.getMessage()));
			} else {
				sb.append(MessageFormat.format(record.getResourceBundle().getString(record.getMessage()), record.getParameters()));
			}
		}
		
		if(record.getThrown() != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			record.getThrown().printStackTrace(pw);
			sb.append(NEW_LINE).append(sw.toString());
		}
		
		sb.append(NEW_LINE);
		
		return sb.toString();
	}
	
	/**
	 * 
	 * @param loggerName
	 * @return
	 */
	private String formatLoggerName(String loggerName) {
		String retval = null;
		
		String[] components = loggerName.split("\\.");
		if(components == null || components.length == 0) {
			retval = loggerName;
		} else {
			if(components.length == 1) {
				retval = components[0];
			} else {
				retval = components[components.length - 2];
			}
		}
		
		return forceLength(retval, 10);
	}
	
	/**
	 * 
	 * @param data
	 * @param length
	 * @return
	 */
	private String forceLength(String data, int length) {
		StringBuilder sb = new StringBuilder(data);
		
		if(sb.length() < length) {
			int padLength = length - sb.length();
			for(int i = 0; i < padLength; i++) {
				sb.append(" ");
			}
		} else if(sb.length() > length) {
			sb.setLength(length);
		}
		
		return sb.toString();
	}
	
	/**
	 * 
	 * @param level
	 * @return
	 */
	private String formatLevel(String level) {
		String retval = LEVELS.get(level);
		return (retval == null ? level : retval);
	}
}
