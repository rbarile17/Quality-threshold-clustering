package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utility.ExceptionAlert;
import model.ServerModel;

public class MainController extends Controller{
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
	
	@FXML
	private TextField tableName;
	
	@FXML
	private TextField radius;
	
	@FXML
	private AnchorPane anchorPane;
	
	private ServerModel serverModel;
	
	public void connectClick() {
		try {
			((SettingsController)newWindow("../graphic/Settings.fxml")).initialize(this);
		} catch(IOException | NullPointerException e) {
			new ExceptionAlert(e);
			e.printStackTrace();
		}
	}
	
	public void loadDBCLick() {
		try {
            serverModel.clusterDBTable(tableName.getText(), Double.parseDouble(radius.getText()));
            ((DBLoaderController)newWindow("../graphic/DBLoader.fxml")).initialize(this,serverModel); 
            
        } catch(IOException | NullPointerException e) {
            new ExceptionAlert(e);
        }
	}
	
	public void loadFileClick() {
		try {
			serverModel.loadFile(fileName.getText());
			((FileLoaderController)newWindow("../graphic/FileLoader.fxml")).initialize(this, serverModel);
        } catch(IOException | NullPointerException e) {
            new ExceptionAlert(e);
        }
	}

	public void connect(String ip, String port) throws IOException{
		this.serverModel = new ServerModel(ip, port);
		connected.setText("Connected");
		connected.setTextFill(Color.GREEN);
		loadDB.setDisable(false);
		loadFile.setDisable(false);
	}
	
	public void disconnect() throws IOException{
		serverModel.close();
		connected.setText("Disonnected");
		connected.setTextFill(Color.RED);
		loadDB.setDisable(true);
		loadFile.setDisable(true);
	}
	
	private Controller newWindow(String path) throws IOException, NullPointerException {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource(path).openStream());  
		Controller controller = (Controller)loader.getController();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("../graphic/application.css").toExternalForm());
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
		return controller;
	}
}
