/**
 * 
 */
package org.superdeduper.parameters.exception;

/**
 * @author evnjones
 *
 */
public class UnsupportedParameterException extends ParameterException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5966173579886810974L;

	/**
	 * 
	 */
	public UnsupportedParameterException() {
	}

	/**
	 * @param message
	 */
	public UnsupportedParameterException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public UnsupportedParameterException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UnsupportedParameterException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public UnsupportedParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
