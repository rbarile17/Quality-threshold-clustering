package data;

import java.io.Serializable;

public abstract class Attribute implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6159727540854323862L;
	protected String name;
	protected int index;
	
	Attribute(String name, int index) {
		this.name = name;
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		return name;
	}
}