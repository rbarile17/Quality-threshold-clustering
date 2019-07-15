package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SettingsController {
	@FXML
	private Button apply;
	@FXML
	private TextField ip;
	@FXML
	private TextField port;
	
	private MainController main;

	
	@FXML
	public void applyClick(ActionEvent event) {
		String ipS = ip.getText();
		String portS = port.getText();
		if (ipS.equals(""))
			ipS = "127.0.0.1";
		if (portS.equals(""))
			portS = "8080";
		
		InetAddress addr;
		ObjectOutputStream out;
		ObjectInputStream in;
		Socket server = null;
		try {
			addr = InetAddress.getByName(ipS);
			try {
				server = new Socket(InetAddress.getByName(ipS), Integer.parseInt(portS));
				out = new ObjectOutputStream(server.getOutputStream());
				in = new ObjectInputStream(server.getInputStream());
				main.setServer(server);
				((Stage)((Node) event.getSource()).getScene().getWindow()).hide();
			} catch (IOException e) {
				new AlertException(e);
			} 
		} catch (UnknownHostException e2) {
			new AlertException(e2);
		}
	}
	
	public void init(MainController main) {
		this.main = main;
	}
}
