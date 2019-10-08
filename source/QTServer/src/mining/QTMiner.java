package mining;

import java.util.Arrays;
import data.Data;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Defines a class that implements QT algorithm<br>
 * @author Pasquale De Marinis
 * @author Roberto Barile
 * @author Sergio Caputo
 */
public class QTMiner {
	private ClusterSet C;
	private double radius;

	/**
	 * Open the file given in input and memorize the object read in the cluster set
	 * @param fileName file where there's the cluster set to store
	 * @throws FileNotFoundException if the file named fileName is not found
	 * @throws IOException if stream used to read from file is interrupted
	 * @throws ClassNotFoundException if class requested to instantiate FileInputStream with fileName in input is not found
	 */
	public QTMiner(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
		C = (ClusterSet) in.readObject();
		in.close();

	}

	/**
	 * @param radius radius of cluster
	 */
	public QTMiner(double radius) {
		this.radius = radius;
		C = new ClusterSet();
	}

	/**
	 * @return cluster set
	 */
	public ClusterSet getC() {
		return C;
	}

	/**
	 * Creates a cluster for every tuple that is not clustered yet including in the cluster points near the area defined by the radius,
	 * <br> saves the most popular cluster and removes all its points in the list of the tuples to be clustered.
	 * <br> Repeats all the steps till there are tuples to be assigned to a cluster
	 * @param data transitions (examples)
	 * @return number of cluster
	 * @throws ClusteringRadiusException when only one cluster is generated
	 */
	public int compute(Data data) throws ClusteringRadiusException{
		int numClusters = 0;
		boolean isClustered[] = new boolean[data.getNumberOfExamples()];
		Arrays.fill(isClustered, false);

		int countClustered = 0;

		while (countClustered != data.getNumberOfExamples()) {
			// Ricerca cluster più popoloso
			Cluster c = buildCandidateCluster(data, isClustered);

			C.add(c);
			numClusters++;

			// Rimuovo tuple clusterizzate da dataset
			for (int i : c) {
				isClustered[i] = true;
			}
			countClustered += c.getSize();
		}

		if (numClusters == 1) {
			throw new ClusteringRadiusException("Only one cluster found, try smaller radius.");
		}
		return numClusters;
	}

	/**
	 * Builds a candidate cluster to add to the cluster set
	 * @param data transitions (examples)
	 * @param isClustered array which indicates clustered tuples
	 * @return candidate cluster
	 * @throws EmptyDatasetException if empty data is returned
	 */
	private Cluster buildCandidateCluster(Data data, boolean isClustered[]){
		Cluster biggestC = null;
		Cluster c = null;
		for (int i = 0; i < isClustered.length; i++) {
			if (!isClustered[i]) {
				c = new Cluster(data.getItemSet(i));

				for (int j = 0; j < data.getNumberOfExamples(); j++)
					if (!isClustered[j]) {
						double dist = data.getItemSet(i).getDistance(data.getItemSet(j));
						if (dist <= radius)
							c.addData(j);
					}

				try {
					if (c.getSize() > biggestC.getSize())
						biggestC = c;
				} catch (NullPointerException e) {
					biggestC = c;
				}
			}
		}

		return biggestC;
	}

	/**
	 * Saves the cluster set in a file
	 * @param fileName file used to store cluster set
	 * @param data transitions
	 * @throws FileNotFoundException if the file named fileName is not found
	 * @throws IOException if stream used to write on file is interrupted
	 */
	public void save(String fileName, Data data) throws FileNotFoundException, IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
		out.writeObject(C);
		out.close();
	}

	/**
	 * @return the cluster set
	 */
	public String toString() {
		return C.toString();
	}

}
