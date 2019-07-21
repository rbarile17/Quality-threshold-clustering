package main;


import java.io.IOException;
import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import utility.ExceptionAlert;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * The main class of the application, start point of the program
 * 
 * @author Pasquale De Marinis
 * @author Roberto Barile
 * @author Sergio Caputo
 */
public class Main extends Application {

	/**
	 * Generate the main window
	 * 
	 * @param primaryStage The main window that wrap the scene
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("../graphic/Main.fxml").openStream());
			MainController controller = (MainController) loader.getController();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../graphic/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setOnCloseRequest((e) -> {
				controller.disconnect(true);
			});
			primaryStage.setTitle("QT Clustering");
			primaryStage.getIcons().add(new Image(this.getClass().getClassLoader().getResourceAsStream("icon.png")));
			primaryStage.show();
		} catch (IOException | NullPointerException e) {
			new ExceptionAlert(e);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
