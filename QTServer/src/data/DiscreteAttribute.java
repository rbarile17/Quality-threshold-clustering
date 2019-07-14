package data;

import java.util.TreeSet;
import java.util.Iterator;
import java.io.Serializable;

public class DiscreteAttribute extends Attribute implements Iterable<String>,Serializable{
	private TreeSet<String>  values;
	
	DiscreteAttribute(String name, int index, TreeSet<String>  values) {
		super(name,index);
		this.values = (TreeSet<String>)values.clone();
	}
	
	int getNumberOfDistinctValues() {
		return values.size();
	}
	
	public Iterator<String> iterator() {
		return values.iterator();
	}
}