package data;

import java.io.Serializable;

/**
 * Defines a class that represents item like a discrete attribute and discrete value <br>
 * @author Barile Roberto
 * @author De Marinis Pasquale
 * @author Caputo Sergio
 */
class DiscreteItem extends Item implements Serializable {
	
	private static final long serialVersionUID = 8816016030660681339L;

	/**
	 * @param attribute discrete attribute in discrete item
	 * @param value discrete value in discrete item
	 */
	DiscreteItem(DiscreteAttribute attribute, Object value) {
		super(attribute, value);
	}

	/**
	 * @param a object used to calculate distance
	 */
	double distance(Object a) {
		if (this.getValue().equals(a))
			return 0;
		else
			return 1;
	}
}
