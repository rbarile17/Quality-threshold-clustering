package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert.AlertType;
import utility.ExceptionAlert;

/**
 * Communicates with the server to receive the mining results
 * 
 * @author Pasquale De Marinis
 * @author Roberto Barile
 * @author Sergio Caputo
 */
public class ServerModel {

	public Socket server;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private static final String OK = "OK";

	private static final int CLOSE_CONNECTION = -1;
	private static final int GO_BACK = 0;
	private static final int CLUSTER_DB = 1;
	private static final int SAVE_FILE = 2;
	private static final int LOAD_FILE = 3;
	private static final int RECEIVE_DATA = 4;
	private static final int RECEIVE_DISTANCES = 5;

	/**
	 * Initialize the socket and the streams
	 * 
	 * @param ip      The ip of the server
	 * @param port The port of the server
	 * @throws IOException          if the connection to the server fails
	 * @throws UnknownHostException if the host isn't found
	 */
	public ServerModel(String ip, int port) throws IOException, UnknownHostException {
		this.server = new Socket(InetAddress.getByName(ip), port);
		out = new ObjectOutputStream(server.getOutputStream());
		in = new ObjectInputStream(server.getInputStream());
	}

	/**
	 * Tells the server to load and sent the file with the specified name
	 * 
	 * @param fileName The name of the file to be requested to the server
	 * @return true if the file name has been corrected sent and it exists
	 * @throws IOException if the communication with the server is interrupted
	 */
	public boolean loadFile(String fileName) throws IOException {
		out.writeObject(LOAD_FILE);
		out.writeObject(fileName);
		String serverAnswer;
		try {
			serverAnswer = (String) in.readObject();
			if (!serverAnswer.equals(OK)) {
				new ExceptionAlert("File loading error",serverAnswer,AlertType.ERROR);
				return false;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean clusterDBTable(String tableName, double radius) throws IOException {
		out.writeObject(CLUSTER_DB);
		out.writeObject(tableName);
		try {
			String serverAnswer = (String) in.readObject();
			if (!serverAnswer.equals(OK)) {
				new ExceptionAlert("DB clustering error",serverAnswer,AlertType.ERROR);
				return false;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		out.writeObject(radius);
		return true;
	}

	public LinkedList<String> getAttributesNames() throws IOException {
		LinkedList<String> names = null;

		try {
			names = (LinkedList<String>) in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return names;
	}

	public int getCentroidsNumber() throws IOException {
		int centroidsNumber = 1;

		try {
			String serverAnswer = (String) in.readObject();
			if (!serverAnswer.equals(OK))
				new ExceptionAlert("Message", serverAnswer, AlertType.INFORMATION);
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

	public LinkedList<List<List<String>>> getData() throws IOException {
		LinkedList<List<List<String>>> data = null;

		out.writeObject(RECEIVE_DATA);

		try {
			data = (LinkedList<List<List<String>>>) in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return data;
	}

	public void close(Boolean regularClosing) {
		if (isConnected()) {
			try {
				if (regularClosing)
					out.writeObject(CLOSE_CONNECTION);
				server.close();
			} catch (IOException e) {
				new ExceptionAlert("Connecton lost", "Failed to properly closing connection", AlertType.ERROR);
			}
		}
		server = null;
	}

	public boolean isConnected() {
		return server != null;
	}

	public String saveFile(String file) throws IOException, ClassNotFoundException {
		out.writeObject(SAVE_FILE);
		out.writeObject(file);
		String answer = (String) in.readObject();
		if (answer.equals(OK))
			return "";
		else
			return answer;
	}

	public void goBack() {
		try {
			out.writeObject(GO_BACK);
		} catch (IOException e) {
			new ExceptionAlert(e);
		}
	}

	public LinkedList<List<Double>> getDistances() throws IOException {
		LinkedList<List<Double>> distances = null;
		out.writeObject(RECEIVE_DISTANCES);
		try {
			distances = (LinkedList<List<Double>>) in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return distances;
	}

	public String getIP() {
		return server.getInetAddress().getCanonicalHostName();
	}

	public int getPort() {
		return server.getPort();
	}
}
