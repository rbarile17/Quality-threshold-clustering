package application;

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

public class MainController extends Controller implements Initializable {
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
	private AnchorPane anchorPane;
	
	private ServerModel serverModel;
	
	public void connectClick() {
		try {
			((SettingsController)newWindow("/application/Settings.fxml")).init(this);
		} catch(IOException | NullPointerException e) {
			new AlertException(e);
			e.printStackTrace();
		}
	}
	
	public void loadDBCLick() {
		try {
            newWindow("/application/DBLoader.fxml"); 
            serverModel.clusterDBTable(tableName.getText());
            
        } catch(IOException | NullPointerException e) {
            new AlertException(e);
        }
	}
	
	public void loadFileClick() {
		try {
			newWindow("/application/FileLoader.fxml");
			serverModel.loadFile(fileName.getText());
            
        } catch(IOException | NullPointerException e) {
            new AlertException(e);
            e.printStackTrace();
        }
	}

	public void connect(String ip, String port) throws IOException{
		this.serverModel = new ServerModel(ip,port);
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
	
	private Controller newWindow(String path) throws IOException,NullPointerException {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource(path).openStream());  
		Controller controller = (Controller)loader.getController();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
		return controller;
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Scene scene = anchorPane.getScene();
		Stage stage = (Stage) scene.getWindow();
		//stage.setOnCloseRequest((e)->{serverModel.close();});
	}
}
