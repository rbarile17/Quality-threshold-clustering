package data;

import java.util.TreeSet;
import java.util.Iterator;
import java.io.Serializable;

/**
 * Defines a class that represents a discrete attribute<br>
 * @author Pasquale De Marinis
 * @author Roberto Barile
 * @author Sergio Caputo
 */
class DiscreteAttribute extends Attribute implements Iterable<String>, Serializable {
	
	private static final long serialVersionUID = -6257717446631385482L;
	private TreeSet<String> values;

	/**
	 * @param name name of the attribute
	 * @param index index of the attribute
	 * @param values domain of the attribute
	 */
	DiscreteAttribute(String name, int index, TreeSet<String> values) {
		super(name, index);
		this.values = values;
	}

	/**
	 * @return number of discrete values in the domain
	 */
	int getNumberOfDistinctValues() {
		return values.size();
	}

	/**
	 * @return iterator for discrete values in the domain
	 */
	public Iterator<String> iterator() {
		return values.iterator();
	}
}