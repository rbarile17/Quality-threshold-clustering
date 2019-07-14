package data;

import java.io.Serializable;

public class ContinuousAttribute extends Attribute implements Serializable{
	private double max;
	private double min;
	
	ContinuousAttribute(String name, int index, double min, double max) {
		super(name,index);
		this.min = min;
		this.max = max;
	}
	
	double getScaledValue(double v) {
		return (v-min)/(max-min);
	}
}