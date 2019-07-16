package controller;

import java.io.IOException;
import java.util.List;

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
	private TableView<List<Object>> table;
	
	private ServerModel serverModel;
	private MainController main;
	
	public void initialize(MainController main, ServerModel serverModel) {
		this.serverModel = serverModel;
		this.main = main;
		
		List<List<Object>> centroids;
		try {
			centroids = serverModel.getFileData();
			ObservableList<List<Object>> list = FXCollections.observableArrayList(centroids);
			System.out.println("fatto\n"+centroids);
			for(int i=0; i<centroids.get(0).size(); i++) {
				TableColumn c = new TableColumn<List<Object>, Object>();
				c.setCellValueFactory(new PropertyValueFactory<>("ok"));
				table.getColumns().add(c);
			}

			for(List<Object> l : centroids)
				table.getItems().addAll(l);
			
			table.setItems(list);
			
		} catch (IOException e) {
			new ExceptionAlert(e);
		}
		
	}
}
