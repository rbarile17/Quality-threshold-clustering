package controller;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.ServerModel;
import utility.ExceptionAlert;

public class FileLoaderController extends Controller{
	@FXML
	private TableView table;
	
	private ServerModel serverModel;
	private MainController main;
	
	public void initialize(MainController main, ServerModel serverModel) {
		this.serverModel = serverModel;
		this.main = main;
		
		List<List<Object>> centroids;
		try {
			centroids = serverModel.getFileData();
			System.out.println("fatto\n"+centroids);
			for(int i=0; i<centroids.get(0).size(); i++) {
				TableColumn<List<Object>, Object> c = new TableColumn<List<Object>, Object>();
				table.getColumns().add(c);
			}
			
			for(List<Object> l : centroids) {
				table.getItems().addAll(l);
			}
		} catch (IOException e) {
			new ExceptionAlert(e);
		}
		
	}
}
