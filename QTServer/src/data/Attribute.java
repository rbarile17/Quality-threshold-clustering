package data;

import java.io.Serializable;

/**
 * Defines an abstract class that represents attribute like a name and index pair <br>
 * @author Pasquale De Marinis
 * @author Roberto Barile
 * @author Sergio Caputo
 */
public abstract class Attribute implements Serializable {
	
	private static final long serialVersionUID = 6159727540854323862L;
	protected String name;
	protected int index;

	/**
	 * @param name name of the attribute
	 * @param index index of the attribute
	 */
	Attribute(String name, int index) {
		this.name = name;
		this.index = index;
	}

	/**
	 * @return index of the attribute
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @return name of the attribute
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return name of the attribute
	 */
	public String toString() {
		return name;
	}
}