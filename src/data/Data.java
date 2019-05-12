package data;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

public class Data {
	private Object data [][] =
		{
			{"sunny",    "hot",  "high",   "weak",   "no" },
			{"sunny",    "hot",  "high",   "strong", "no" },
			{"overcast", "hot",  "high",   "weak",   "yes"},
			{"rain",     "mild", "high",   "weak",   "yes"},
			{"rain",     "cool", "normal", "weak",   "yes"},
			{"rain", 	 "cool", "normal", "strong", "no" },
			{"overcast", "cool", "normal", "strong", "yes"},
			{"sunny",    "mild", "high",   "weak",   "no" },
			{"sunny",    "cool", "normal", "weak",   "yes"},
			{"rain",     "mild", "normal", "weak",   "yes"},
			{"sunny",    "mild", "normal", "strong", "yes"},
			{"overcast", "mild", "high",   "strong", "yes"},
			{"overcast", "hot",  "normal", "weak",   "yes"},
			{"rain",     "mild", "high",   "strong", "no" }
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
		TreeSet<String> temperatureValues = new TreeSet<String>();
		temperatureValues.add("cool");
		temperatureValues.add("hot");
		temperatureValues.add("mild");
		attributeSet.add(new DiscreteAttribute("Temperature", 1, temperatureValues));
		
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
			tuple.add(new DiscreteItem((DiscreteAttribute) att, (String)data[index][i]), i);
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
