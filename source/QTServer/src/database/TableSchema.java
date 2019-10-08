package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Defines a class that models the scheme of a table in the DB<br>
 * @author Pasquale De Marinis
 * @author Roberto Barile
 * @author Sergio Caputo
 */
public class TableSchema {
	DbAccess db;
	String name;

	/**
	 * Defines a class that models the columns in the scheme
	 *
	 */
	public class Column {
		private String name;
		private String type;

		/**
		 * @param name header of the column
		 * @param type type of the column
		 */
		Column(String name, String type) {
			this.name = name;
			this.type = type;
		}

		/**
		 * @return name of the column
		 */
		public String getColumnName() {
			return name;
		}

		/**
		 * @return true if type is number
		 */
		public boolean isNumber() {
			return type.equals("number");
		}

		/**
		 * @return the column model
		 */
		public String toString() {
			return name + ":" + type;
		}
	}

	List<Column> tableSchema = new ArrayList<Column>();

	/**
	 * @param db connection with DB
	 * @param tableName name of the table to schematize
	 * @throws SQLException if DB table access fails
	 */
	public TableSchema(DbAccess db, String tableName) throws SQLException {
		this.db = db;
		this.name = tableName;
		HashMap<String, String> mapSQL_JAVATypes = new HashMap<String, String>();
		// http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
		mapSQL_JAVATypes.put("CHAR", "string");
		mapSQL_JAVATypes.put("VARCHAR", "string");
		mapSQL_JAVATypes.put("LONGVARCHAR", "string");
		mapSQL_JAVATypes.put("BIT", "string");
		mapSQL_JAVATypes.put("SHORT", "number");
		mapSQL_JAVATypes.put("INT", "number");
		mapSQL_JAVATypes.put("LONG", "number");
		mapSQL_JAVATypes.put("FLOAT", "number");
		mapSQL_JAVATypes.put("DOUBLE", "number");
		mapSQL_JAVATypes.put("DECIMAL", "number");

		Connection con = db.getConnection();
		DatabaseMetaData meta = con.getMetaData();
		ResultSet res = meta.getColumns(null, null, tableName, null);

		while (res.next()) {
			if (mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME")))
				tableSchema.add(
						new Column(res.getString("COLUMN_NAME"), mapSQL_JAVATypes.get(res.getString("TYPE_NAME"))));
		}

		res.close();
	}

	/**
	 * @return number of attributes
	 */
	public int getNumberOfAttributes() {
		return tableSchema.size();
	}

	/**
	 * @param index index of the column to get
	 * @return column in index position
	 */
	public Column getColumn(int index) {
		return tableSchema.get(index);
	}

	/**
	 * @return table name
	 */
	public String getTableName() {
		return name;
	}
}
