package data;

import java.util.Set;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.io.Serializable;

public class Tuple implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3782053023012533177L;
	private Item[] tuple;
	
	Tuple(int size) {
		tuple = new Item[size];
	}
	
	public int getLength() {
		return tuple.length;
	}
	
	public Item get(int i) {
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
	
	public double getDistance(Tuple obj) {
		double distance = 0.0;
		for (int i = 0; i < this.getLength(); i++) {
			distance = distance + tuple[i].distance(obj.get(i).getValue());
		}
		return distance;
	}

	public double avgDistance(Data data,  Set<Integer> clusteredData){
		double sumD=0.0;
		for(int i : clusteredData){
			double d= getDistance(data.getItemSet(i));
			sumD+=d;
		}
		return sumD/clusteredData.size();
	}
	
	public List<String> toList() {
		List<String> l = new LinkedList<String>();
		
		for(int i=0; i<this.getLength(); i++) {
			l.add(tuple[i].toString());
		}
		
		return l;
	}

}
