package controller;

import java.util.LinkedList;
import java.util.List;

import com.sun.glass.ui.Screen;
import utility.Fractions;
import javafx.fxml.FXML;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Control the events of the window that shows the scatter plot of the tuples
 * 
 * @author Pasquale De Marinis
 * @author Roberto Barile
 * @author Sergio Caputo
 */
/**
 * @author PasqualeDeMarinis
 *
 */
/**
 * @author PasqualeDeMarinis
 *
 */
/**
 * @author PasqualeDeMarinis
 *
 */
public class ScatterPlotController extends Controller {

	@FXML
	ScatterChart<String, Double> chart;

	@FXML
	AnchorPane pane;

	@FXML
	Label title;

	LinkedList<List<Double>> elements;

	/**
	 * Calls the methods to initialize the window
	 * 
	 * @param elements The elements that will be shown on the chart
	 */
	public void initialize(LinkedList<List<Double>> elements) {
		this.elements = elements;

		int tupleNumber = this.fillChart();
		this.setWindow(tupleNumber);
	}

	/**
	 * Set the window to the right size
	 * 
	 * @param tupleNumber The number of tuples
	 */
	private void setWindow(int tupleNumber) {
		pane.setPrefHeight(Fractions.HEIGHT_1 * Fractions.SCREEN_HEIGHT);
		pane.setPrefWidth(Fractions.WIDTH_1 * Fractions.SCREEN_WIDTH * tupleNumber);
		Stage current = ((Stage) pane.getScene().getWindow());
		current.setY(Fractions.HEIGHT_2 * Fractions.SCREEN_HEIGHT);
		current.sizeToScene();
	}

	/**
	 * Fills the chart with the elements passed to the method 'initialize'
	 * 
	 * @return The number of elemnts of the chart
	 */
	private int fillChart() {
		int i = 0;
		int j = 0;
		for (List<Double> list : elements) {
			XYChart.Series<String, Double> xValues = new XYChart.Series<String, Double>();
			xValues.setName(String.valueOf(i));
			i++;
			for (Double xValue : list) {
				xValues.getData().add(new XYChart.Data<String, Double>(String.valueOf(j), xValue));
				j++;
			}
			chart.getData().add(xValues);
		}

		return j;
	}
}
