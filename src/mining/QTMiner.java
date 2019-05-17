package mining;

import data.Data;
import data.EmptyDatasetException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

public class QTMiner {
	private ClusterSet C;
	private double radius;
	
	public QTMiner(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
		FileInputStream inFile = new FileInputStream(fileName);
		ObjectInputStream inStream = new ObjectInputStream(inFile);
		this.C = (ClusterSet) inStream.readObject();
		inStream.close();
	}
	
	public void save(String fileName) throws FileNotFoundException, IOException {
		FileOutputStream outFile = new FileOutputStream(fileName);
		ObjectOutputStream outStream = new ObjectOutputStream(outFile);
		outStream.writeObject(this.C);
		outStream.close();
	}
	
	public QTMiner(double radius) {
		C = new ClusterSet();
		this.radius = radius;
	}
	
	public ClusterSet getC() {
		return C;
	}
	
	public String toString() {
		return C.toString();
	}
	
	public int compute(Data data) throws ClusteringRadiusException, EmptyDatasetException {
		if (data.getNumberOfExamples() == 0) throw new EmptyDatasetException();
		int numClusters=0;
		boolean isClustered[] = new boolean[data.getNumberOfExamples()];
		Arrays.fill(isClustered, false);
		
		int countClustered=0;

		while(countClustered != data.getNumberOfExamples()) {
			//Ricerca cluster più popoloso
			Cluster c = buildCandidateCluster(data, isClustered);
			
			C.add(c);
			numClusters++;
			
			//Rimuovo tuple clusterizzate da dataset
			for(int i : c){
				isClustered[i] = true;
			}	
			countClustered += c.getSize();
		 }
		
		if (numClusters == 1) {
			throw new ClusteringRadiusException();
		}
		return numClusters;
	}
	
	Cluster buildCandidateCluster(Data data, boolean isClustered[]) {
		Cluster biggestC = null;
		Cluster c = null;
		
		for (int i=0; i<isClustered.length; i++) {
			if(!isClustered[i]) {
				c = new Cluster(data.getItemSet(i));
				
				for(int j=0; j<data.getNumberOfExamples(); j++)
					if(!isClustered[j])
						if(data.getItemSet(i).getDistance(data.getItemSet(j)) <= radius)
							c.addData(j);
				
				if(biggestC == null)  
					biggestC = c;
				else 
					if(c.getSize() > biggestC.getSize()) 
						biggestC = c;
			}
		}

		return biggestC;
	}
}