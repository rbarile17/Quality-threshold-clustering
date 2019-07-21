package controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.ServerModel;
import utility.ExceptionAlert;

/**
 * The controller class of the DB clustering window
 * 
 * @author Pasquale De Marinis
 * @author Roberto Barile
 * @author Sergio Caputo
 */
public class DBLoaderController extends Controller {
	@FXML
	private Label centroidsLabel;

	@FXML
	private TableView<List<StringProperty>> table;

	@FXML
	private Button tabular;

	@FXML
	private Button graphic;

	@FXML
	private Button saveOnFile;

	@FXML
	private ImageView home;

	@FXML
	private TextField fileName;

	private Scene homeScene;
	private BooleanProperty connection;

	private ServerModel serverModel;
	private LinkedList<List<List<String>>> tuples;
	private LinkedList<LinkedList<String>> centroids;
	private LinkedList<String> names;
	private LinkedList<List<Double>> distances;
	private LinkedList<Double> avgDistances;

	/**
	 * initialize the class attribute and calls the method that fill the table
	 * 
	 * @param serverModel The serverModel class used to get the centroids
	 * @param homeScene   The scene to set when the home image is clicked
	 * @param connection  The BooleanProperty binded with the MainController, which
	 *                    can interrupt the connection
	 */
	public void initialize(ServerModel serverModel, Scene homeScene, BooleanProperty connection) {
		this.serverModel = serverModel;
		this.homeScene = homeScene;
		this.connection = connection;
		
		this.fillTable();
	}
	
	/**
	 * Fill the table with the data received from the server
	 */
	private void fillTable() {
		try {
			int centroidsNumber = serverModel.getCentroidsNumber();
			centroidsLabel.setText(centroidsNumber + " " + centroidsLabel.getText());
			names = serverModel.getAttributesNames();
			centroids = serverModel.getCentroids();
			avgDistances = serverModel.getAvgDistances();

			ObservableList<List<StringProperty>> list = FXCollections.observableArrayList();

			TableColumn<List<StringProperty>, String> col = new TableColumn<List<StringProperty>, String>("Avg distance");
			col.setCellValueFactory(data -> data.getValue().get(0));
			table.getColumns().add(col);
			int i = 1;
			for (String s : names) {
				final int j = i;
				TableColumn<List<StringProperty>, String> c = new TableColumn<List<StringProperty>, String>(s);
				c.setCellValueFactory(data -> data.getValue().get(j));
				table.getColumns().add(c);
				i++;
			}

			Iterator<Double> it = avgDistances.iterator();
			for (LinkedList<String> l : centroids) {
				List<StringProperty> oList = new LinkedList<StringProperty>();
				oList.add(0, new SimpleStringProperty(String.valueOf(it.next())));
				i = 1;
				for (String s : l) {
					oList.add(i, new SimpleStringProperty(s));
					i++;
				}
				list.add(oList);
			}
			table.setItems(list);
		} catch (IOException e) {
			this.connection.set(false);
			new ExceptionAlert("Connecton lost", "The connection to the server has been lost", AlertType.ERROR);
		}
	}
	
	/**
	 * Open a new window that shows the whole tuples
	 */
	public void tabularClick() {
		try {
			if (tuples == null) {
				tuples = serverModel.getData();
			}
			((ClustersTuplesController) newWindow(new Stage(), "../graphic/ClustersTuples.fxml", true,"QT Clustering - Tuples table"))
					.initialize(names, tuples);
		} catch (IOException | NullPointerException e) {
			new ExceptionAlert("Connecton lost", "The connection to the server has been lost", AlertType.ERROR);
		}
	}

	/**
	 * Get the string from the text field and calls the ServerModel method to save
	 * the file on the server
	 * 
	 * @throws ClassNotFoundException
	 */
	public void onSaveClick() throws ClassNotFoundException {
		String name = fileName.getText();
		if (name.equals("")) {
			new ExceptionAlert("File name", "File name cannot be empty", AlertType.WARNING);
			return;
		}
		try {
			String answer = serverModel.saveFile(name);
			if (answer.equals("")) {
				new ExceptionAlert("Saved!", "File successfully saved", AlertType.INFORMATION);
			}
		} catch (IOException e) {
			connection.set(false);
			new ExceptionAlert("Connection lost", "File not saved", AlertType.ERROR);
		}
	}

	/**
	 * Goes back to the main window
	 */
	public void onHomeClick() {
		Stage stage = ((Stage) (home.getScene().getWindow()));
		serverModel.goBack();
		stage.setScene(homeScene);
		stage.setResizable(false);
		stage.sizeToScene();
		stage.show();
	}

	/**
	 * Opens the new window with the graphic representation of the clustering
	 * 
	 * @param event
	 */
	public void onGraphicClick(ActionEvent event) {
		try {
			if (distances == null) {
				distances = serverModel.getDistances();
			}
			((ScatterPlotController) newWindow(new Stage(), "../graphic/ScatterPlot.fxml", true,"QT Clustering - Tuples chart")).initialize(distances);
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
			new ExceptionAlert(e);
		}
	}
}
