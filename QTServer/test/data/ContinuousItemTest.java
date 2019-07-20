package data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ContinuousItemTest {

	@Test
	void distanceTest() {
		double min = 3.0;
		double max = 7.0;
		ContinuousAttribute a = new ContinuousAttribute("test",0,min,max);
		ContinuousItem A = new ContinuousItem(a,6.0);
		assertEquals(0.25,A.distance(5.0));
	}

}
