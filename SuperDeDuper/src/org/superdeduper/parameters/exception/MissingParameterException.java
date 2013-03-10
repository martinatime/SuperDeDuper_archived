/**
 * 
 */
package org.superdeduper.parameters.exception;

/**
 * @author evnjones
 *
 */
public class MissingParameterException extends ParameterException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -159750097322422969L;

	/**
	 * 
	 */
	public MissingParameterException() {
	}

	/**
	 * @param message
	 */
	public MissingParameterException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public MissingParameterException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MissingParameterException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public MissingParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
