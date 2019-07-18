package mining;

import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import java.io.Serializable;
import data.Tuple;
import data.Data;

public class Cluster implements Iterable<Integer>, Comparable<Cluster>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3998359756241982723L;

	private Tuple centroid;

	private Set<Integer> clusteredData;

	Cluster(Tuple centroid) {
		this.centroid = centroid;
		clusteredData = new HashSet<Integer>();
	}

	public Tuple getCentroid() {
		return centroid;
	}

	// return true if the tuple is changing cluster
	boolean addData(int id) {
		return clusteredData.add(id);

	}

	// verifica se una transazione è clusterizzata nell'array corrente
	boolean contain(int id) {
		return clusteredData.contains(id);
	}

	// remove the tuple that has changed the cluster
	void removeTuple(int id) {
		clusteredData.remove(id);

	}

	int getSize() {
		return clusteredData.size();
	}

	public Iterator<Integer> iterator() {
		return clusteredData.iterator();
	}

	public String toString() {
		String str = "Centroid=(";
		for (int i = 0; i < centroid.getLength(); i++)
			str += centroid.get(i);
		str += ")";
		return str;

	}

	public int compareTo(Cluster c) {
		return this.getSize() > c.getSize() ? 1 : -1;
	}

	public String toString(Data data) {
		String str = "Centroid=(";
		int value;
		for (int i = 0; i < centroid.getLength(); i++)
			str += centroid.get(i) + " ";
		str += ")\nExamples:\n";
		Iterator<Integer> it = clusteredData.iterator();
		while (it.hasNext()) {
			str += "[";
			value = it.next();
			for (int j = 0; j < data.getNumberOfAttributes(); j++)
				str += data.getAttributeValue(value, j) + " ";
			str += "] dist=" + getCentroid().getDistance(data.getItemSet(value)) + "\n";

		}
		str += "\nAvgDistance=" + getCentroid().avgDistance(data, this.clusteredData);
		return str;
	}
}
