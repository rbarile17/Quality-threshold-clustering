package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SettingsController {
	@FXML
	private Button apply;
	@FXML
	private TextField ip;
	@FXML
	private TextField port;
	
	@FXML
	public void applyClick() {
		String ipS = ip.getText();
		String portS = port.getText();
		if (ipS.equals(""))
			ipS = "127.0.0.1";
		if (portS.equals(""))
			portS = "8080";
		
		InetAddress addr;
		try {
			addr = InetAddress.getByName(ipS);
			try {
				Socket socket = new Socket(addr, Integer.parseInt(portS));
			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			} 
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
	}
}
