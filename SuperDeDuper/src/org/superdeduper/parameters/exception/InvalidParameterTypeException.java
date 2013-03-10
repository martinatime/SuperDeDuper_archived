/**
 * 
 */
package org.superdeduper.parameters.exception;

/**
 * @author evnjones
 *
 */
public class InvalidParameterTypeException extends ParameterException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8872272088632397734L;

	/**
	 * 
	 */
	public InvalidParameterTypeException() {
	}

	/**
	 * @param message
	 */
	public InvalidParameterTypeException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public InvalidParameterTypeException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidParameterTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public InvalidParameterTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
