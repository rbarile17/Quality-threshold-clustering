package data;

import java.util.Iterator;
import java.util.TreeSet;

class DiscreteAttribute extends Attribute implements Iterable<String>{
	TreeSet<String> values;
	 
	DiscreteAttribute(String name, int index, TreeSet<String> values) {
		super(name, index);
		
		this.values = values;
	}
	
	int getNumberOfDistinctValues() {
		return values.size();
	}
	
	
	public Iterator<String> iterator() {
		return this.values.iterator();
	}
}
