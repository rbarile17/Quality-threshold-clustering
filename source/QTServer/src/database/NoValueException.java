package database;

/**
 * Defines a class that handles a controlled exception when a value in the resulset is not found<br>
 * @author Pasquale De Marinis
 * @author Roberto Barile
 * @author Sergio Caputo
 */
public class NoValueException extends Exception {

	private static final long serialVersionUID = -2000060478400436524L;

	public NoValueException() {

	}

	/**
	 * @param msg message of the exception
	 */
	public NoValueException(String msg) {
		super(msg);
	}
}
