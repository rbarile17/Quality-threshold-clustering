import java.util.Arrays;

class Tuple {
	private Item[] tuple;
	
	Tuple(int size) {
		tuple = new Item[size];
	}
	
	int getLength() {
		return tuple.length;
	}
	
	Item get(int i) {
		return tuple[i];
	}
	
	void add(Item c, int i) {
		if(i >= this.getLength()){
			//enlarge the tuple
			Item temp[] = new Item[this.getLength()*2];
			Arrays.fill(temp, null);
			System.arraycopy(tuple, 0, temp, 0, this.getLength());
			tuple = temp;
		}
		
		tuple[i] = c;
	}
	
	double getDistance(Tuple obj) {
		double totalDistance=0.0;
		for (int i=0; i<this.getLength(); i++) {
			totalDistance += tuple[i].distance(obj.get(i).getValue());
		}
		
		return totalDistance;
	}
	
	double avgDistance(Data data, int clusteredData[]) {
		double sumD=0.0;
		for(int i=0; i<clusteredData.length; i++) {
			double d = getDistance(data.getItemSet(clusteredData[i]));
			sumD += d;
		}
		
		return sumD/clusteredData.length;
	}
}
