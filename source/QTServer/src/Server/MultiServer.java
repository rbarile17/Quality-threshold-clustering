package Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

/**
 * Defines a class that describes a multiserver<br>
 * @author Pasquale De Marinis
 * @author Roberto Barile
 * @author Sergio Caputo
 */
public class MultiServer {
	private int PORT = 8080;

	/**
	 * Instantiates a multiserver type object 
	 * @param args
	 */
	public static void main(String[] args) { 
		new MultiServer(8080);
	}

	/**
	 * Initializes the port and launches run()
	 * @param port port of server
	 */
	public MultiServer(int port) {
		this.PORT = port;
		listen();
	}

	/**
	 * Instantiates a ServerSocket which waits connection requests from client. A ServerOneCient is instantiated for every connection request
	 */
	private void listen() {
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(PORT);
			while (true) {
				Socket socket = serverSocket.accept();
				try {
					new ServerOneClient(socket);
				} catch (IOException e) {
					socket.close();
				}
			}
		} catch (IOException e) {

		}
	}
}
