package data;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import mining.ClusterSet;
import mining.QTMiner;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataTest {

	private static Data data;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		data = new Data("test");
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testGetNumberOfExamples() {
		assertEquals(2,data.getNumberOfExamples());
	}

	@Test
	void testGetNumberOfAttributes() {
		assertEquals(2,data.getNumberOfAttributes());
	}

	@Test
	void testGetAttributeValue() {
		assertEquals("b",data.getAttributeValue(1, 1));
	}

	@Test
	void testGetAttribute() {
		assertEquals(1,data.getAttribute(1).getIndex());
	}

	@Test
	void testGetAttributesNames() {
		List<String> names = new LinkedList<String>();
		names.add("ID");
		names.add("stringa");
		assertEquals(names,data.getAttributesNames());
	}

	@Test
	void testToList() throws Exception {
		QTMiner qt = new QTMiner(0.5);
		qt.compute(data);
		ClusterSet c = qt.getC();
		List<List<List<String>>> list = new LinkedList<List<List<String>>>();
		LinkedList<List<String>> c1 = new LinkedList<List<String>>();
		List<String> t1 = new LinkedList<String>();
		t1.add("0.0");
		t1.add("2.0");
		t1.add("b");
		c1.add(t1);
		list.add(c1);
		LinkedList<List<String>> c2 = new LinkedList<List<String>>();
		List<String> t2 = new LinkedList<String>();
		t2.add("0.0");
		t2.add("1.0");
		t2.add("a");
		c2.add(t2);
		list.add(c2);
		assertEquals(list,data.toList(c));
		
	}

	@Test
	void testGetDistances() throws Exception{
		QTMiner qt = new QTMiner(0.5);
		qt.compute(data);
		ClusterSet c = qt.getC();
		LinkedList<List<Double>> distances = new LinkedList<List<Double>>();
		LinkedList<Double> c1 = new LinkedList<Double>();
		LinkedList<Double> c2 = new LinkedList<Double>();
		c1.add(2.0);
		c2.add(0.0);
		distances.add(c1);
		distances.add(c2);
		assertEquals(distances,data.getDistances(c));
	}

	@Test
	void testGetItemSet() {
		fail("Not yet implemented");
	}

	@Test
	void testToString() {
		String test = "ID stringa \n1.0 a \n2.0 b \n";
		assertEquals(test,data.toString());
	}

}
