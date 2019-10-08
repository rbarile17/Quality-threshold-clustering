package mining;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import data.Data;
import data.Tuple;

class ClusterSetTest {
	private static ClusterSet c;
	private static Data data;
	private static Cluster cluster1, cluster2;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		data = new Data("test");
		QTMiner qt = new QTMiner(0.5);
		qt.compute(data);
		c = qt.getC();
		
		Iterator<Cluster> it = c.iterator();
		cluster1 = it.next();
		cluster2 = it.next();
	}

	@Test
	void testToList() {
		Tuple centroid1 = cluster1.getCentroid();
		Tuple centroid2 = cluster2.getCentroid();
		List<List<String>> l = new LinkedList<List<String>>();
		
		l.add(centroid1.toList());
		l.add(centroid2.toList());
		
		assertEquals(l, c.toList());
	}
	
	@Test
	void testAvgDistanceList() {
		LinkedList<Double> l = new LinkedList<Double>();
		
		l.add(cluster1.getAvgDistance(data));
		l.add(cluster2.getAvgDistance(data));
		
		assertEquals(l, c.avgDistanceList(data));
	}
	
	@Test
	void testToString() {
		String expected = cluster1.toString() + "\n" + cluster2.toString() + "\n";
		
		assertEquals(expected, c.toString());
	}
	
	@Test
	void testToString2() {
		String expected = "0:" + cluster1.toString(data) + "\n\n" + "1:" + cluster2.toString(data) + "\n\n";
		
		assertEquals(expected, c.toString(data));
	}

}
