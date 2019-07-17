package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

import javafx.scene.control.Alert.AlertType;
import utility.ExceptionAlert;

public class ServerModel {
	
	private Socket server;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private static final String OK = "OK";
	
	public ServerModel(String ip, String port) throws IOException, UnknownHostException {
		this.server = new Socket(InetAddress.getByName(ip), Integer.parseInt(port));
		out = new ObjectOutputStream(server.getOutputStream());
		in = new ObjectInputStream(server.getInputStream());
	}

	public boolean loadFile(String fileName) throws IOException {
        out.writeObject(3);
        out.writeObject(fileName);
        
        String serverAnswer;
		try {
			serverAnswer = (String) in.readObject();
	        if (!serverAnswer.equals(OK)) {
	        	new ExceptionAlert(serverAnswer);
	        	return false;
	        }
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean clusterDBTable(String tableName, double radius) throws IOException {
        out.writeObject(0);
        out.writeObject(tableName);
        try {
        	String serverAnswer = (String)in.readObject();
            if (!serverAnswer.equals(OK)) {
            	new ExceptionAlert(serverAnswer);
            	return false;
            }
        } catch(ClassNotFoundException e) {
        	e.printStackTrace();
        	return false;
        }
        out.writeObject(radius);
        return true;
	}
	
	public LinkedList<String> getAttributesNames() throws IOException{
		LinkedList<String> names = null;
		
		try {
			names = (LinkedList<String>) in.readObject();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return names;
	}
	
	public int getCentroidsNumber() throws IOException {
		int centroidsNumber = 1;
		
		try {
			String serverAnswer = (String) in.readObject();
			if(!serverAnswer.equals(OK))
				new ExceptionAlert(serverAnswer, AlertType.INFORMATION);
			centroidsNumber = (int) in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		
		return centroidsNumber;
	}
	
	public LinkedList<LinkedList<String>> getCentroids() throws IOException {
		LinkedList<LinkedList<String>> data = null;

		try {
			data = (LinkedList<LinkedList<String>>) in.readObject();
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

