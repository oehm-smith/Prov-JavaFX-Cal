package app;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

	public Main() {
		
	}
	
	@Override
    public void start(Stage primaryStage) {
        try {
            Pane page =  FXMLLoader.load(Main.class.getResource("/fx/interface.fxml"));//(StackPane)
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("FXML is Simple");
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

	public static void main(String[] args) {
		launch(args);
	}
}
