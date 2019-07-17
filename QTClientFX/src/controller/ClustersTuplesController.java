package controller;

import java.util.LinkedList;
import java.util.List;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import model.ServerModel;

public class ClustersTuplesController extends Controller{
	private ServerModel serverModel;
	
	@FXML
	private TableView<List<StringProperty>> table;
	
	public void initialize(ServerModel serverModel, LinkedList<LinkedList<String>> centroids) {
		this.serverModel = serverModel;
		
		
		
	}

}
