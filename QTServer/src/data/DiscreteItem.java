package data;

import java.io.Serializable;

public class DiscreteItem extends Item implements Serializable{
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
