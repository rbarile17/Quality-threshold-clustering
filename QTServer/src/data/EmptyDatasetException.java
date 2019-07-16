package data;

public class EmptyDatasetException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2529580605740729274L;

	public EmptyDatasetException( ) {
		
	}
	
	public EmptyDatasetException(String msg ) {
		super(msg);
	}
}
