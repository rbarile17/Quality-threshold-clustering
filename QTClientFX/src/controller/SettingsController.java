package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import utility.ExceptionAlert;

public class SettingsController extends Controller {
	@FXML
	private Button apply;
	@FXML
	private TextField ip;
	@FXML
	private TextField port;

	private MainController main;

	public void applyClick(ActionEvent event) {
		String ipS = "", portS = "";
		int portI = -1;

		ipS = ip.getText();
		portS = port.getText();
		if (ipS.equals(""))
			ipS = "127.0.0.1";
		if (portS.equals(""))
			portS = "8080";
			
		try {
			portI = Integer.parseInt(portS);
			if (portI < 1 || portI > 65536) {
				new ExceptionAlert("Port error", "Port must be a number between 1 and 65536", AlertType.WARNING);
				return;
			}
		} catch (NumberFormatException e) {
			new ExceptionAlert("Port error", "Port must be a number!", AlertType.WARNING);
			return;
		}
		try {
			main.connect(ipS, portI);
			((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
		} catch (IOException e) {
			new ExceptionAlert(e);
		}
	}

	public void initialize(MainController main) {
		this.main = main;
	}
}
