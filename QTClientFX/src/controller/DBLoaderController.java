package controller;

import java.io.IOException;
import java.util.List;

import javafx.util.Pair;
import model.ServerModel;
import utility.ExceptionAlert;


public class DBLoaderController extends Controller{

private ServerModel serverModel;
private MainController main;
	
	public void initialize (MainController main, ServerModel serverModel) {
		this.serverModel = serverModel;
		this.main = main;
		try {
			Pair<List<String>,List<String>> data = serverModel.getClusteringResult();
		} catch (IOException e) {
			try {
				main.disconnect();
			} catch (IOException ex) {
				new ExceptionAlert(ex);
			}
		}
	}
}
