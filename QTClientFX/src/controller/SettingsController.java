package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
		String ipS = ip.getText();
		String portS = port.getText();
		if (ipS.equals(""))
			ipS = "127.0.0.1";
		if (portS.equals(""))
			portS = "8080";
		try {
			main.connect(ipS,portS);
			((Stage)((Node) event.getSource()).getScene().getWindow()).close();
		} catch (IOException e) {
			new ExceptionAlert(e);
		}
	}
	
	public void initialize(MainController main) {
		this.main = main;
	}
}
