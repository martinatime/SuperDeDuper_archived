/**
 * 
 */
package org.superdeduper.parameters.exception;

/**
 * @author evnjones
 *
 */
public class DuplicateParameterException extends ParameterException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1242550243637044539L;

	/**
	 * 
	 */
	public DuplicateParameterException() {
	}

	/**
	 * @param message
	 */
	public DuplicateParameterException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public DuplicateParameterException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DuplicateParameterException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public DuplicateParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
