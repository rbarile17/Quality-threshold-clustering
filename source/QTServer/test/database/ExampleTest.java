package database;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ExampleTest {

	@Test
	void testToString() {
		String expected = "a b ";
		Example e = new Example();
		e.add("a");
		e.add("b");
		
		assertEquals(expected, e.toString());
	}

}
