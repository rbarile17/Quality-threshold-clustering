package controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.ServerModel;
import utility.ExceptionAlert;

public class FileLoaderController extends Controller {
	@FXML
	private Label centroidsLabel;

	@FXML
	private TableView<List<StringProperty>> table;
	
	@FXML
	private ImageView home;

	private ServerModel serverModel;
	private Scene homeScene;

	public void initialize(ServerModel serverModel,Scene homeScene) {
		this.serverModel = serverModel;
		this.homeScene = homeScene;
		
		LinkedList<LinkedList<String>> centroids;
		try {
			centroids = serverModel.getCentroids();
			centroidsLabel.setText(centroids.size() + " " + centroidsLabel.getText());
			ObservableList<List<StringProperty>> list = FXCollections.observableArrayList();

			for (int iterator = 0; iterator < centroids.get(0).size(); iterator++) {
				final int j = iterator;
				TableColumn<List<StringProperty>, String> c = new TableColumn<List<StringProperty>, String>(
						"Attribute " + (iterator + 1));
				c.setCellValueFactory(data -> data.getValue().get(j));
				table.getColumns().add(c);
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
	
	public void onHomeClick() {
		Stage stage = ((Stage) (home.getScene().getWindow()));
		stage.setScene(homeScene);
		stage.show();
	}
}
