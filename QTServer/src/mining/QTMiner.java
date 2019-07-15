package mining;
import java.util.Arrays;
import data.Data;
import data.EmptyDatasetException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class QTMiner {
	private ClusterSet C;
	private double radius;
	
	public QTMiner(String fileName) throws FileNotFoundException,IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
				C = (ClusterSet)in.readObject();
				in.close();

	}
	
	public QTMiner(double radius) {
		this.radius = radius;
		C = new ClusterSet();
	}
	
	public ClusterSet getC() {
		return C;
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
			for(int i : c ) {
				isClustered[i] = true;
			}
			countClustered += c.getSize();
		 }
		
		if (numClusters == 1) {
			throw new ClusteringRadiusException();
		}
		return numClusters;
	}
	
	Cluster buildCandidateCluster(Data data, boolean isClustered[]) throws EmptyDatasetException {
		Cluster biggestC = null;
		Cluster c = null;
		if (data == null || data.getNumberOfExamples() == 0 )
			throw new EmptyDatasetException("Empty data set");
		for (int i=0; i<isClustered.length; i++) {
			if(!isClustered[i]) {
				c = new Cluster(data.getItemSet(i));
				
				for(int j=0; j<data.getNumberOfExamples(); j++)
					if(!isClustered[j]) {
						double dist = data.getItemSet(i).getDistance(data.getItemSet(j));
						if(dist <= radius)
							c.addData(j);
					}
				
				try {
					if(c.getSize() > biggestC.getSize()) 
						biggestC = c;
				} catch (NullPointerException e) { 
					biggestC = c;
				}
			}
		}

		return biggestC;
	}
	
	public void salva(String fileName, Data data) throws FileNotFoundException,IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
		out.writeObject(C.toString(data));
		out.close();
	}
	
	public String toString() {
		return C.toString();
	}

}
