package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbAccess {
	// private String DRIVER_CLASS_NAME = "org.gjt.mm.mysql.Driver";
	private String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

	private final String DBMS = "jdbc:mysql";

	// contiene l’identificativo del server su cui risiede la base di dati (per
	// esempio localhost)
	private final String SERVER = "localhost";

	// contiene il nome della base di dati
	private final String DATABASE = "MapDB";

	// La porta su cui il DBMS MySQL accetta le connessioni
	private final String PORT = "3306";

	// contiene il nome dell’utente per l’accesso alla base di dati
	private final String USER_ID = "MapUser";

	// contiene la password di autenticazione per l’utente identificato da USER_ID
	private final String PASSWORD = "map";

	// gestisce una connessione
	private Connection conn;

	public void initConnection() throws DatabaseConnectionException {
		try {
			Class.forName(DRIVER_CLASS_NAME).newInstance();
		} catch (Exception ex) {
			System.out.println("Driver Class Not Found");
		}

		try {
			conn = DriverManager.getConnection(
					DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE + "?user=" + USER_ID + "&password=" + PASSWORD);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new DatabaseConnectionException("Connection to the database failed");
		}
	}

	public Connection getConnection() {
		return conn;
	}

	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException ex) {
			ex.getMessage();
			ex.getErrorCode();
			ex.getSQLState();
		}
	}
}
