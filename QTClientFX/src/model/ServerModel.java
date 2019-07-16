package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javafx.util.Pair;
import java.util.List;

import utility.ExceptionAlert;

public class ServerModel {
	
	private Socket server;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public ServerModel(String ip, String port) throws IOException, UnknownHostException {
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
	
	public List<List<Object>> getFileData() throws IOException{
		List<List<Object>> data = null;

		try {
			data = (List<List<Object>>) in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return data;
	}
	
	public void close() {
		try {
			server.close();
		} catch (IOException e) {
			new ExceptionAlert(e);
		}
	}
 }

