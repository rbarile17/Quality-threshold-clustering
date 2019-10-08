package Server;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LogFileTest {

	private static File testFile;
	private static BufferedReader reader;
	private static LogFile log;

	@BeforeEach
	void createFile() throws Exception {
		testFile = new File("logTest.txt");
		if (testFile.exists())
			testFile.delete();
		log = new LogFile("logTest.txt");
	}

	@AfterEach
	void deleteFile() throws Exception {
		reader.close();
		testFile.delete();
	}

	@Test
	void testWriteLine() throws IOException {
		log.writeLine("test");
		testFile = new File("logTest.txt");
		reader = new BufferedReader(new FileReader(testFile));
		String testString = reader.readLine();
		assertTrue(testString.contains("test"));

		LogFile logNull = new LogFile("");
		assertThrows(NullPointerException.class, () -> logNull.writeLine("prova"));
	}

	@Test
	void testClose() throws Exception {
		reader = new BufferedReader(new FileReader(testFile));
		assertAll(() -> log.close());
	}

}
