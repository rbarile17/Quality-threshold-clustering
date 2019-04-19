class Cluster {
	private Tuple centroid;

	private ArraySet clusteredData; 
	
	/*Cluster(){
		
	}*/

	Cluster(Tuple centroid) {
		this.centroid = centroid;
		clusteredData = new ArraySet();
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
		return clusteredData.get(id);
	}

	//remove the tuple that has changed the cluster
	void removeTuple(int id) {
		clusteredData.delete(id);
	}
	
	int  getSize() {
		return clusteredData.size();
	}
	
	int[] iterator() {
		return clusteredData.toArray();
	}
	
	public String toString() {
		String str = "Centroid=(";
		for(int i=0; i<centroid.getLength(); i++)
			str += centroid.get(i);
		str += ")";
		
		return str;
	}
	
	public String toString(Data data) {
		String str="Centroid=(";
		for(int i=0; i<centroid.getLength(); i++)
			str += centroid.get(i)+ " ";
		str += ")\nExamples:\n";
		int array[] = clusteredData.toArray();
		for(int i=0; i<array.length; i++){
			str += "[";
			for(int j=0; j<data.getNumberOfAttributes(); j++)
				str += data.getAttributeValue(array[i], j)+" ";
			str += "] dist=" + getCentroid().getDistance(data.getItemSet(array[i])) + "\n";
		}
		str+="\nAvgDistance="+getCentroid().avgDistance(data, array);
		
		return str;
	}
}
