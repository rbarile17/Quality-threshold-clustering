class DiscreteAttribute extends Attribute{
	String values[];
	//array di oggetti String, uno per ciascun valore del dominio discreto. 
	//I  valori del dominio sono memorizzati in values seguendo un ordine lessicografico. 
	 
	DiscreteAttribute(String name, int index, String values[]) {
		super(name, index);
		
		this.values = values;
	}
	
	int getNumberOfDistinctValues() {
		return values.length;
	}
	
	String getValue(int i) {
		return values[i];
	}
}
