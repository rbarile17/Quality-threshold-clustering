package data;

import java.util.TreeSet;
import java.util.Iterator;
import java.io.Serializable;

public class DiscreteAttribute extends Attribute implements Iterable<String>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6257717446631385482L;
	private TreeSet<String> values;

	DiscreteAttribute(String name, int index, TreeSet<String> values) {
		super(name, index);
		this.values = values;
	}

	int getNumberOfDistinctValues() {
		return values.size();
	}

	public Iterator<String> iterator() {
		return values.iterator();
	}
}