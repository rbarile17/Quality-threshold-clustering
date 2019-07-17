package controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.ServerModel;
import utility.ExceptionAlert;

public class DBLoaderController extends Controller{
	@FXML
	private Label centroidsLabel;
	
	@FXML
	private TableView<List<StringProperty>> table;
	
	@FXML
	private Button tabular;
	
	private ServerModel serverModel;
	private LinkedList<LinkedList<String>> centroids;
	
	public void initialize (ServerModel serverModel) {
		this.serverModel = serverModel;
		LinkedList<String> names;

		try {
			int centroidsNumber = serverModel.getCentroidsNumber();
			centroidsLabel.setText(centroidsNumber + " " + centroidsLabel.getText());
			names  = serverModel.getAttributesNames();
			centroids = serverModel.getCentroids();
			
			ObservableList<List<StringProperty>> list = FXCollections.observableArrayList();
			
			TableColumn<List<StringProperty>, String> buttons = new TableColumn<List<StringProperty>, String>();
			buttons.setCellValueFactory(data -> data.getValue().get(0));
			table.getColumns().add(buttons);
			int i=1;
			for(String s : names) {
				final int j = i;
				TableColumn<List<StringProperty>, String> c = new TableColumn<List<StringProperty>, String>(s);
				c.setCellValueFactory(data -> data.getValue().get(j));
				table.getColumns().add(c);
				i++;
			}
						
			for(LinkedList<String> l : centroids) {
				List<StringProperty> oList = new LinkedList<StringProperty>();
				oList.add(0, new SimpleStringProperty("View tuples"));
				int j = 1;
				for(String s : l) {
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
			((ClustersTuplesController)newWindow(new Stage(), "../graphic/ClustersTuples.fxml")).initialize(serverModel, centroids);
		} catch (IOException | NullPointerException e) {
			new ExceptionAlert(e);
		}
	}
}
