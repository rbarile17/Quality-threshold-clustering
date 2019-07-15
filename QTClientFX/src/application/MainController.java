package application;

import java.io.IOException;
import java.net.Socket;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainController {
	@FXML
	private Button connect;
	
	@FXML
	private Label connected;
	
	@FXML
	private TextField loadFromDatabase;
	
	@FXML
	private TextField loadFromFile;
	
	private Socket server;
	
	@FXML
	public void connectClick() {
		try {
			Stage settings = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/application/Settings.fxml").openStream());  
			SettingsController settingsController = (SettingsController)loader.getController();
			settingsController.init(this);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			settings.setScene(scene);
			settings.setResizable(false);
			settings.show();
		} catch(IOException | NullPointerException e) {
			new AlertException(e);
			e.printStackTrace();
		}
	}
	

	public void setServer(Socket server) {
		this.server = server;
		connected.setText("Connected");
		connected.setTextFill(Color.GREEN);
		loadFromFile.setEditable(true);
		loadFromFile.setEditable(true);
	}
}
