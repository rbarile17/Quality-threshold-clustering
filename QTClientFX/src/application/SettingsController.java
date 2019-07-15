package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
			ipS = "127.0.0.900";
		if (portS.equals(""))
			portS = "8080";
		
		InetAddress addr;
		ObjectOutputStream out;
		ObjectInputStream in;
		try {
			addr = InetAddress.getByName(ipS);
			try {
				Socket server = new Socket(InetAddress.getByName(ipS), Integer.parseInt(portS));
				out = new ObjectOutputStream(server.getOutputStream());
				in = new ObjectInputStream(server.getInputStream());
			} catch (IOException e) {
				new AlertException(e);
			} 
		} catch (UnknownHostException e2) {
			new AlertException(e2);
		}
	}
}
