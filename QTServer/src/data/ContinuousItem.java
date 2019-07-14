package data;

import java.io.Serializable;

public class ContinuousItem extends Item implements Serializable {

	public ContinuousItem(ContinuousAttribute attribute , double value) {
		super(attribute,value);
	}
	
	@Override
	public double distance(Object a) {
		return java.lang.Math.abs( ((ContinuousAttribute)attribute).getScaledValue((double)this.getValue()) -
				((ContinuousAttribute)((ContinuousItem)this).getAttribute()).getScaledValue((double) a ));
	}

}
