/**
 * 
 */
package org.superdeduper.parameters.exception;

/**
 * @author evnjones
 *
 */
public class UnknownParameterException extends ParameterException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4871107381715594207L;

	/**
	 * 
	 */
	public UnknownParameterException() {
	}

	/**
	 * @param message
	 */
	public UnknownParameterException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public UnknownParameterException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UnknownParameterException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public UnknownParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
