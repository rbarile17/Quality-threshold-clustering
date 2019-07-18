package controller;

import java.util.LinkedList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

public class ScatterPlotController extends Controller{

	@FXML
	ScatterChart<String, Integer> chart;

	public void initialize(LinkedList<List<Double>> elements) {
		for (List<Double> list : elements) {
			XYChart.Series<String, Integer> xValues = new XYChart.Series<String, Integer>();
			for (Double xValue : list) {
				xValues.getData().add(new XYChart.Data<String, Integer>(String.valueOf(xValue), 1));
			}
			chart.getData().add(xValues);
		}
	}

}
