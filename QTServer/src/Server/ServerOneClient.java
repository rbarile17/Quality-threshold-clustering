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

public class ServerOneClient extends Thread {
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private QTMiner kmeans;

	private static final int FILE_LOADING = 3;
	private static final int FILE_SAVING = 2;
	private static final int DB_CLUSTERING = 1;
	private static final int CONNECTION_CLOSING = -1;
	private static final int DATA_SENDING = 4;
	private static final String OK = "OK";

	public ServerOneClient(Socket s) throws IOException {
		this.socket = s;
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		this.start();
	}

	public void run() {
		System.out.println("Client accepted");
		Data data;
		Boolean active = true;
		try {
			while (active) {
				int answer = (int) in.readObject();
				switch (answer) {
				case DB_CLUSTERING:
					data = learningFromDB();
					if (data == null)
						break;
					compute(data);
					while (true) {
						int choice = (int) in.readObject();
						if (choice == FILE_SAVING)
							storeClusterInFile(data);
						else if (choice == DATA_SENDING)
							out.writeObject(data.toList(kmeans.getC()));
						else if (choice == CONNECTION_CLOSING)
							break;
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
		} catch (IOException | ClassNotFoundException | EmptyDatasetException e) {
			e.printStackTrace();
		}
	}

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