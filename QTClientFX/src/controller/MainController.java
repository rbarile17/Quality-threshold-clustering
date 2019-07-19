package controller;

import java.io.IOException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

/**
 * Control the events of the main window of the application
 * 
 * @author Pasquale De Marinis
 * @author Roberto Barile
 * @author Sergio Caputo
 */
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

	private BooleanProperty connection;

	/**
	 * initialize the listeners and the properties of the main window
	 */
	public void initialize() {
		connection = new SimpleBooleanProperty();
		connection.addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue)
					disconnect(false);
			}

		});
	}

	/**
	 * Opens the Settings window, called when the button 'Connect' is clicked
	 */
	public void connectClick() {
		try {
			((SettingsController) newWindow(new Stage(), "../graphic/Settings.fxml")).initialize(this);
		} catch (IOException | NullPointerException e) {
			new ExceptionAlert(e);
		}
	}

	/**
	 * Takes the text in the fields Table and Radius and calls the methods to tell
	 * the server to cluster the table and opens the new scene, called when the
	 * button 'load' is clicked
	 * 
	 * @param event
	 */
	public void loadDBCLick(ActionEvent event) {
		try {
			try {
				if (serverModel.clusterDBTable(tableName.getText(), Double.parseDouble(radius.getText())) == true) {
					DBLoaderController controller;
					Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					Scene mainScene = mainStage.getScene();
					try {
						controller = ((DBLoaderController) newWindow(mainStage, "../graphic/DBLoader.fxml"));
					} catch (IOException ex) {
						new ExceptionAlert(ex);
						return;
					}
					controller.initialize(serverModel, mainScene, connection);
				}
			} catch (NumberFormatException e) {
				new ExceptionAlert("Radius error", "Radius must be a number!", AlertType.WARNING);
			}
		} catch (IOException e) {
			disconnect(false);
			new ExceptionAlert("Connecton lost", "The connection to the server has been lost", AlertType.ERROR);
		}
	}

	/**
	 * Takes the text in the fields 'file', to tell the server to load the file and
	 * opens the relative new scene,called when the button 'load' is clicked.
	 * 
	 * @param event
	 */
	public void loadFileClick(ActionEvent event) {
		try {
			FileLoaderController fileLoaderController;
			if (serverModel.loadFile(fileName.getText()) == true) {
				Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				Scene mainScene = mainStage.getScene();
				try {
					fileLoaderController = ((FileLoaderController) newWindow(mainStage, "../graphic/FileLoader.fxml"));
				} catch (IOException ex) {
					new ExceptionAlert(ex);
					return;
				}
				fileLoaderController.initialize(serverModel, mainScene);
			}
		} catch (IOException e) {
			disconnect(false);
			System.out.println(serverModel.isConnected());
			new ExceptionAlert("Connecton lost", "The connection to the server has been lost", AlertType.ERROR);
		}
	}

	/**
	 * Connects to the server specified by 'ip' and 'port' and set the label
	 * 'Disconnected' to 'Connected' in green
	 * 
	 * @param ip:   the ip of the server
	 * @param port: the port of the server
	 * @return true if the connection is completed, else false
	 * @throws IOException
	 */
	public boolean connect(String ip, int port) throws IOException {

		if (serverModel != null && serverModel.isConnected() && serverModel.getIP().equals(ip)
				&& serverModel.getPort() == port) {
			new ExceptionAlert("Same server", "You are already connected to this server!", AlertType.WARNING);
			return false;
		}

		this.serverModel = new ServerModel(ip, port);
		connected.setText("Connected");
		connected.setTextFill(Color.GREEN);
		loadDB.setDisable(false);
		loadFile.setDisable(false);
		return true;
	}

	/**
	 * Disconnect from the server and set the label to 'Disconnected' in red
	 * 
	 * @param regualClosing: true if the disconnection is regularly chosen by the
	 *                       user, false if is due to an Exception
	 */
	public void disconnect(boolean regualClosing) {
		try {
			if (serverModel.isConnected())
				serverModel.close(regualClosing);
		} catch (NullPointerException e) {
			return;
		}
		connected.setText("Disonnected");
		connected.setTextFill(Color.RED);
		loadDB.setDisable(true);
		loadFile.setDisable(true);
	}
}
