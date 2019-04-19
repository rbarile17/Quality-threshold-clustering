class Data {
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
		}; //una matrice nXm di tipo Object dove ogni riga modella una transazione
	private int numberOfExamples;	//cardinalità dell’insieme di transazioni 
	private Attribute attributeSet[];	//un vettore degli attributi in ciascuna tupla (schema della tabella di dati)
	
	Data(){
		numberOfExamples=14;		 
		attributeSet = new Attribute[5];
		
		//outlookValues
		String outLookValues[]=new String[3];
		outLookValues[0]="overcast";
		outLookValues[1]="rain";
		outLookValues[2]="sunny";
		attributeSet[0] = new DiscreteAttribute("Outlook", 0, outLookValues);

		//temperatureValues
		String temperatureValues[]=new String[3];
		temperatureValues[0]="cool";
		temperatureValues[1]="hot";
		temperatureValues[2]="mild";
		attributeSet[1] = new DiscreteAttribute("Temperature", 1, temperatureValues);
		
		//humidityValues
		String humidityValues[]=new String[2];
		humidityValues[0]="high";
		humidityValues[1]="normal";
		attributeSet[2] = new DiscreteAttribute("Humidity", 2, humidityValues);

		//windValues
		String windValues[]=new String[3];
		windValues[0]="strong";
		windValues[1]="weak";
		attributeSet[3] = new DiscreteAttribute("Wind", 3, windValues);
		
		//playTennisValues
		String playTennisValues[]=new String[2];
		windValues[0]="no";
		windValues[1]="yes";
		attributeSet[4] = new DiscreteAttribute("PlayTennis", 4, playTennisValues);
	}
	
	int getNumberOfExamples(){
		return numberOfExamples;
	}
	
	int getNumberOfAttributes(){
		return attributeSet.length;
	}	
	
	Object getAttributeValue(int exampleIndex, int attributeIndex){
		return data[exampleIndex][attributeIndex];
	}
	
	Attribute getAttribute(int index){
		return attributeSet[index];
	}

	Tuple getItemSet(int index) {
		Tuple tuple = new Tuple(attributeSet.length);
		for(int i=0; i<attributeSet.length; i++)
			tuple.add(new DiscreteItem((DiscreteAttribute)attributeSet[i], (String)data[index][i]), i);
		
		return tuple;
	}
	
	public String toString(){
		String str="";
		
		for(int i=0; i<this.getNumberOfAttributes()-1; i++) {
			str += attributeSet[i] + ", ";
		}
		str += attributeSet[this.getNumberOfAttributes()-1];
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
