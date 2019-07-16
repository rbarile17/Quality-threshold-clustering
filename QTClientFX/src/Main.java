import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import controller.Controller;
import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import utility.ExceptionAlert;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("graphic/Main.fxml").openStream());  
			MainController controller = (MainController)loader.getController(); 
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("graphic/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setOnCloseRequest((e) -> {
				try {
					controller.disconnect();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
			primaryStage.show();
		} catch(IOException | NullPointerException e) {
			e.printStackTrace();
			new ExceptionAlert(e);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
