package mining;

import data.Tuple;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import data.Data;

class Cluster implements Iterable<Integer>, Comparable<Cluster>, Serializable{
	private Tuple centroid;

	private Set<Integer> clusteredData; 
	
	/*Cluster(){
		
	}*/

	Cluster(Tuple centroid) {
		this.centroid = centroid;
		clusteredData = new HashSet<Integer>();
	}
		
	Tuple getCentroid() {
		return centroid;
	}
	
	//return true if the tuple is changing cluster
	boolean addData(int id) {
		return clusteredData.add(id);
	}
	
	//verify that a transation is in the cluster 
	boolean contain(int id) {
		return clusteredData.contains(id);
	}

	//remove the tuple that has changed the cluster
	void removeTuple(int id) {
		clusteredData.remove(id);
	}
	
	int  getSize() {
		return clusteredData.size();
	}
	
	public String toString() {
		String str = "Centroid=(";
		for(int i=0; i<centroid.getLength(); i++)
			str += centroid.get(i) + " ";
		str += ")";
		
		return str;
	}
	
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

	@Override
	public int compareTo(Cluster o) {
		if (this.getSize() > o.getSize())
			return 1;
		else
			return -1;
	}
	
	@Override
	public Iterator<Integer> iterator() {
		return this.clusteredData.iterator();
	}
}
