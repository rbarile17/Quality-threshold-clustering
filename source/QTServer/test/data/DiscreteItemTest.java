package data;

import static org.junit.jupiter.api.Assertions.*;

import java.util.TreeSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DiscreteItemTest {

	private static DiscreteItem item;
	private static DiscreteAttribute d;
	
	@BeforeAll
	static void setUpBeforeClass() {
		TreeSet<String> set = new TreeSet<String>();
		set.add("a");
		set.add("c");
		set.add("b");
		d = new DiscreteAttribute("test", 0, set);
		item = new DiscreteItem(d, "a");
	}
	
	@Test
	void testDistance() {
		assertEquals(0, item.distance("a"));
		assertEquals(1, item.distance("b"));
	}
	
	@Test
	void testToString() {
		assertEquals("a",item.toString());
	}
}
