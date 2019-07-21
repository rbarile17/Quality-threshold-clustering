package utility;

import javafx.scene.control.Alert;

/**
 * Generate and shows an Alert when constructed
 * 
 * @author Pasquale De Marinis
 * @author Roberto Barile
 * @author Sergio Caputo
 */
public class ExceptionAlert extends Alert {

	/**
	 * Construct and shows the alert with the message of the Exception e
	 * 
	 * @param e The exception shown by the Alert
	 */
	public ExceptionAlert(Exception e) {
		super(AlertType.ERROR);

		this.setTitle("Exception");
		this.setHeaderText("Exception Thrown");
		this.setContentText(e.toString());
		this.show();
	}

	/**
	 * Construct and shows the alert set with the parameters
	 * 
	 * @param subTitle The string that will be used as subtitle
	 * @param content The string that will be used as text of the Alert
	 * @param at The AlertType
	 */
	public ExceptionAlert(String subTitle, String content, AlertType at) {
		super(at);
		this.setTitle(at.toString());
		this.setHeaderText(subTitle);
		this.setContentText(content);
		this.show();
	}
	
	/**
	 * Construct and shows the alert set with the parameters
	 * 
	 * @param subTitle The string that will be used as subtitle
	 * @param Exception The exception that will be used as text of the Alert
	 * @param at The AlertType
	 */
	public ExceptionAlert(String subTitle, Exception e, AlertType at) {
		super(at);
		this.setTitle(at.toString());
		this.setHeaderText(subTitle);
		this.setContentText(e.getMessage());
		this.show();
	}

}
