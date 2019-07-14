package data;

import java.io.Serializable;

public abstract class Item implements Serializable{
	protected Attribute attribute;
	protected Object value;
	
	public Item (Attribute attribute, Object value) {
		this.attribute = attribute;
		this.value = value;
	}
	
	public Attribute getAttribute() {
		return attribute;
	}
	
	public Object getValue() {
		return value;
	}
	
	public String toString() {
		return value.toString();
	}
	
	public abstract double distance(Object a);
}
