package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Needs a server running to work
 *
 */
class ServerModelTest {

	private static ServerModel serverModel;
	private static ServerModel serverModelFail;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		serverModel = new ServerModel("localhost", 8080);
		serverModelFail = new ServerModel("localhost", 8080);
		serverModelFail.close(true);
	}

	@Test
	void testLoadFile() throws IOException {
		ServerModel serverModel = new ServerModel("localhost", 8080);
		assertTrue(serverModel.loadFile("test"));
		LinkedList<LinkedList<String>> centroids = new LinkedList<LinkedList<String>>();
		centroids.add(new LinkedList<String>(Arrays.asList("2.0", "b")));
		centroids.add(new LinkedList<String>(Arrays.asList("1.0", "a")));
		assertEquals(centroids, serverModel.getCentroids());
		assertThrows(Error.class, () -> {
			serverModel.loadFile("nonexistentfile");
		});
		assertThrows(IOException.class, () -> {
			serverModelFail.loadFile("file");
		});
		serverModel.close(true);
	}

	@Test
	void testClusterDBTable() throws IOException {
		ServerModel serverModel = new ServerModel("localhost", 8080);
		assertTrue(serverModel.clusterDBTable("test", 0.5));
		ServerModel serverModelThrows = new ServerModel("localhost", 8080);
		assertThrows(Error.class, () -> {
			serverModelThrows.clusterDBTable("nonexistenttable", 0.5);
		});
		assertThrows(IOException.class, () -> {
			serverModelFail.clusterDBTable("test", 0.5);
		});
		serverModel.close(true);
	}

	@Test
	void testGetCentroidsNumber() throws IOException {
		ServerModel serverModel = new ServerModel("localhost", 8080);
		serverModel.clusterDBTable("test", 0.5);
		assertEquals(2, serverModel.getCentroidsNumber());
		serverModel.close(true);
	}

	@Test
	void testGetAttributesNames() throws Exception {
		ServerModel serverModel = new ServerModel("localhost", 8080);
		serverModel.clusterDBTable("test", 0.5);
		serverModel.getCentroidsNumber();
		assertEquals(new LinkedList<String>(Arrays.asList("ID", "stringa")), serverModel.getAttributesNames());
	}

	@Test
	void testGetCentroids() throws IOException {
		ServerModel serverModel = new ServerModel("localhost", 8080);
		serverModel.clusterDBTable("test", 0.5);
		serverModel.getCentroidsNumber();
		serverModel.getAttributesNames();
		LinkedList<LinkedList<String>> centroids = new LinkedList<LinkedList<String>>();
		centroids.add(new LinkedList<String>(Arrays.asList("2.0", "b")));
		centroids.add(new LinkedList<String>(Arrays.asList("1.0", "a")));
		assertEquals(centroids, serverModel.getCentroids());
		serverModel.close(true);
	}

	@Test
	void testGetAvgDistances() throws Exception {
		ServerModel serverModel = new ServerModel("localhost", 8080);
		serverModel.clusterDBTable("test", 0.5);
		serverModel.getCentroidsNumber();
		serverModel.getAttributesNames();
		serverModel.getCentroids();
		assertEquals(new LinkedList<Double>(Arrays.asList(0.0, 0.0)), serverModel.getAvgDistances());
		serverModel.close(true);
	}

	@Test
	void testGetData() throws IOException {
		ServerModel serverModel = new ServerModel("localhost", 8080);
		serverModel.clusterDBTable("test", 0.5);
		serverModel.getCentroidsNumber();
		serverModel.getAttributesNames();
		serverModel.getCentroids();
		serverModel.getAvgDistances();
		LinkedList<List<List<String>>> tuples = new LinkedList<List<List<String>>>();
		LinkedList<String> l = new LinkedList<String>();
		l.addAll(Arrays.asList("0.0", "2.0", "b"));
		LinkedList<List<String>> k = new LinkedList<List<String>>();
		k.add(l);
		tuples.add(k);
		l = new LinkedList<String>();
		l.addAll(Arrays.asList("0.0", "1.0", "a"));
		k = new LinkedList<List<String>>();
		k.add(l);
		tuples.add(k);
		assertEquals(tuples,serverModel.getData());
		serverModel.close(true);
	}

	@Test
	void testClose() throws Exception {
		ServerModel serverModel = new ServerModel("localhost", 8080);
		serverModel.close(true);
		assertFalse(serverModel.isConnected());
		serverModel = new ServerModel("localhost", 8080);
		serverModel.close(false);
		assertFalse(serverModel.isConnected());
		serverModel.close(true);
	}

	@Test
	void testSaveFile() throws Exception {
		ServerModel serverModel = new ServerModel("localhost", 8080);
		serverModel.clusterDBTable("test", 0.5);
		serverModel.getCentroidsNumber();
		serverModel.getAttributesNames();
		serverModel.getCentroids();
		serverModel.getAvgDistances();
		assertEquals("", serverModel.saveFile("test"));
		serverModel.close(true);
	}

	@Test
	void testGoBack() throws Exception {
		ServerModel serverModel = new ServerModel("localhost", 8080);
		serverModel.clusterDBTable("test", 0.5);
		serverModel.getCentroidsNumber();
		serverModel.getAttributesNames();
		serverModel.getCentroids();
		serverModel.getAvgDistances();
		serverModel.goBack();
		assertTrue(serverModel.loadFile("test"));
		serverModel.close(true);
	}

	@Test
	void testGetDistances() throws Exception {
		ServerModel serverModel = new ServerModel("localhost", 8080);
		serverModel.clusterDBTable("test", 0.5);
		serverModel.getCentroidsNumber();
		serverModel.getAttributesNames();
		serverModel.getCentroids();
		serverModel.getAvgDistances();
		serverModel.getDistances();
		serverModel.close(true);
	}

	@Test
	void testGetIP() {
		assertEquals("127.0.0.1", serverModel.getIP());
	}

	@Test
	void testGetPort() {
		assertEquals(8080, serverModel.getPort());
	}

}
