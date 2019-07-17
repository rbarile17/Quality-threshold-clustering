package controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.ServerModel;
import utility.ExceptionAlert;

public class DBLoaderController extends Controller{
	@FXML
	private Label centroidsLabel;
	
	@FXML
	private TableView<List<StringProperty>> table;
	
	private ServerModel serverModel;
	private MainController main;
	
	public void initialize (MainController main, ServerModel serverModel) {
		this.serverModel = serverModel;
		this.main = main;
		LinkedList<LinkedList<String>> centroids;
		LinkedList<String> names;

		try {
			int centroidsNumber = serverModel.getCentroidsNumber();
			centroidsLabel.setText(centroidsNumber + " " + centroidsLabel.getText());
			names  = serverModel.getAttributesNames();
			centroids = serverModel.getCentroids();
			
			ObservableList<List<StringProperty>> list = FXCollections.observableArrayList();
			
			int i=0;
			for(String s : names) {
				final int j = i;
				TableColumn<List<StringProperty>, String> c = new TableColumn<List<StringProperty>, String>(s);
				c.setCellValueFactory(data -> data.getValue().get(j));
				table.getColumns().add(c);
				i++;
			}
						
			for(LinkedList<String> l : centroids) {
				List<StringProperty> oList = new LinkedList<StringProperty>();
				int j = 0;
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
}
