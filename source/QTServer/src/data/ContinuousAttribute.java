package data;

import java.io.Serializable;

/**
 * Defines a class that represents attribute like a numeric continuous attribute <br>
 * @author Pasquale De Marinis
 * @author Roberto Barile
 * @author Sergio Caputo
 */
class ContinuousAttribute extends Attribute implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 971180948596186229L;
	private double max;
	private double min;

	/**
	 * @param name name of the attribute
	 * @param index index of the attribute
	 * @param min lower value of the range of values(domain)
	 * @param max higher value of the range of values(domain)
	 */
	ContinuousAttribute(String name, int index, double min, double max) {
		super(name, index);
		this.min = min;
		this.max = max;
	}

	/**
	 * Calculates normalized value of the value in input
	 * @param v value to normalize
	 * @return value normalized
	 */
	double getScaledValue(double v) {
		return (v - min) / (max - min);
	}
}