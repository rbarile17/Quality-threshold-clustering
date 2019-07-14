package data;

public class EmptyDatasetException extends Exception{
	public EmptyDatasetException( ) {
		
	}
	
	public EmptyDatasetException(String msg ) {
		super(msg);
	}
}
