package database;

public class DatabaseConnectionException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6312916993042372641L;

	public DatabaseConnectionException() {
		
	}
	
	public DatabaseConnectionException(String msg ) {
		super(msg);
	}
}
