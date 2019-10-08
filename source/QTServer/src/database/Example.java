package database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Defines a class that describes a transition read from the DB<br>
 * @author Pasquale De Marinis
 * @author Roberto Barile
 * @author Sergio Caputo
 */
public class Example implements Comparable<Example>, Iterable<Object> {
	private List<Object> example = new ArrayList<Object>();

	/**
	 * @param o object to add to the example
	 */
	public void add(Object o) {
		example.add(o);
	}

	/**
	 * @param i index of the object to get
	 * @return object in index position in the example
	 */
	public Object get(int i) {
		return example.get(i);
	}

	/**
	 * @param ex example to be compared with
	 * 
	 */
	public int compareTo(Example ex) {

		int i = 0;
		for (Object o : ex.example) {
			if (!o.equals(this.example.get(i)))
				return ((Comparable) o).compareTo(example.get(i));
			i++;
		}
		return 0;
	}

	/**
	 * @return the example
	 */
	public String toString() {
		String str = "";
		for (Object o : example)
			str += o.toString() + " ";
		return str;
	}

	/**
	 * @return example iterator
	 */
	@Override
	public Iterator<Object> iterator() {
		return example.iterator();
	}
}