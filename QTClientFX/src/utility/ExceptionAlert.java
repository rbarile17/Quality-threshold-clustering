package utility;
import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.scene.control.Alert;

public class ExceptionAlert extends Alert {

	public ExceptionAlert(Exception e) {
		super(AlertType.ERROR);
		
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        this.setTitle("Exception");
        this.setHeaderText("Exception Thrown");
        this.setContentText(e.toString());
        this.show();
	}

	public ExceptionAlert(String s) {
		super(AlertType.ERROR);
		
        this.setTitle("Exception");
        this.setHeaderText("Error");
        this.setContentText(s);
        this.show();
	}
	
	public ExceptionAlert(String s, AlertType at) {
		super(at);
		
        this.setTitle("Exception");
        this.setHeaderText("Message");
        this.setContentText(s);
        this.show();
	}
	
}
