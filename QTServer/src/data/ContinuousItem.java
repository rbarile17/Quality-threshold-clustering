package data;

import java.io.Serializable;

public class ContinuousItem extends Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6279908508744923995L;

	public ContinuousItem(ContinuousAttribute attribute, double value) {
		super(attribute, value);
	}

	@Override
	public double distance(Object a) {
		return java.lang.Math.abs(((ContinuousAttribute) attribute).getScaledValue((double) this.getValue())
				- ((ContinuousAttribute) ((ContinuousItem) this).getAttribute()).getScaledValue((double) a));
	}

}
