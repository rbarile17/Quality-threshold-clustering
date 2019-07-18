package database;

public class EmptySetException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5833380353580231430L;

	public EmptySetException() {

	}

	public EmptySetException(String msg) {
		super(msg);
	}
}
