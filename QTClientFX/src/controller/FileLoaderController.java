package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ServerModel;
import utility.ExceptionAlert;

public class FileLoaderController extends Controller{
	@FXML
	private TableView<List<ObjectProperty>> table;
	
	private ServerModel serverModel;
	private MainController main;
	
	public void initialize(MainController main, ServerModel serverModel) {
		this.serverModel = serverModel;
		this.main = main;
		
		ArrayList<ArrayList<Object>> centroids;
		try {
			centroids = serverModel.getCentroids();
			ObservableList<List<ObjectProperty>> list = FXCollections.observableArrayList();

			for(int iterator=0; iterator<centroids.get(0).size(); iterator++) {
				final int j = iterator;
				TableColumn<List<ObjectProperty>, Object> c = new TableColumn<>("Attribute " + (iterator+1));
				c.setCellValueFactory(data -> data.getValue().get(j));
				table.getColumns().add(c);
			}
						
			for(ArrayList<Object> l : centroids) {
				List<ObjectProperty> oList = new ArrayList();
				int j = 0;
				for(Object o : l) {
					oList.add(j, new SimpleObjectProperty(o));
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
