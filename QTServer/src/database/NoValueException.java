package database;

public class NoValueException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2000060478400436524L;

	public NoValueException() {
		
	}
	
	public NoValueException(String msg ) {
		super(msg);
	}
}
