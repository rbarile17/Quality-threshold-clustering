package database;

public class DatabaseConnectionException extends Exception {
	
	public DatabaseConnectionException() {
		
	}
	
	public DatabaseConnectionException(String msg ) {
		super(msg);
	}
}
