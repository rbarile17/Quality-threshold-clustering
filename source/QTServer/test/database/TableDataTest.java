package database;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TableDataTest {

	private static DbAccess db;
	private static TableSchema tableSchema;
	private static TableData tableData;
	
	@BeforeAll
	static void set() throws Exception{
		db = new DbAccess();
		db.initConnection();
	}
	
	@Test
	void testGetDistinctTransactions() throws Exception{
		tableData = new TableData(db, "empt");
		assertThrows(EmptySetException.class, () -> tableData.getDistinctTransactions());
		tableData = new TableData(db, "gfgfds");
		assertThrows(SQLException.class, () -> tableData.getDistinctTransactions());
	}
	
	@Test
	void testGetAggregateColumnValue() throws Exception{
		tableData = new TableData(db, "empt");
		tableSchema = new TableSchema(db, "empt");
		assertThrows(NoValueException.class, 
					() -> tableData.getAggregateColumnValue("empt", tableSchema.getColumn(0), QUERY_TYPE.MAX));
		tableData = new TableData(db, "test");
		tableSchema = new TableSchema(db, "test");
		assertEquals(2.0,tableData.getAggregateColumnValue("test", tableSchema.getColumn(0), QUERY_TYPE.MAX));
	}
}
