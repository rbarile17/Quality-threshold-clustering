package mining;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import data.Data;

class ClusterTest {
	private static Data data;
	private static Cluster cluster;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		data = new Data("test");
		QTMiner qt = new QTMiner(0.5);
		qt.compute(data);
		ClusterSet c = qt.getC();

		cluster = c.iterator().next();
	}
	
	@Test
	void testAddData() {
		cluster.addData(25);
		assertTrue(cluster.contain(25));
	}

	@Test
	void testRemoveTuple() {
		cluster.removeTuple(25);
		assertFalse(cluster.contain(25));
	}
	
	@Test
	void testToString() {
		String expected = "Centroid=(2.0, b)";
		
		assertEquals(expected, cluster.toString());
	}
	
	@Test
	void testAvgDistance() {
		assertEquals(cluster.getAvgDistance(data), 0.0);
	}
	
	@Test 
	void testToString2() {
		String expected = "Centroid=(2.0, b)\nExamples:\n[2.0 b ] dist=0.0\n\nAvgDistance=0.0";
		
		assertEquals(expected, cluster.toString(data));
	}
}
