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
		this.listen();
	}
	
	private void listen() {// Istanzia un oggetto istanza della classe ServerSocket che pone in attesa di crichiesta di connessioni da parte del client. Ad ogni nuova richiesta connessione si istanzia ServerOneClient.
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(PORT);
			while(true) { 
				Socket socket = serverSocket.accept();
				try {
					new ServerOneClient(socket);
				} catch(IOException e) {
					socket.close();
				}
			}
		} catch(IOException e) {
			
		}
	}	   
}
