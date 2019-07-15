package application;

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
		Socket server = null;
		try {
			addr = InetAddress.getByName(ipS);
			try {
				server = new Socket(InetAddress.getByName(ipS), Integer.parseInt(portS));
				main.setServer(server);
				((Stage)((Node) event.getSource()).getScene().getWindow()).hide();
			} catch (IOException e) {
				new AlertException(e);
			} 
		} catch (UnknownHostException e1) {
			new AlertException(e1);
		}
	}
	
	public void init(MainController main) {
		this.main = main;
	}
}
