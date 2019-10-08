package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import database.TableSchema.Column;

/**
 * Defines a class that models the set of transitions stored in a table<br>
 * 
 * @author Pasquale De Marinis
 * @author Roberto Barile
 * @author Sergio Caputo
 */
public class TableData {

	DbAccess db;
	TableSchema tSchema;

	/**
	 * @param db    connection with the DB
	 * @param table table to handle
	 * @throws SQLException if DB access fails
	 */
	public TableData(DbAccess db, String table) throws SQLException {
		this.db = db;
		tSchema = new TableSchema(db, table);
	}

	/**
	 * @return list of transitions in the table
	 * @throws SQLException      if DB access fails
	 * @throws EmptySetException if resultset returned is empty
	 */
	public List<Example> getDistinctTransactions() throws SQLException, EmptySetException {
		LinkedList<Example> transSet = new LinkedList<Example>();
		Statement statement;

		String query = "select distinct ";

		for (int i = 0; i < tSchema.getNumberOfAttributes(); i++) {
			Column c = tSchema.getColumn(i);
			if (i > 0)
				query += ",";
			query += c.getColumnName();
		}
		if (tSchema.getNumberOfAttributes() == 0)
			throw new SQLException();
		query += (" FROM " + tSchema.getTableName());

		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		boolean empty = true;
		while (rs.next()) {
			empty = false;
			Example currentTuple = new Example();
			for (int i = 0; i < tSchema.getNumberOfAttributes(); i++)
				if (tSchema.getColumn(i).isNumber())
					currentTuple.add(rs.getDouble(i + 1));
				else
					currentTuple.add(rs.getString(i + 1));
			transSet.add(currentTuple);
		}
		rs.close();
		statement.close();
		if (empty)
			throw new EmptySetException();

		return transSet;
	}

	/**
	 * @param table  name of the table
	 * @param column name of the column in the table
	 * @return set of ordered distinct values in the column of the table
	 * @throws SQLException if DB access fails
	 */
	public Set<String> getDistinctColumnValues(String table, Column column) throws SQLException {
		Set<String> valueSet = new TreeSet<String>();
		Statement statement;

		String query = "select distinct ";

		query += column.getColumnName();
		query += (" FROM " + table);
		query += (" ORDER BY " + column.getColumnName());

		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		while (rs.next()) {
			if (column.isNumber())
				valueSet.add(String.valueOf(rs.getDouble(1)));
			else
				valueSet.add(rs.getString(1));
		}

		rs.close();
		statement.close();

		return valueSet;
	}

	/**
	 * @param table     name of the table
	 * @param column    name of the column
	 * @param aggregate aggregate sql operator
	 * @return aggregate required
	 * @throws SQLException     if DB access fails
	 * @throws NoValueException if result set returned is empty
	 */
	public Object getAggregateColumnValue(String table, Column column, QUERY_TYPE aggregate)
			throws SQLException, NoValueException {
		Statement statement;
		Object value = null;
		String aggregateOp = "";

		String query = "select ";
		if (aggregate == QUERY_TYPE.MAX)
			aggregateOp += "max";
		else
			aggregateOp += "min";
		query += aggregateOp + "(" + column.getColumnName() + ") FROM " + table + " HAVING " + aggregate + "("
				+ column.getColumnName() + ")" + "is not null";
		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);

		if (rs.next()) {
			if (column.isNumber())
				value = rs.getDouble(1);
			else
				value = rs.getString(1);

		}
		statement.close();
		if (value == null)
			throw new NoValueException("No " + aggregateOp + " on " + column.getColumnName());
		return value;

	}
}
