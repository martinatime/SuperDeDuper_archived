/**
 * 
 */
package org.superdeduper.parameters.exception;

/**
 * @author evnjones
 *
 */
public class ParameterException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7159533158178121109L;

	/**
	 * 
	 */
	public ParameterException() {
	}

	/**
	 * @param message
	 */
	public ParameterException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ParameterException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ParameterException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
