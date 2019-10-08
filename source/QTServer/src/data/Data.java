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
import mining.Cluster;
import mining.ClusterSet;

/**
 * Defines a class that models the set of transitions (tuples) <br>
 * 
 * @author Pasquale De Marinis
 * @author Roberto Barile
 * @author Sergio Caputo
 */
public class Data {
	private List<Example> data = new ArrayList<Example>();

	private int numberOfExamples;
	private List<Attribute> attributeSet;

	/**
	 * @param table DB table name
	 * @throws NoValueException            if value in result set is not found
	 * @throws DatabaseConnectionException if connection with DB fails
	 * @throws SQLException                id DB access fails
	 * @throws EmptySetException           if empty result set is returned
	 */
	public Data(String table) throws NoValueException, DatabaseConnectionException, SQLException, EmptySetException {
		attributeSet = new LinkedList<Attribute>();

		DbAccess db;
		db = new DbAccess();
		db.initConnection();

		TableData tableData = new TableData(db, table);
		TableSchema tableSchema = new TableSchema(db, table);

		data = tableData.getDistinctTransactions();
		numberOfExamples = data.size();

		for (int i = 0; i < tableSchema.getNumberOfAttributes(); i++) {
			TableSchema.Column col = tableSchema.getColumn(i);
			if (col.isNumber())
				attributeSet.add(i,
						new ContinuousAttribute(col.getColumnName(), i,
								(double) tableData.getAggregateColumnValue(table, col, QUERY_TYPE.MIN),
								(double) tableData.getAggregateColumnValue(table, col, QUERY_TYPE.MAX)));
			else {
				TreeSet<String> distinctValues = new TreeSet<String>();
				distinctValues.addAll(tableData.getDistinctColumnValues(table, col));
				attributeSet.add(i, new DiscreteAttribute(col.getColumnName(), i, distinctValues));
			}
		}
	}

	/**
	 * @return numberofExamples of data
	 */
	public int getNumberOfExamples() {
		return numberOfExamples;
	}

	/**
	 * @return numberofAttributes of data
	 */
	public int getNumberOfAttributes() {
		return attributeSet.size();
	}

	/**
	 * @param exampleIndex   index of the example to be considered
	 * @param attributeIndex index of the attribute to be considered
	 * @return list of the example with exampleindex and attributeindex
	 */
	public Object getAttributeValue(int exampleIndex, int attributeIndex) {
		return data.get(exampleIndex).get(attributeIndex);
	}

	/**
	 * @param index
	 * @return list of attribute
	 */
	Attribute getAttribute(int index) {
		return attributeSet.get(index);
	}

	/**
	 * @return list of attributes names
	 */
	public List<String> getAttributesNames() {
		List<String> names = new LinkedList<String>();

		for (Attribute a : attributeSet) {
			names.add(a.toString());
		}

		return names;
	}

	/**
	 * @param C cluster set to be converted in list
	 * @return list of cluster
	 */
	public List<List<List<String>>> toList(ClusterSet C) {
		List<List<List<String>>> data = new LinkedList<List<List<String>>>();

		for (Cluster c : C) {
			LinkedList<List<String>> l = new LinkedList<List<String>>();
			for (int i : c) {
				List<String> tuple = new LinkedList<String>();
				tuple.add(String.valueOf(this.getItemSet(i).getDistance(c.getCentroid())));
				tuple.addAll(this.getItemSet(i).toList());
				l.add(tuple);
			}
			data.add(l);
		}

		return data;
	}

	/**
	 * Calculates distances of each tuple from the main centroid
	 * 
	 * @param C clusterset
	 * @return list of distances of each tuple from the main centroid
	 */
	public LinkedList<List<Double>> getDistances(ClusterSet C) {
		LinkedList<List<Double>> distances = new LinkedList<List<Double>>();
		Cluster last = null;
		for (Cluster element : C) {
			last = element;
		}
		for (Cluster c : C) {
			LinkedList<Double> l = new LinkedList<Double>();
			for (int i : c) {
				l.add(this.getItemSet(i).getDistance(last.getCentroid()));
			}
			distances.add(l);
		}
		return distances;
	}

	/**
	 * @param index of the row
	 * @return tuple with attribute-value in index row
	 */
	public Tuple getItemSet(int index) {
		Tuple tuple = new Tuple(this.getNumberOfAttributes());

		int i = 0;
		for (Attribute att : attributeSet) {
			if (att instanceof DiscreteAttribute)
				tuple.add(new DiscreteItem((DiscreteAttribute) att, (String) this.getAttributeValue(index, i)), i);
			else
				tuple.add(new ContinuousItem((ContinuousAttribute) att, (Double) this.getAttributeValue(index, i)), i);
			i++;
		}

		return tuple;
	}

	/**
	 * @return attribute set with examples in data
	 */
	public String toString() {
		String str = "";

		for (Attribute att : attributeSet)
			str += att + " ";
		str += "\n";

		for (Example e : data) {
			for (Object o : e)
				str += o + " ";
			str += "\n";
		}

		return str;
	}
}
