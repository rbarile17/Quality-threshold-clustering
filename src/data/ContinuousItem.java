package data;

public class ContinuousItem extends Item {
	ContinuousItem(ContinuousAttribute attribute, Double value) {
		super(attribute, value);
	}
	
	@Override
	double distance(Object a) {
		double dist = ((ContinuousAttribute) this.getAttribute()).getScaledValue((Double) this.getValue())
				    - ((ContinuousAttribute) this.getAttribute()).getScaledValue((Double) a);
		
		return Math.abs(dist);
	}

}
