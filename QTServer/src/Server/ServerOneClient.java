package Server;

import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ClassNotFoundException;

import mining.QTMiner;
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
		 in = new ObjectInputStream(socket.getInputStream());
		 out = new ObjectOutputStream(socket.getOutputStream());
		 this.start();
	 }
	 
	 public void run() {
		 boolean loop = true;
		 int radius, answer = 0;
		 String table = "";
		 Data data;

		 try {
			 while (loop) {
				 answer = (int)in.readObject();
				 switch (answer) {
				 case 0:
					 do {
						 data = learningFromDB();
					 } while((int)in.readObject() != 0);
					 compute(data);
					 storeClusterInFile();
				 case 3:
					 learningFromFile();
				 default:
					 loop = false;
				 }
			 }
		 } catch (IOException | ClassNotFoundException e ){
			 e.printStackTrace();
		 }
	 }

	 
	 private Data learningFromDB() {
		 try {
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
			 String table = (String)in.readObject();
			 kmeans = new QTMiner(table+".dmp");
			 out.writeObject("OK");
			 out.writeObject(kmeans.toString());
		 }
		 catch (IOException | ClassNotFoundException e) {
			 System.out.println(e);
		 }
	 }
	 
	 private void compute(Data data) {
		 try {
			 int radius = (int)in.readObject();
			 kmeans = new QTMiner(radius);
			 int result = kmeans.compute(data);
			 if (!((Integer)result).equals(null)) {
				 out.writeObject("OK");
				 out.writeObject(result);
			 }
			 else
				 out.writeObject("NOT OK");
		 }
		 catch (IOException | ClassNotFoundException | EmptyDatasetException | ClusteringRadiusException e) {
			 System.out.println(e);
		 }
	 }
	 
	 private void storeClusterInFile() {
			try {
				String fileName = (String)in.readObject();
				kmeans.salva(fileName);
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