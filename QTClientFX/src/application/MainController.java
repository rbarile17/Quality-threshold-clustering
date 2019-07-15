package application;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController {
	@FXML
	private Button connect;
	
	@FXML
	public void connectClick() {
		try {
			Stage settings = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/application/Settings.fxml")); 
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			settings.setScene(scene);
			settings.setResizable(false);
			settings.show();
		} catch(IOException | NullPointerException e) {
			new AlertException(e);
		}
	}
}
