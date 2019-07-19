package controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Defines the common methods of the Controllers
 * 
 * @author Pasquale De Marinis
 * @author Roberto Barile
 * @author Sergio Caputo
 */
public abstract class Controller {

	/**
	 * Loads a new scene that will be showed
	 * 
	 * @param stage: the stage that wrap the new scene
	 * @param path:  the path of the FXML file that will be loaded
	 * @return Controller controller: the controller of the loaded scene
	 * @throws IOException:         if the loader can't load the FXML file
	 * @throws NullPointerException
	 */
	protected Controller newWindow(Stage stage, String path) throws IOException, NullPointerException {
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource(path).openStream());
		Controller controller = (Controller) loader.getController();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("../graphic/application.css").toExternalForm());
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
		return controller;
	}

	/**
	 * Loads a new scene that will be showed
	 * 
	 * @param stage: the stage that wrap the new scene
	 * @param path:  the path of the FXML file that will be loaded
	 * @return Controller controller: the controller of the loaded scene
	 * @throws IOException:         if the loader can't load the FXML file
	 * @throws NullPointerException
	 */
	protected Controller newWindow(Stage stage, String path, boolean resizable)
			throws IOException, NullPointerException {
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource(path).openStream());
		Controller controller = (Controller) loader.getController();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("../graphic/application.css").toExternalForm());
		stage.setScene(scene);
		stage.setResizable(resizable);
		stage.show();
		return controller;
	}
}
