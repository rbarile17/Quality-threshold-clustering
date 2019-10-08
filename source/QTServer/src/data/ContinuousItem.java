package data;

import java.io.Serializable;

/**
 * Defines a class that represents continuous item <br>
 * @author Pasquale De Marinis
 * @author Roberto Barile
 * @author Sergio Caputo
 */
class ContinuousItem extends Item implements Serializable {

	
	private static final long serialVersionUID = 6279908508744923995L;

	/**
	 * @param attribute attribute of the item
	 * @param value value of the item
	 */
	ContinuousItem(ContinuousAttribute attribute, double value) {
		super(attribute, value);
	}

	/**
	 * Calculates distance between the object in input and the current continuous item
	 * @param a object used to calculate distance
	 * @return distance between a and continuous item
	 */
	@Override
	double distance(Object a) {
		return java.lang.Math.abs(((ContinuousAttribute) attribute).getScaledValue((double) this.getValue())
				- ((ContinuousAttribute) ((ContinuousItem) this).getAttribute()).getScaledValue((double) a));
	}

}
