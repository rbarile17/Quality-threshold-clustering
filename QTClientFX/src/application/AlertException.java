package application;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.scene.control.Alert;

public class AlertException extends Alert {

	public AlertException(Exception e) {
		super(AlertType.ERROR);
		
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        this.setTitle("Exception");
        this.setHeaderText("Exception Thrown");
        this.setContentText(e.toString());
        this.show();
	}

}
