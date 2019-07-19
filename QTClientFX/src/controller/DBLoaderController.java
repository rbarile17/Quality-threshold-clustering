package controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.ServerModel;
import utility.ExceptionAlert;

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

	private ServerModel serverModel;
	private LinkedList<List<List<String>>> tuples;
	private LinkedList<LinkedList<String>> centroids;
	private LinkedList<String> names;
	private LinkedList<List<Double>> distances;

	public void initialize(ServerModel serverModel, Scene homeScene) {
		this.serverModel = serverModel;
		this.homeScene = homeScene;
		
		try {
			int centroidsNumber = serverModel.getCentroidsNumber();
			centroidsLabel.setText(centroidsNumber + " " + centroidsLabel.getText());
			names = serverModel.getAttributesNames();
			centroids = serverModel.getCentroids();

			ObservableList<List<StringProperty>> list = FXCollections.observableArrayList();

			int i = 0;
			for (String s : names) {
				final int j = i;
				TableColumn<List<StringProperty>, String> c = new TableColumn<List<StringProperty>, String>(s);
				c.setCellValueFactory(data -> data.getValue().get(j));
				table.getColumns().add(c);
				i++;
			}

			for (LinkedList<String> l : centroids) {
				List<StringProperty> oList = new LinkedList<StringProperty>();
				int j = 0;
				for (String s : l) {
					oList.add(j, new SimpleStringProperty(s));
					j++;
				}
				list.add(oList);
			}
			table.setItems(list);
		} catch (IOException e) {
			new ExceptionAlert(e);
		}
	}

	public void tabularClick() {
		try {
			if(tuples == null) {
				tuples = serverModel.getData();
			}
			((ClustersTuplesController) newWindow(new Stage(), "../graphic/ClustersTuples.fxml", true))
					.initialize(names, tuples);
		} catch (IOException | NullPointerException e) {
			new ExceptionAlert(e);
		}
	}

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
			new ExceptionAlert("Connection lost", "File not saved", AlertType.ERROR);
		}
	}
	
	public void onHomeClick() {
		Stage stage = ((Stage) (home.getScene().getWindow()));
		serverModel.goBack();
		stage.setScene(homeScene);
		stage.show();
	}
	
	public void onGraphicClick(ActionEvent event) {
		try {
			if(distances == null) {
				distances = serverModel.getDistances();
			}
			((ScatterPlotController) newWindow(new Stage(), "../graphic/ScatterPlot.fxml", true)).initialize(distances);
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
			new ExceptionAlert(e);
		}
	}
}
