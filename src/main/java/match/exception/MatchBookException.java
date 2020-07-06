package match.exception;

public class MatchBookException extends RuntimeException {

	/**
	 * Custom Exception class
	 * which @throws MatchBookException
	 */
	private static final long serialVersionUID = 1L;

	String message;

	public MatchBookException(String message) {
		super(message);
		this.message = message;
	}

}
