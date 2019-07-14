package data;

import java.util.List;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.ArrayList;
import database.TableData;
import database.Example;
import database.DbAccess;
import database.DatabaseConnectionException;
import database.EmptySetException;
import java.sql.SQLException;

public class Data {
	/*
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
		};*/
	private List<Example> data=new ArrayList<Example>(); 

	private int numberOfExamples;
	private List<Attribute> attributeSet;
	
	
	public Data(String table){
		
		numberOfExamples=14;		 
		
		attributeSet = new LinkedList<Attribute>();
		
		TreeSet<String> outLookValues =new TreeSet<String>();
		outLookValues.add("Overcast");
		outLookValues.add("Rain");
		outLookValues.add("Sunny");
		attributeSet.add(0, new DiscreteAttribute("Outlook",0, outLookValues));
		
		attributeSet.add(1,  new ContinuousAttribute("Temperture", 1, 0, 30.3));

		TreeSet<String> humidityValues = new TreeSet<String>();
		humidityValues.add("High");
		humidityValues.add("Normal");
		attributeSet.add(2, new DiscreteAttribute("Humidity",2, humidityValues));

		TreeSet<String> windValues = new TreeSet<String>();
		windValues.add("Weak");
		windValues.add("Strong");
		attributeSet.add(3, new DiscreteAttribute("Wind",3, windValues));
		
		TreeSet<String> playTennisValues = new TreeSet<String>();
		playTennisValues.add("Yes");
		playTennisValues.add("No");
		attributeSet.add(4, new DiscreteAttribute("PlayTennis",4, playTennisValues));
		
		DbAccess db;
		try {
			db = new DbAccess();
			try {
				db.initConnection();
			} catch (DatabaseConnectionException exc) {
				System.out.println(exc.getMessage());
			}
				data = new TableData(db).getDistinctTransazioni(table);
		} catch (SQLException  | EmptySetException e ) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public int getNumberOfExamples(){
		return numberOfExamples;
	}
	
	public int getNumberOfAttributes(){
		return attributeSet.size();
	}
	
	
	
	public Object getAttributeValue(int exampleIndex, int attributeIndex){
		return data.get(exampleIndex).get(attributeIndex);
	}
	
	Attribute getAttribute(int index){
		return attributeSet.get(index);
	}
	
	
	public String toString(){
		String output = new String();
		output = "";
		for(int i = 0; i < this.getNumberOfAttributes(); i++) {
			output = output+attributeSet.get(i).getName()+",";
		}
		output = output+"\n";
		for(int i = 0; i < numberOfExamples; i++) {
			output = output + (i+1) + ": ";
			for(int j = 0; j < this.getNumberOfAttributes() ; j++ )
				output = output + data.get(i).get(j)+",";
			output = output +"\n";
		}
		return output;
	}

	public Tuple getItemSet(int index){
		Tuple tuple=new Tuple(attributeSet.size());
		for(int i=0;i<attributeSet.size();i++) {
			if(attributeSet.get(i) instanceof DiscreteAttribute)
				tuple.add(new DiscreteItem((DiscreteAttribute)attributeSet.get(i),(String)data.get(index).get(i)),i);
			else
				tuple.add(new ContinuousItem((ContinuousAttribute)attributeSet.get(i),(Double)data.get(index).get(i)),i);
		}
		return tuple;
	}
}
