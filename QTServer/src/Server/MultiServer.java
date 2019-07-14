package Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class MultiServer {
	private int PORT = 8080;
	public static void main(String[] args) { //istanzia un oggetto di tipo MultiServer.
		MultiServer server = new MultiServer(8080);
	}
	public MultiServer(int port) {//Costruttore di classe. Inizializza la porta ed invoca run()
		this.PORT = port;
	}
	private void run() {// Istanzia un oggetto istanza della classe ServerSocket che pone in attesa di crichiesta di connessioni da parte del client. Ad ogni nuova richiesta connessione si istanzia ServerOneClient.
		ServerSocket serverSocket;
		try {
			int i = 0;
			serverSocket = new ServerSocket(PORT);
			while(i<200) {
				Socket socket = serverSocket.accept();
				i++;
				try {
					new ServerOneClient(socket);
				} catch(IOException e) {
				socket.close();
				}

			}

		serverSocket.close();
		} catch(IOException e) {
		}
	}	   
}
