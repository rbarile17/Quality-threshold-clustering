package controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class Controller {

	protected Controller newWindow(Stage stage,String path) throws IOException, NullPointerException {
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource(path).openStream());  
		Controller controller = (Controller)loader.getController();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("../graphic/application.css").toExternalForm());
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
		return controller;
	}
}
