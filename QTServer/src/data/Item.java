package data;

import java.io.Serializable;

/**
 * Defines an abstract class that represents item like a attribute and value pair <br>
 * @author Pasquale De Marinis
 * @author Roberto Barile
 * @author Sergio Caputo
 */
public abstract class Item implements Serializable {
	
	private static final long serialVersionUID = -349107164823808253L;
	protected Attribute attribute;
	protected Object value;

	/**
	 * @param attribute attribute of item
	 * @param value value of item
	 */
	public Item(Attribute attribute, Object value) {
		this.attribute = attribute;
		this.value = value;
	}

	/**
	 * @return attribute of item
	 */
	public Attribute getAttribute() {
		return attribute;
	}

	/**
	 * @return value of item
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @return value of item
	 */
	public String toString() {
		return value.toString();
	}

	/**
	 * Calculates distance between the object in input and the item
	 * @param a object used to calculate distance
	 * @return distance of a
	 */
	public abstract double distance(Object a);
}
