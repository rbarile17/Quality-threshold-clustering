package controller;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import com.sun.glass.ui.Screen;

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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utility.Fractions;

/**
 * The controller class of the window that shows the whole tuples of the
 * clustering
 * 
 * @author Pasquale De Marinis
 * @author Roberto Barile
 * @author Sergio Caputo
 */
public class ClustersTuplesController extends Controller {
	private LinkedList<List<List<String>>> tuples;
	LinkedList<String> names;

	@FXML
	private AnchorPane pane;

	@FXML
	private Button applyFilter;

	@FXML
	private Button removeFilter;

	@FXML
	private MenuButton filter;

	@FXML
	private TableView<List<StringProperty>> table;

	/**
	 * Initialize the attributes and fills the table
	 * 
	 * @param names The names of the columns
	 * @param tuples The tuples of the clustring
	 */
	public void initialize(LinkedList<String> names, LinkedList<List<List<String>>> tuples) {
		this.tuples = tuples;
		this.names = names;

		this.setFilter();
		this.setTable();
		this.fillTable();
	}

	/**
	 * set the table listeners and adds the columns, and indexes
	 */
	private void setTable() {
		table.setRowFactory(tv -> new TableRow<List<StringProperty>>() {
			@Override
			public void updateItem(List<StringProperty> item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setStyle("");
				} else if (Double.parseDouble(item.get(1).getValue()) == 0) {
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
		int i = 2;
		for (String s : names) {
			final int j = i;
			TableColumn<List<StringProperty>, String> c = new TableColumn<List<StringProperty>, String>(s);
			c.setCellValueFactory(data -> data.getValue().get(j));
			table.getColumns().add(c);
			i++;
		}
	}

	/**
	 * Set the menu filter with the clusters
	 */
	private void setFilter() {
		for (int i = 0; i < tuples.size(); i++) {
			CheckMenuItem menuItem = new CheckMenuItem();
			menuItem.setText(String.valueOf(i));
			filter.getItems().add(menuItem);
		}
	}

	/**
	 * Fill the table with the tuples
	 */
	private void fillTable() {
		ObservableList<List<StringProperty>> list = FXCollections.observableArrayList();
		int k = 0;
		for (List<List<String>> l : tuples) {
			List<StringProperty> oList = new LinkedList<StringProperty>();

			for (List<String> example : l) {
				oList = new LinkedList<StringProperty>();
				String index = String.valueOf(k);
				oList.add(0, new SimpleStringProperty(String.valueOf(index)));
				int i = 1;
				for (String s : example) {
					oList.add(i, new SimpleStringProperty(s));
					i++;
				}
				list.add(oList);
			}

			k++;
		}
		table.setItems(list);

	}

	/**
	 * Shows the filtered tuples on the table
	 * 
	 * @param filtered The TreeSet that indicates the indixes of the tuples filtered
	 */
	private void fillTable(TreeSet<Integer> filtered) {
		ObservableList<List<StringProperty>> list = FXCollections.observableArrayList();

		int k = 0;
		for (int j : filtered) {
			List<StringProperty> oList;

			for (List<String> example : tuples.get(j)) {
				oList = new LinkedList<StringProperty>();
				oList.add(0, new SimpleStringProperty(String.valueOf(k)));
				int i = 1;
				for (String s : example) {
					oList.add(i, new SimpleStringProperty(s));
					i++;
				}
				list.add(oList);
			}
			k++;
		}
		table.setItems(list);
	}

	/**
	 * Set filters to the table taken from the filter menu when the relative button is clicked
	 */
	public void applyFilterClick() {
		TreeSet<Integer> notFiltered = filter.getItems().stream().filter(
				menuItem -> CheckMenuItem.class.isInstance(menuItem) && CheckMenuItem.class.cast(menuItem).isSelected())
				.mapToInt(menuItem -> Integer.parseInt(menuItem.getText()))
				.collect(TreeSet::new, TreeSet::add, TreeSet::addAll);

		this.fillTable(notFiltered);
	}

	/**
	 * Remove the filters from the menu when the relative button is clicked
	 */
	public void removeFilterClick() {
		this.fillTable();
		filter.getItems().stream().filter(
				menuItem -> CheckMenuItem.class.isInstance(menuItem) && CheckMenuItem.class.cast(menuItem).isSelected())
				.forEach(menuItem -> CheckMenuItem.class.cast(menuItem).setSelected(false));
	}
}
