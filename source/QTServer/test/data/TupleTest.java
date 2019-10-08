package data;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class TupleTest {
	
	private static Tuple t;
	private static DiscreteAttribute d;
	private static Tuple centroid;
	
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
	void testAvgDistance() throws Exception{
		Data data;
		
		data = new Data("test2");
		centroid = new Tuple(2);
		
		TreeSet<String> set = new TreeSet<String>();
		set.add("aaa");
		set.add("aab");
		set.add("sdf");

		ContinuousAttribute ID = new ContinuousAttribute("ID", 0, 0.1, 5.8);
		ContinuousItem item2 = new ContinuousItem(ID, 0.1);
		centroid.add(item2, 0);
		
		DiscreteAttribute value = new DiscreteAttribute("value", 0, set);
		DiscreteItem item = new DiscreteItem(value, "aaa");
		centroid.add(item, 1);
		
		Set<Integer> clusteredData = new HashSet<Integer>();
		clusteredData.add(0);
		clusteredData.add(1);
		clusteredData.add(2);
				
		assertEquals(0.7485380116959064, centroid.avgDistance(data, clusteredData));
	}

	@Test
	void testToList() {
		LinkedList<String> centroidAsList = new LinkedList<String>();
		
		centroidAsList.add("0.1");
		centroidAsList.add("aaa");
		
		assertEquals(centroidAsList, centroid.toList());
	}

}
