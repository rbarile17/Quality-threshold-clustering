package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javafx.util.Pair;

import java.util.ArrayList;
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

	public void loadFile(String fileName) throws IOException {
        out.writeObject(3);
        out.writeObject(fileName);
	}
	
	public void clusterDBTable(String tableName, double radius) throws IOException {
        out.writeObject(0);
        out.writeObject(tableName);
        out.writeObject(radius);
	}
	
	public int getCentroidsNumber() throws IOException {
		int centroidsNumber = -1;
		
		try {
			centroidsNumber = (int) in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		
		return centroidsNumber;
	}
	
	public ArrayList<ArrayList<Object>> getCentroids() throws IOException {
		ArrayList<ArrayList<Object>> data = null;

		try {
			data = (ArrayList<ArrayList<Object>>) in.readObject();
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

