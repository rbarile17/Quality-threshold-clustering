package data;

/**
 * Defines a class that handles a controlled exception when an empty data set is returned<br>
 * @author Pasquale De Marinis
 * @author Roberto Barile
 * @author Sergio Caputo
 */
public class EmptyDatasetException extends Exception {
	
	private static final long serialVersionUID = 2529580605740729274L;

	public EmptyDatasetException() {

	}

	/**
	 * @param msg message of the exception
	 */
	public EmptyDatasetException(String msg) {
		super(msg);
	}
}
