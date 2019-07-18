package controller;

import java.util.LinkedList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

public class ScatterPlotController extends Controller{

	@FXML
	ScatterChart<String, Double> chart;

	public void initialize(LinkedList<List<Double>> elements) {
		for (List<Double> list : elements) {
			XYChart.Series<String, Double> xValues = new XYChart.Series<String, Double>();
			for (Double xValue : list) {
				xValues.getData().add(new XYChart.Data<String, Double>("",xValue));
			}
			chart.getData().add(xValues);
		}
	}

}
