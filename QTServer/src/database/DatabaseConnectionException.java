package database;

/**
 * Defines a class that handles a controlled exception when a connection with DB fails<br>
 * @author Pasquale De Marinis
 * @author Roberto Barile
 * @author Sergio Caputo
 */
public class DatabaseConnectionException extends Exception {

	private static final long serialVersionUID = 6312916993042372641L;

	public DatabaseConnectionException() {

	}

	/**
	 * @param msg message of the exception
	 */
	public DatabaseConnectionException(String msg) {
		super(msg);
	}
}
