package utility;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

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
		this("Exception Thrown",e.getMessage(),AlertType.ERROR);
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
		Stage stage = (Stage) this.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getClassLoader().getResourceAsStream("icon.png")));
		this.setTitle(at.toString());
		this.setHeaderText(subTitle);
		this.setContentText(content);
		this.show();
	}
}
