package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
				if (serverModel.clusterDBTable(tableName.getText(), Double.parseDouble(radius.getText())) == true)
					((DBLoaderController) newWindow(((Stage) ((Node) event.getSource()).getScene().getWindow()),
							"../graphic/DBLoader.fxml")).initialize(serverModel);
			} catch (NumberFormatException e) {
				new ExceptionAlert("Radius error", "Radius must be a number!", AlertType.WARNING);
			}
		} catch (IOException | NullPointerException e) {
			new ExceptionAlert(e);
		}
	}

	public void loadFileClick(ActionEvent event) {
		try {

			if (serverModel.loadFile(fileName.getText()) == true)
				((FileLoaderController) newWindow(((Stage) ((Node) event.getSource()).getScene().getWindow()),
						"../graphic/FileLoader.fxml")).initialize(serverModel);
		} catch (IOException | NullPointerException e) {
			new ExceptionAlert(e);
		}
	}

	public void connect(String ip, int port) throws IOException {
		this.serverModel = new ServerModel(ip, port);
		connected.setText("Connected");
		connected.setTextFill(Color.GREEN);
		loadDB.setDisable(false);
		loadFile.setDisable(false);
	}

	public void disconnect() throws IOException {
		serverModel.close();
		connected.setText("Disonnected");
		connected.setTextFill(Color.RED);
		loadDB.setDisable(true);
		loadFile.setDisable(true);
	}

}
