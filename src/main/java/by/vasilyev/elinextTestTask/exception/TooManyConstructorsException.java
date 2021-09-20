package by.vasilyev.elinextTestTask.exception;

public class TooManyConstructorsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TooManyConstructorsException() {
		super();
	}

	public TooManyConstructorsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TooManyConstructorsException(String message, Throwable cause) {
		super(message, cause);
	}

	public TooManyConstructorsException(String message) {
		super(message);
	}

	public TooManyConstructorsException(Throwable cause) {
		super(cause);
	}
}
