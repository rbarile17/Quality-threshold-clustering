package data;

import java.util.List;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.ArrayList;
import database.TableData;
import database.Example;
import database.NoValueException;
import database.QUERY_TYPE;
import database.DbAccess;
import database.DatabaseConnectionException;
import database.EmptySetException;
import java.sql.SQLException;
import database.TableSchema;

public class Data {
	private List<Example> data = new ArrayList<Example>(); 

	private int numberOfExamples;
	private List<Attribute> attributeSet;
	
	public Data(String table) throws NoValueException{		 
		
		attributeSet = new LinkedList<Attribute>();
		
		/*
		TreeSet<String> outLookValues = new TreeSet<String>();
		outLookValues.add("Overcast");
		outLookValues.add("Rain");
		outLookValues.add("Sunny");
		attributeSet.add(0, new DiscreteAttribute("Outlook", 0, outLookValues));
		
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
		*/

		DbAccess db;
		try {
			db = new DbAccess();
			try {
				db.initConnection();
			} catch (DatabaseConnectionException exc) {
				System.out.println(exc.getMessage());
			}
			TableData tableData = new TableData(db,table);
			TableSchema tableSchema = new TableSchema(db,table);
			
			data = tableData.getDistinctTransactions();
			numberOfExamples=data.size();	
			
			for(int i = 0; i < tableSchema.getNumberOfAttributes();i++) {
				TableSchema.Column col = tableSchema.getColumn(i);
				if(col.isNumber()) {
					attributeSet.add(i,new ContinuousAttribute(col.getColumnName(), i, 
									(double) tableData.getAggregateColumnValue(table, col, QUERY_TYPE.MIN),
									(double)tableData.getAggregateColumnValue(table, col, QUERY_TYPE.MAX)));
				}
				else {
					TreeSet<String> distinctValues = new TreeSet<String>();
					distinctValues.addAll(tableData.getDistinctColumnValues(table, col));
					attributeSet.add(i , new DiscreteAttribute(col.getColumnName(),i, distinctValues ));
				}
			}
			
			
		} catch (SQLException  | EmptySetException e ) {
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
	
	public List<String> getAttributesNames() {
		List<String> names = new LinkedList<String>();  
		
		for(Attribute a : attributeSet) {
			names.add(a.toString());
		}
		
		return names;
	}
	
	public Tuple getItemSet(int index) {
		Tuple tuple = new Tuple(this.getNumberOfAttributes());
		
		int i=0;
		for(Attribute att : attributeSet) {
			if (att instanceof DiscreteAttribute)
				tuple.add(new DiscreteItem((DiscreteAttribute) att, (String) this.getAttributeValue(index, i)), i);
			else
				tuple.add(new ContinuousItem((ContinuousAttribute) att, (Double) this.getAttributeValue(index, i)), i);
			i++;
		}
		
		return tuple;
	}
	
	public String toString(){
		String str="";
		
		for(Attribute att : attributeSet) 
			str += att + " ";
		str += "\n";
		
		for(Example e : data) {
			for(Object o : e) 
				str += o + " ";
			str += "\n";
		}
		
		return str;
	}
}
