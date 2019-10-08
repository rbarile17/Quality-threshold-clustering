package data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ContinuousAttributeTest {
	@Test
	void getScaledValueTest() {
		double min = 5.0;
		double max = 7.0;
		double n = 6.0;
		ContinuousAttribute c = new ContinuousAttribute("test",0,min,max);
		assertEquals(0.5, c.getScaledValue(n));
	}

}
