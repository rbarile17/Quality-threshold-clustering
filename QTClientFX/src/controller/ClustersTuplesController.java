package controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.ServerModel;
import utility.ExceptionAlert;

public class ClustersTuplesController extends Controller{
	private ServerModel serverModel;
	private LinkedList<LinkedList<String>> centroids;
	private LinkedList<List<List<String>>> tuples;
	
	@FXML
	private TextField filter;
	
	@FXML
	private Button applyFilter;
	
	@FXML
	private TableView<List<StringProperty>> table;
	
	public void initialize(ServerModel serverModel, LinkedList<LinkedList<String>> centroids, LinkedList<String> names) {
		this.serverModel = serverModel;
		this.centroids = centroids;
		
		table.setRowFactory(tv -> new TableRow<List<StringProperty>>() {
		    @Override
		    public void updateItem(List<StringProperty> item, boolean empty) {
		        super.updateItem(item, empty) ;
		        if (item == null) {
		            setStyle("");
		        } else if (item.get(0).getValue().equals("")) {
		            setStyle("-fx-background-color: lightblue;");
		        } else {
		            setStyle("");
		        }
		    }
		});
		
		try {
			tuples = serverModel.getData();
			
			ObservableList<List<StringProperty>> list = FXCollections.observableArrayList();
			
			TableColumn<List<StringProperty>, String> centroidIndex = new TableColumn<List<StringProperty>, String>("Centroid Index");
			centroidIndex.setCellValueFactory(data -> data.getValue().get(0));

			table.getColumns().add(centroidIndex);
			int i=1;
			for(String s : names) {
				final int j = i;
				TableColumn<List<StringProperty>, String> c = new TableColumn<List<StringProperty>, String>(s);
				c.setCellValueFactory(data -> data.getValue().get(j));
				table.getColumns().add(c);
				i++;
			}
						
			Iterator<LinkedList<String>> centroid = centroids.iterator();
			int k=0;
			for(List<List<String>> l : tuples) {
				List<StringProperty> oList = new LinkedList<StringProperty>();
				oList.add(0, new SimpleStringProperty(""));
				i = 1;
				for(String s : centroid.next()) {
					oList.add(i, new SimpleStringProperty(s));
					i++;
				}	
				list.add(oList);
				
				for(List<String> example : l) {
					oList = new LinkedList<StringProperty>();
					oList.add(0, new SimpleStringProperty(String.valueOf(k)));
					i = 1;
					for(String s : example) {
						oList.add(i, new SimpleStringProperty(s));
						i++;
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
	
	
	public void applyFilterClick() {
		if (filter.getText().equals("")) {
			new ExceptionAlert("Filter", "Filter cannot be empty", AlertType.WARNING);
			return;
		}
			
		int centroidIndex = Integer.parseInt(filter.getText());
		if(centroidIndex >= centroids.size() || centroidIndex < 0) {
			new ExceptionAlert("Filter", "This centroid doesn't exist", AlertType.WARNING);	
			return;
		}
		ObservableList<List<StringProperty>> list = FXCollections.observableArrayList();
		table.setItems(list);
		
		List<StringProperty> oList = new LinkedList<StringProperty>();
		int j = 1;
		oList.add(0, new SimpleStringProperty(""));
		for(String s : centroids.get(centroidIndex)) {
			oList.add(j, new SimpleStringProperty(s));
			j++;
		}	
		list.add(oList);
		
		for(List<String> example : tuples.get(centroidIndex)) {
			oList = new LinkedList<StringProperty>();
			oList.add(0, new SimpleStringProperty(String.valueOf(centroidIndex)));
			j = 1;
			for(String s : example) {
				oList.add(j, new SimpleStringProperty(s));
				j++;
			}
			list.add(oList);
		}
	}
}
