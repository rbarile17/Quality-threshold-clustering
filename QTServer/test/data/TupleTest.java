package data;

import static org.junit.jupiter.api.Assertions.*;

import java.util.TreeSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import mining.ClusterSet;
import mining.QTMiner;

class TupleTest {
	
	private static Tuple t;
	private static DiscreteAttribute d;
	
	@BeforeAll
	static void setUpBeforeClass() {
		TreeSet<String> set = new TreeSet<String>();
		set.add("a");
		set.add("c");
		set.add("b");
		set.add("d");
		d = new DiscreteAttribute("test", 0, set);
		DiscreteItem item = new DiscreteItem(d, "a");
		DiscreteItem item2 = new DiscreteItem(d, "b");
		t = new Tuple(2);
		t.add(item, 0);
		t.add(item2, 1);
	}

	@Test
	void testAdd() {
		DiscreteItem item3 = new DiscreteItem(d, "c");
		DiscreteItem item4 = new DiscreteItem(d, "d");
		t.add(item3, 2);
		t.add(item4, 3);
		assertEquals(item3,t.get(2));
		assertEquals(item4,t.get(3));
	}

	@Test
	void testGetDistance() {
		Tuple t2 = new Tuple(4);
		t2.add(new DiscreteItem(d,"a"), 0);
		t2.add(new DiscreteItem(d,"b"), 1);
		t2.add(new DiscreteItem(d,"a"), 2);
		t2.add(new DiscreteItem(d,"b"), 3);
		assertEquals(2, t.getDistance(t2));
	}

	@Test
	void testAvgDistance() {
		data = new Data("test");
		
		fail("Not yet implemented");
	}

	@Test
	void testToList() {
		fail("Not yet implemented");
	}

}
