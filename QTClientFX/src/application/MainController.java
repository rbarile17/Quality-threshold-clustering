package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	private Button loadDB;
	
	@FXML
	private Button loadFile;
	
	@FXML
	private TextField fileName;
	
	private Socket server;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
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
	
	public void loadDBCLick() {
		try {
            Stage settings = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/application/DBLoader.fxml")); 
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            settings.setScene(scene);
            settings.setResizable(false);
            settings.show();
            
        } catch(IOException | NullPointerException e) {
            new AlertException(e);
        }
	}
	
	public void loadFileClick() {
		try {
            Stage settings = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/application/FileLoader.fxml")); 
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            settings.setScene(scene);
            settings.setResizable(false);
            settings.show();
            out.writeObject(3);
            out.writeObject(fileName.getText());
            
        } catch(IOException | NullPointerException e) {
            new AlertException(e);
        }
	}

	public void setServer(Socket server) {
		this.server = server;
		try {
			out = new ObjectOutputStream(server.getOutputStream());
			in = new ObjectInputStream(server.getInputStream());
			connected.setText("Connected");
			connected.setTextFill(Color.GREEN);
			loadDB.setDisable(false);
			loadFile.setDisable(false);
		} catch (IOException e) {
			new AlertException(e);
		}
	}
}
