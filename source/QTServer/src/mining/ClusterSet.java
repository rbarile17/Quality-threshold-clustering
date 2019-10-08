package mining;

import java.util.TreeSet;
import java.util.Set;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import data.Data;
import java.io.Serializable;

/**
 * Defines a class that represents a set of clusters<br>
 * @author Pasquale De Marinis
 * @author Roberto Barile
 * @author Sergio Caputo
 */
public class ClusterSet implements Iterable<Cluster>, Serializable {

	private static final long serialVersionUID = 6423140876509792857L;
	private Set<Cluster> C = new TreeSet<Cluster>();

	ClusterSet() {
	}

	/**
	 * @param c cluster to add to the set
	 */
	void add(Cluster c) {
		C.add(c);
	}

	/**
	 * @return centroids from clusters set
	 */
	public String toString() {
		String str = "";

		for (Cluster c : C)
			str += c + "\n";

		return str;
	}

	/**
	 * @param data transitions (examples)
	 * @return state of every cluster in the set
	 */
	public String toString(Data data) {
		String str = "";

		int i = 0;
		for (Cluster c : C) {
			if (c != null)
				str += (i++) + ":" + c.toString(data) + "\n";
			str += "\n";
		}
		return str;
	}

	/**
	 * @return list of centroids
	 */
	public List<List<String>> toList() {
		List<List<String>> l = new LinkedList<List<String>>();

		for (Cluster c : this.C) {
			l.add(c.getCentroid().toList());
		}

		return l;
	}
	
	/**
	 * Returns the list of the avg distances, each element is a cluster avg distance
	 * @param data clustered data
	 * @return
	 */
	public List<Double> avgDistanceList(Data data) {
		List<Double> distances = new LinkedList<Double>();
		
		for(Cluster c : this.C) {
			distances.add(c.getAvgDistance(data));
		}
		
		return distances;
	}
	
	/**
	 * @return iterator for cluster
	 */
	public Iterator<Cluster> iterator() {
		return C.iterator();
	}
}
