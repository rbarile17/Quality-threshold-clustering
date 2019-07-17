package controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.ServerModel;
import utility.ExceptionAlert;

public class ClustersTuplesController extends Controller{
	private ServerModel serverModel;
	
	@FXML
	private TableView<List<StringProperty>> table;
	
	public void initialize(ServerModel serverModel, LinkedList<LinkedList<String>> centroids, LinkedList<String> names) {
		this.serverModel = serverModel;
		
		try {
			LinkedList<List<List<String>>> tuples = serverModel.getData();
			
			ObservableList<List<StringProperty>> list = FXCollections.observableArrayList();
			
			int i=0;
			for(String s : names) {
				final int j = i;
				TableColumn<List<StringProperty>, String> c = new TableColumn<List<StringProperty>, String>(s);
				c.setCellValueFactory(data -> data.getValue().get(j));
				table.getColumns().add(c);
				i++;
			}
						
			int k=0;
			for(List<List<String>> l : tuples) {
				List<StringProperty> oList = new LinkedList<StringProperty>();
				int j = 0;

				for(String s : centroids.get(k)) {
					oList.add(j, new SimpleStringProperty(s));
					j++;
				}	
				list.add(oList);
				
				for(List<String> example : l) {
					oList = new LinkedList<StringProperty>();
					j = 0;
					for(String s : example) {
						oList.add(j, new SimpleStringProperty(s));
						j++;
					}
					list.add(oList);
				}
				k++;
			}
			table.setItems(list);
		} catch (IOException e) {
			new ExceptionAlert(e);
		}		
	}

}
