package by.vasilyev.elinextTestTask.exception;

public class ConstructorNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public ConstructorNotFoundException() {
		super();
	}

	public ConstructorNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ConstructorNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConstructorNotFoundException(String message) {
		super(message);
	}

	public ConstructorNotFoundException(Throwable cause) {
		super(cause);
	}

}
