package data;

import java.util.Set;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.io.Serializable;

/**
 * Defines a class that represents a tuple as sequence of attribute-value pairs <br>
 * @author Pasquale De Marinis
 * @author Roberto Barile
 * @author Sergio Caputo
 */
public class Tuple implements Serializable {
	
	private static final long serialVersionUID = -3782053023012533177L;
	private Item[] tuple;

	/**
	 * @param size size of items in the tuple
	 */
	Tuple(int size) {
		tuple = new Item[size];
	}

	/**
	 * @return length of the tuple
	 */
	public int getLength() {
		return tuple.length;
	}

	/**
	 * @param i index position of the item to get
	 * @return item at index position
	 */
	public Item get(int i) {
		return tuple[i];
	}

	/**
	 * @param c item to add
	 * @param i index position where add item
	 */
	void add(Item c, int i) {
		if (i >= this.getLength()) {
			// enlarge the tuple
			Item temp[] = new Item[this.getLength() * 2];
			Arrays.fill(temp, null);
			System.arraycopy(tuple, 0, temp, 0, this.getLength());
			tuple = temp;
		}

		tuple[i] = c;
	}

	/**
	 * Calculates distance between object in input and current tuple
	 * @param obj object to be used to calculate distance 
	 * @return distance
	 */
	public double getDistance(Tuple obj) {
		double distance = 0.0;
		for (int i = 0; i < this.getLength(); i++) {
			distance = distance + tuple[i].distance(obj.get(i).getValue());
		}
		return distance;
	}

	/**
	 * Calculates the average distance between current tuple and rows in data with index in clusteredData   
	 * @param data transitions 
	 * @param clusteredData set of clustered data
	 * @return average distance 
	 */
	public double avgDistance(Data data, Set<Integer> clusteredData) {
		double sumD = 0.0;
		for (int i : clusteredData) {
			double d = getDistance(data.getItemSet(i));
			sumD += d;
		}
		return sumD / clusteredData.size();
	}

	/**
	 * @return list with the tuple
	 */
	public List<String> toList() {
		List<String> l = new LinkedList<String>();

		for (int i = 0; i < this.getLength(); i++) {
			l.add(tuple[i].toString());
		}

		return l;
	}

}
