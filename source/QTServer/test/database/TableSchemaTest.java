package database;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TableSchemaTest {
	private static DbAccess db;
	private static TableSchema tableSchema;
	
	@BeforeAll
	static void set() throws Exception{
		db = new DbAccess();
		db.initConnection();
		
		tableSchema = new TableSchema(db, "empt");
	}
	@Test
	void testToString() {
		String expected = "id:number";
		assertEquals(expected, tableSchema.getColumn(0).toString());
	}

}
