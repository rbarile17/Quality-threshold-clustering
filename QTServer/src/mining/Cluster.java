package mining;

import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import java.io.Serializable;
import data.Tuple;
import data.Data;

/**
 * Defines a class that represents a cluster<br>
 * @author Pasquale De Marinis
 * @author Roberto Barile
 * @author Sergio Caputo
 */
public class Cluster implements Iterable<Integer>, Comparable<Cluster>, Serializable {
	
	private static final long serialVersionUID = 3998359756241982723L;

	private Tuple centroid;

	private Set<Integer> clusteredData;

	/**
	 * @param centroid for the cluster
	 */
	Cluster(Tuple centroid) {
		this.centroid = centroid;
		clusteredData = new HashSet<Integer>();
	}

	/**
	 * @return centroid
	 */
	public Tuple getCentroid() {
		return centroid;
	}

	/**
	 * Adds a transition to the cluster
	 * @param id index of the transition
	 * @return true if the tuple is changing cluster
	 */
	boolean addData(int id) {
		return clusteredData.add(id);
	}

	/**
	 * Checks if a transition is clustered in the current array
	 * @param id index of the transition
	 * @return true if the transition is clustered
	 */
	boolean contain(int id) {
		return clusteredData.contains(id);
	}

	/**
	 * Removes the tuple that has changed the cluster
	 * @param id index of the transition
	 */
	void removeTuple(int id) {
		clusteredData.remove(id);
	}

	/**
	 * @return size of the cluster
	 */
	int getSize() {
		return clusteredData.size();
	}

	/**
	 * @return iterator for the clustered data
	 */
	public Iterator<Integer> iterator() {
		return clusteredData.iterator();
	}
	
	/**
	 * @return centroid
	 */
	public String toString() {
		String str = "Centroid=( ";
		int i;
		for (i=0; i < centroid.getLength() - 1; i++)
			str += centroid.get(i) + ", ";
		str += centroid.get(i);
		str += ")";
		return str;

	}

	/**
	 * @param c cluster to be compared with the current one 
	 */
	public int compareTo(Cluster c) {
		return this.getSize() > c.getSize() ? 1 : -1;
	}

	/**
	 * @param data transitions (examples)
	 * @return centroid and examples
	 */
	public String toString(Data data) {
		String str = "Centroid=( ";
		for(int i=0; i<centroid.getLength(); i++)
			str += centroid.get(i) + " ";
		str += ")\nExamples:\n";

		for(int i : this){
			str += "[";
			for(int j=0; j<data.getNumberOfAttributes(); j++)
				str += data.getAttributeValue(i, j)+" ";
			str += "] dist=" + getCentroid().getDistance(data.getItemSet(i)) + "\n";
		}
		str+="\nAvgDistance="+getCentroid().avgDistance(data, clusteredData);
		
		return str;
	}
	
	/**
	 * Returns the avg distance of the cluster tuples to the cluster centroidd
	 * 
	 * @param data
	 * @return
	 */
	public double getAvgDistance(Data data) {
		return this.getCentroid().avgDistance(data, clusteredData);
	}
}
