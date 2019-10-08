package mining;

/**
 * Defines a class that handles a controlled exception when QTminer generates only one cluster<br>
 * @author Pasquale De Marinis
 * @author Roberto Barile
 * @author Sergio Caputo
 */
public class ClusteringRadiusException extends Exception {
	
	private static final long serialVersionUID = 4917497189702825018L;

	public ClusteringRadiusException() {

	}

	/**
	 * @param msg message of the exception
	 */
	public ClusteringRadiusException(String msg) {
		super(msg);
	}

}
