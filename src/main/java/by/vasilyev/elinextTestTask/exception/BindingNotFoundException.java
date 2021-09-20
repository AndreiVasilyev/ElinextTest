package by.vasilyev.elinextTestTask.exception;

public class BindingNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BindingNotFoundException() {
		super();
	}

	public BindingNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BindingNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public BindingNotFoundException(String message) {
		super(message);
	}

	public BindingNotFoundException(Throwable cause) {
		super(cause);
	}

}
