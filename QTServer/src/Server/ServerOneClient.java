package Server;

import java.net.Socket;
import java.util.List;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ClassNotFoundException;

import mining.QTMiner;
import mining.ClusterSet;
import mining.ClusteringRadiusException;

import data.EmptyDatasetException;
import data.Data;


public class ServerOneClient extends Thread {
	 private Socket socket;
	 private ObjectInputStream in;
	 private ObjectOutputStream out;
	 private QTMiner kmeans;
	 
	 public ServerOneClient(Socket s) throws IOException {
		 this.socket = s;
		 out = new ObjectOutputStream(socket.getOutputStream());
		 in = new ObjectInputStream(socket.getInputStream());
		 this.start();
	 }
	 
	 public void run() {
		 System.out.println("Client accepted");
		 int radius, answer = 0;
		 String table = "";
		 Data data;

		 try {
			 while (true) {
				 answer = (int) in.readObject();
				 switch (answer) {
				 case 0:
					 data = learningFromDB();
					 compute(data);
					 storeClusterInFile(data);
				 case 3:
					 learningFromFile();
				 default:
					 break;
				 }
			 }
		 } catch (IOException | ClassNotFoundException e ){
			 e.printStackTrace();
		 }
	 }

	 
	 private Data learningFromDB() {
		 try {
			 System.out.println("Waiting table name");
			 String table = (String)in.readObject();
			 Data data = new Data(table);
			 out.writeObject("OK");
			 return data;
		 }
		 catch (IOException | ClassNotFoundException e) {
			 System.out.println(e);
		 }
		 return null;
	 }
	 
	 private void learningFromFile() {
		 try {
			String fileName = (String) in.readObject();
			ObjectInputStream serialIn = new ObjectInputStream(new FileInputStream(fileName));
			ClusterSet C = (ClusterSet) serialIn.readObject();
			serialIn.close();
			List<List<Object>> l = C.toList();
			System.out.println("Lista creata");
			out.writeObject(l);
		 }
		 catch (IOException | ClassNotFoundException e) {
			 System.out.println(e);
		 }
	 }
	 
	 private void compute(Data data) {
		 try {
			 System.out.println("Waiting Radius");
			 int radius = (int)in.readObject();
			 kmeans = new QTMiner(radius);
			 int result = kmeans.compute(data);
			 if (!((Integer)result).equals(null)) {
				 out.writeObject("OK");
				 out.writeObject(result);
				 out.writeObject(kmeans.getC().toString());
			 }
			 else
				 out.writeObject("NOT OK");
		 }
		 catch (IOException | ClassNotFoundException | EmptyDatasetException | ClusteringRadiusException e) {
			 System.out.println(e);
		 }
	 }
	 
	 private void storeClusterInFile(Data data) {
			try {
				System.out.println("choose "+in.readObject());
				System.out.println("Waiting file name");
				String fileName = (String)in.readObject();
				fileName += ".dmp";
				kmeans.save(fileName, data);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
	 }
}