package Server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Defines a class that create and write a log file<br>
 * 
 * @author Pasquale De Marinis
 * @author Roberto Barile
 * @author Sergio Caputo
 */
class LogFile {

	private static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	private BufferedWriter writer;

	LogFile(String fileName) {
		File file;
		try {
			file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			this.writer = new BufferedWriter(new FileWriter(file, true));
		} catch (IOException e) {
			System.out.println("Path null");
		}
	}

	/**
	 * Write a new line on the log
	 * 
	 * @param line The line to write
	 * @throws IllegalArgumentException
	 */
	void writeLine(String line) throws IllegalArgumentException, NullPointerException, IOException {
		if (line == null) {
			throw new IllegalArgumentException("line may not be null");
		}
		String time = "[" + formatter.format(new Date(System.currentTimeMillis())) + "] ";
		this.writer.write(time);
		this.writer.write(line);
		this.writer.newLine();
		this.writer.flush();
	}

	/**
	 * Close the stream with the log file
	 */
	void close() throws IOException {
		this.writer.close();
	}
}