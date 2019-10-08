package mining;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import data.Data;

class QTMinerTest {

	private static QTMiner qt;
	private static QTMiner qt1;
	private static Data data;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		data = new Data("test");
		qt = new QTMiner(0.5);
		qt1 = new QTMiner(10);
	}
	
	@Test
	void testCompute() {
		assertThrows(ClusteringRadiusException.class, () ->  { qt1.compute(data); });
	}
	
	@Test
	void testSave() throws Exception{
		qt.save("test.dmp", data);
		QTMiner qtLoadTest = new QTMiner("test.dmp");
		assertEquals(qt.getC().toList(), qtLoadTest.getC().toList());
		
		String notFound = "notFound.dmp";
		File f = new File("files");
		
		if (Arrays.asList(f.list()).contains(notFound))
			assertAll(() -> new QTMiner(notFound));
		else
			assertThrows(FileNotFoundException.class, () -> new QTMiner(notFound));
	}

	@Test
	void testToString() {
		assertEquals(qt.getC().toString(), qt.toString());
	}
}
