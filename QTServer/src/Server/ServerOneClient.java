package Server;

import java.net.Socket;
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
import database.NoValueException;
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
		 Data data;
		 Boolean active = true;
		 try {
			 while (active) {
				 int answer = (int) in.readObject();
				 switch (answer) {
				 case 0:
					 data = learningFromDB();
					 compute(data);
					 //storeClusterInFile(data);
					 break;
				 case 3:
					 learningFromFile();
					 break;
				 default:
					 active = false;
				 }
			 }
		 } catch (IOException | ClassNotFoundException e ){
			 e.printStackTrace();
		 }
		 System.out.println("Client connection closed");
	 }
	 
	 private Data learningFromDB() {
		 try {
			 String table = (String) in.readObject();
			 try {
				 Data data = new Data(table);
				 out.writeObject("OK");
				 return data;
			 } catch (NoValueException e) {
				 out.writeObject(e.getMessage());
			 }
		 }
		 catch (IOException | ClassNotFoundException e) {
			 e.printStackTrace();
		 }
		 return null;
	 }
	 
	 private void learningFromFile() {
		 try {
			String fileName = (String) in.readObject();
			try {
				ObjectInputStream serialIn = new ObjectInputStream(new FileInputStream(fileName+".dmp"));
				out.writeObject("OK");
				ClusterSet C = (ClusterSet) serialIn.readObject();
				serialIn.close();
				List<List<String>> l = C.toList();
				out.writeObject(l);
			} catch(FileNotFoundException e) {
				out.writeObject("File not found.");
			}
		 } catch (IOException | ClassNotFoundException  e) {
			 System.out.println(e);
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
			 System.out.println(e);
		 }
	 }
	 
	 private void storeClusterInFile(Data data) {
		 System.out.println("sto");
			try {/*
				System.out.println("choose "+in.readObject());
				System.out.println("Waiting file name");
				String fileName = (String)in.readObject();
				fileName += ".dmp";*/
				kmeans.save("file.dmp", data);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} /*catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}*/
			System.out.println("finito");
	 }
}