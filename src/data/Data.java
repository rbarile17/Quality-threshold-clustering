package data;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

public class Data {
	private Object data [][] =
		{
			{"sunny",    30.3,  "high",   "weak",   "no" },
			{"sunny",    30.3,  "high",   "strong", "no" },
			{"overcast", 30.0,  "high",   "weak",   "yes"},
			{"rain",     13.0,  "high",   "weak",   "yes"},
			{"rain",     0.0,   "normal", "weak",   "yes"},
			{"rain", 	 0.0,   "normal", "strong", "no" },
			{"overcast", 0.1,   "normal", "strong", "yes"},
			{"sunny",    13.0,  "high",   "weak",   "no" },
			{"sunny",    0.1,   "normal", "weak",   "yes"},
			{"rain",     12.0,  "normal", "weak",   "yes"},
			{"sunny",    12.5,  "normal", "strong", "yes"},
			{"overcast", 12.5,  "high",   "strong", "yes"},
			{"overcast", 29.21, "normal", "weak",   "yes"},
			{"rain",     12.5,  "high",   "strong", "no" }
		}; 
	private int numberOfExamples;
	List<Attribute> attributeSet;
	
	public Data(){
		numberOfExamples=14;		 
		attributeSet = new LinkedList<Attribute>();
		
		//outlookValues
		TreeSet<String> outLookValues = new TreeSet<String>();
		outLookValues.add("overcast");
		outLookValues.add("rain");
		outLookValues.add("sunny");
		attributeSet.add(new DiscreteAttribute("Outlook", 0, outLookValues));

		//temperatureValues
		attributeSet.add(new ContinuousAttribute("Temperature", 1, 3.2, 38.7));
		
		//humidityValues
		TreeSet<String> humidityValues = new TreeSet<String>();
		humidityValues.add("high");
		humidityValues.add("normal");
		attributeSet.add(new DiscreteAttribute("Humidity", 2, humidityValues));

		//windValues
		TreeSet<String> windValues = new TreeSet<String>();
		windValues.add("strong");
		windValues.add("weak");
		attributeSet.add(new DiscreteAttribute("Wind", 3, windValues));
		
		//playTennisValues
		TreeSet<String> playTennisValues = new TreeSet<String>();
		playTennisValues.add("no");
		playTennisValues.add("yes");
		attributeSet.add(new DiscreteAttribute("PlayTennis", 4, playTennisValues));
	}
	
	public int getNumberOfExamples(){
		return numberOfExamples;
	}
	
	public int getNumberOfAttributes(){
		return attributeSet.size();
	}	
	
	public Object getAttributeValue(int exampleIndex, int attributeIndex){
		return data[exampleIndex][attributeIndex];
	}
	
	Attribute getAttribute(int index){
		return attributeSet.get(index);
	}

	public Tuple getItemSet(int index) {
		Tuple tuple = new Tuple(this.getNumberOfAttributes());
		
		int i=0;
		for(Attribute att : attributeSet) {
			if (att instanceof DiscreteAttribute)
				tuple.add(new DiscreteItem((DiscreteAttribute) att, (String) data[index][i]), i);
			else
				tuple.add(new ContinuousItem((ContinuousAttribute) att, (Double) data[index][i]), i);
			i++;
		}
		
		return tuple;
	}
	
	public String toString(){
		String str="";
		
		for(Attribute att : attributeSet) {
			str += att + " ";
		}
		str += "\n";
		
		for(int i=0; i<this.getNumberOfExamples(); i++) {
			str += (i+1) + ": ";
			for(int j=0; j<this.getNumberOfAttributes()-1; j++) {
				str += this.getAttributeValue(i, j) + ", ";
			}
			str += this.getAttributeValue(i, this.getNumberOfAttributes()-1);
			str += "\n";
		}
		
		return str;
	}

	public static void main(String args[]){
		Data trainingSet=new Data();
		System.out.println(trainingSet);
	}
}
