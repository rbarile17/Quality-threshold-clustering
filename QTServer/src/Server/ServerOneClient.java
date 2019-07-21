package Server;

import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import mining.QTMiner;
import mining.ClusterSet;
import mining.ClusteringRadiusException;
import data.EmptyDatasetException;
import database.DatabaseConnectionException;
import database.EmptySetException;
import database.NoValueException;
import data.Data;

/**
 * Defines a class that describes a server<br>
 * @author Pasquale De Marinis
 * @author Roberto Barile
 * @author Sergio Caputo
 */
public class ServerOneClient extends Thread {
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private QTMiner kmeans;

	private static final int CONNECTION_CLOSING = -1;
	private static final int GOING_BACK = 0;
	private static final int DB_CLUSTERING = 1;
	private static final int FILE_SAVING = 2;
	private static final int FILE_LOADING = 3;
	private static final int DATA_SENDING = 4;
	private static final int DISTANCES_SENDING = 5;
	private static final String OK = "OK";

	/**
	 * Starts the thread associated to the connection with client
	 * @param s socket for the connection with client
	 * @throws IOException if thread associated to the connection is interrupted
	 */
	public ServerOneClient(Socket s) throws IOException {
		this.socket = s;
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		this.start();
	}

	/**
	 * Handles client requests
	 */
	public void run() {
		System.out.println("Client accepted");
		Data data;
		boolean active = true;
		try {
			while (active) {
				int answer = (int) in.readObject();
				switch (answer) {
				case DB_CLUSTERING:
					data = learningFromDB();
					if (data == null)
						break;
					compute(data);
					boolean DB = true;
					while (DB) {
						int choice = (int) in.readObject();
						switch (choice) {
						case FILE_SAVING:
							storeClusterInFile(data);
							break;
						case DATA_SENDING:
							out.writeObject(data.toList(kmeans.getC()));
							break;
						case DISTANCES_SENDING: 
							out.writeObject(data.getDistances(kmeans.getC()));
							break;
						case CONNECTION_CLOSING:
							DB = false;
							active = false;
							break;
						case GOING_BACK:
							DB = false;
						}
					}
					break;
				case FILE_LOADING:
					learningFromFile();
					break;
				default:
					active = false;
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Client connection closed");
	}

	/**
	 * Uses DB table to cluster data
	 * @return
	 */
	private Data learningFromDB() {
		try {
			String table = (String) in.readObject();
			try {
				Data data;
				data = new Data(table);
				out.writeObject("OK");
				return data;
			} catch (NoValueException e) {
				out.writeObject(e.getMessage());
			} catch (DatabaseConnectionException e) {
				out.writeObject(e.getMessage());
			} catch (SQLException e) {
				out.writeObject(e.getMessage() + "\n" + e.getErrorCode() + "\n" + e.getSQLState());
			} catch (EmptySetException e) {
				out.writeObject(e.getMessage());
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Uses file to cluster data
	 */
	private void learningFromFile() {
		try {
			String fileName = (String) in.readObject();
			try {
				ObjectInputStream serialIn = new ObjectInputStream(new FileInputStream(fileName + ".dmp"));
				out.writeObject("OK");
				ClusterSet C = (ClusterSet) serialIn.readObject();
				serialIn.close();
				List<List<String>> l = C.toList();
				out.writeObject(l);
			} catch (FileNotFoundException e) {
				out.writeObject("File not found.");
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Clusters data
	 * @param data data to be clustered
	 */
	private void compute(Data data) {
		try {
			double radius = (double) in.readObject();
			kmeans = new QTMiner(radius);
			int result = 1;
			try {
				result = kmeans.compute(data);
				out.writeObject("OK");
			} catch (ClusteringRadiusException e1) {
				out.writeObject(e1.getMessage());
			}
			out.writeObject(result);
			List<String> names = data.getAttributesNames();
			out.writeObject(names);
			out.writeObject(kmeans.getC().toList());
			out.writeObject(kmeans.getC().avgDistanceList(data));
		} catch (IOException | ClassNotFoundException | EmptyDatasetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saves data in a file
	 * @param data data to be saved in a file given in input
	 * @throws ClassNotFoundException
	 */
	private void storeClusterInFile(Data data) throws ClassNotFoundException {
		try {
			try {
				String fileName = (String) in.readObject();
				fileName += ".dmp";
				kmeans.save(fileName, data);
				out.writeObject(OK);
			} catch (FileNotFoundException e) {
				out.writeObject("Wrong path");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}