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
		item = new DiscreteItem(d, "prova");
	}
	
	@Test
	void testDistance() {
		DiscreteItem item = new DiscreteItem(d, "prova");
		assertEquals(0, item.distance("prova"));
		assertEquals(1, item.distance("prova1"));
	}
	
	@Test
	void testToString() {
		assertEquals("prova",item.toString());
	}
}
