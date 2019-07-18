package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utility.ExceptionAlert;
import model.ServerModel;

public class MainController extends Controller {
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
			((SettingsController) newWindow(new Stage(), "../graphic/Settings.fxml")).initialize(this);
		} catch (IOException | NullPointerException e) {
			new ExceptionAlert(e);
		}
	}

	public void loadDBCLick(ActionEvent event) {
		try {
			try {
				if (serverModel.clusterDBTable(tableName.getText(), Double.parseDouble(radius.getText())) == true) {
					Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					Scene mainScene = mainStage.getScene();
					((DBLoaderController) newWindow(mainStage,"../graphic/DBLoader.fxml")).initialize(serverModel,mainScene);
				}
			} catch (NumberFormatException e) {
				new ExceptionAlert("Radius error", "Radius must be a number!", AlertType.WARNING);
			}
		} catch (IOException | NullPointerException e) {
			new ExceptionAlert(e);
		}
	}

	public void loadFileClick(ActionEvent event) {
		try {

			if (serverModel.loadFile(fileName.getText()) == true) {
				Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				Scene mainScene = mainStage.getScene();
				((FileLoaderController) newWindow(mainStage,"../graphic/FileLoader.fxml")).initialize(serverModel,mainScene);
			}
		} catch (IOException | NullPointerException e) {
			new ExceptionAlert(e);
		}
	}

	public boolean connect(String ip, int port) throws IOException {
		if(serverModel != null && serverModel.getIP().equals(ip) && serverModel.getPort() == port) {
			new ExceptionAlert("Same server","You are already connected to this server!",AlertType.WARNING);
			return false;
		}

			this.serverModel = new ServerModel(ip, port);
			connected.setText("Connected");
			connected.setTextFill(Color.GREEN);
			loadDB.setDisable(false);
			loadFile.setDisable(false);
			return true;
	}

	public void disconnect() throws IOException {
		try {
			serverModel.close();
			connected.setText("Disonnected");
			connected.setTextFill(Color.RED);
			loadDB.setDisable(true);
			loadFile.setDisable(true);
		} catch (NullPointerException e) {
			
		}
	}

}
