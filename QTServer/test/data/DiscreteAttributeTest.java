package data;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DiscreteAttributeTest {

	private static DiscreteAttribute d;
	private static TreeSet<String> set = new TreeSet<String>();
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		TreeSet<String> set = new TreeSet<String>();
		set.add("a");
		set.add("c");
		set.add("b");
		d = new DiscreteAttribute("test",0,set);
	}
	
	@Test
	void testGetNumberOfDistinctValues() {
		assertEquals(3,d.getNumberOfDistinctValues());
	}

	@Test
	void testGetIndex() {
		assertEquals(0,d.getIndex());
	}
	
	@Test
	void testGetName() {
		assertEquals("test",d.getName());
	}
	
	@Test
	void testToString() {
		assertEquals("test",d.toString());
	}

}
