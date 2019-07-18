package controller;

import java.util.LinkedList;
import java.util.List;


import javafx.fxml.FXML;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ScatterPlotController extends Controller{

	@FXML
	ScatterChart<String, Double> chart;
	
	@FXML
	AnchorPane pane;
	
	@FXML
	Label title;

	public void initialize(LinkedList<List<Double>> elements) {
		int i = 0;
		int j = 0;
		for (List<Double> list : elements) {
			XYChart.Series<String, Double> xValues = new XYChart.Series<String, Double>();
			xValues.setName(String.valueOf(i));
			i++;
			for (Double xValue : list) {
				xValues.getData().add(new XYChart.Data<String, Double>(String.valueOf(j),xValue));
				j++;
			}
			chart.getData().add(xValues);
			pane.setPrefWidth(100*i);
			((Stage)pane.getScene().getWindow()).sizeToScene();
		}
	}

}
