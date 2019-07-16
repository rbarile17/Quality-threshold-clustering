package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Pair;
import model.ServerModel;
import utility.ExceptionAlert;


public class DBLoaderController extends Controller{
	@FXML
	private Label centroidsLabel;
	
	private ServerModel serverModel;
	private MainController main;
	
	public void initialize (MainController main, ServerModel serverModel) {
		this.serverModel = serverModel;
		this.main = main;
		ArrayList<ArrayList<Object>> centroids;

		try {
			int centroidsNumber = serverModel.getCentroidsNumber();
			centroidsLabel.setText(centroidsNumber + " " + centroidsLabel.getText());
			centroids = serverModel.getCentroids();
		} catch (IOException e) {
			new ExceptionAlert(e);
		}
	}
}
