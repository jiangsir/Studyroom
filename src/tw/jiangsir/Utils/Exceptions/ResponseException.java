package tw.jiangsir.Utils.Exceptions;

public class ResponseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 專門回傳 Response 的錯誤訊息，適用與讓 jQuery 捕捉錯誤訊息。
	 */
	public ResponseException() {
		super();
	}

	public ResponseException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResponseException(String message) {
		super(message);
	}

	public ResponseException(Throwable cause) {
		super(cause);
	}

}
