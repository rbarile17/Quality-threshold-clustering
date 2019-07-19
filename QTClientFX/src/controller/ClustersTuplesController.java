package controller;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

public class ClustersTuplesController extends Controller{
	private LinkedList<LinkedList<String>> centroids;
	private LinkedList<List<List<String>>> tuples;
	LinkedList<String> names;
	
	@FXML
	private Button applyFilter;
	
	@FXML
	private Button removeFilter;
	
	@FXML
	private MenuButton filter;
	
	@FXML
	private TableView<List<StringProperty>> table;
	
	public void initialize(LinkedList<LinkedList<String>> centroids, LinkedList<String> names, LinkedList<List<List<String>>> tuples) {
		this.centroids = centroids;
		this.tuples = tuples;
		this.names = names;
		
		table.setRowFactory(tv -> new TableRow<List<StringProperty>>() {
		    @Override
		    public void updateItem(List<StringProperty> item, boolean empty) {
		        super.updateItem(item, empty) ;
		        if (item == null) {
		            setStyle("");
		        } else if (item.get(0).getValue().charAt(item.get(0).getValue().length() - 1) == ' ') {
		            setStyle("-fx-background-color: lightblue;");
		        } else {
		            setStyle("");
		        }
		    }
		});
		
		TableColumn<List<StringProperty>, String> col = new TableColumn<List<StringProperty>, String>("Centroid Index");
		col.setCellValueFactory(data -> data.getValue().get(0));
		table.getColumns().add(col);
		col = new TableColumn<List<StringProperty>, String>("Distance");
		col.setCellValueFactory(data -> data.getValue().get(1));
		table.getColumns().add(col);
		int i=2;
		for(String s : names) {
			final int j = i;
			TableColumn<List<StringProperty>, String> c = new TableColumn<List<StringProperty>, String>(s);
			c.setCellValueFactory(data -> data.getValue().get(j));
			table.getColumns().add(c);
			i++;
		}
		
		this.setFilter();
		this.fillTable();
	}
	
	private void setFilter() {
		for(int i=0; i<centroids.size(); i++) {
			CheckMenuItem menuItem = new CheckMenuItem();
			menuItem.setText(String.valueOf(i));
			filter.getItems().add(menuItem);
		}
	}
	
	private void fillTable() {
		ObservableList<List<StringProperty>> list = FXCollections.observableArrayList();
					
		Iterator<LinkedList<String>> centroid = centroids.iterator();
		int k=0;
		for(List<List<String>> l : tuples) {
			List<StringProperty> oList = new LinkedList<StringProperty>();
			oList.add(0, new SimpleStringProperty(k+" "));
			oList.add(1, new SimpleStringProperty("0.0"));
			int i = 2;
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
	}
	
	private void fillTable(TreeSet<Integer> notFiltered) {
		ObservableList<List<StringProperty>> list = FXCollections.observableArrayList();		
					
		int k=0;
		for(int j : notFiltered) {
			List<StringProperty> oList = new LinkedList<StringProperty>();
			oList.add(0, new SimpleStringProperty(k+" "));
			oList.add(1, new SimpleStringProperty("0.0"));
			int i = 2;
			for(String s : centroids.get(j)) {
				oList.add(i, new SimpleStringProperty(s));
				i++;
			}	
			list.add(oList);
			
			for(List<String> example : tuples.get(j)) {
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
	}
	
	public void applyFilterClick() {
		TreeSet<Integer> notFiltered = 
			filter.getItems().stream()
			.filter(menuItem -> CheckMenuItem.class.isInstance(menuItem) && CheckMenuItem.class.cast(menuItem).isSelected())
			.mapToInt(menuItem -> Integer.parseInt(menuItem.getText()))
			.collect(TreeSet::new, TreeSet::add, TreeSet::addAll);
			
		this.fillTable(notFiltered);
	}
	
	public void removeFilterClick() {
		this.fillTable();
		filter.getItems().stream()
		.filter(menuItem -> CheckMenuItem.class.isInstance(menuItem) && CheckMenuItem.class.cast(menuItem).isSelected())
		.forEach(menuItem -> CheckMenuItem.class.cast(menuItem).setSelected(false));
	}
}
