package data;

import java.io.Serializable;

public class DiscreteItem extends Item implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8816016030660681339L;

	public DiscreteItem(DiscreteAttribute attribute, Object value ) {
		super(attribute,value);
	}
	
	public double distance(Object a) {
		if(this.getValue().equals(a))
			return 0;
		else 
			return 1;
	}
}
