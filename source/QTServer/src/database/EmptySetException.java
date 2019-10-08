package database;

/**
 * Defines a class that handles a controlled exception when an empty resultset is returned<br>
 * @author Pasquale De Marinis
 * @author Roberto Barile
 * @author Sergio Caputo
 */
public class EmptySetException extends Exception {

	private static final long serialVersionUID = -5833380353580231430L;

	public EmptySetException() {

	}

	/**
	 * @param msg message of the exception
	 */
	public EmptySetException(String msg) {
		super(msg);
	}
}
