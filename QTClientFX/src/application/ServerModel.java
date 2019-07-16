package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerModel {
	
	private Socket server;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	ServerModel(String ip, String port) throws IOException, UnknownHostException {
		this.server = new Socket(InetAddress.getByName(ip), Integer.parseInt(port));
		out = new ObjectOutputStream(server.getOutputStream());
		in = new ObjectInputStream(server.getInputStream());
	}
	
	public void loadFile(String fileName) throws IOException{
        out.writeObject(3);
        out.writeObject(fileName);
	}
	
	public void clusterDBTable(String tableName) throws IOException{
        out.writeObject(3);
        out.writeObject(tableName);
	}
	
	public void close() {
		try {
			server.close();
		} catch (IOException e) {
			new AlertException(e);
		}
	}
 }
