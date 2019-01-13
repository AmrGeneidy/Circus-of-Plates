import java.io.IOException;

import eg.edu.alexu.csd.oop.game.world.InitialWorld;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FirstMain extends Application{
	public static Stage primaryStage;
	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("MainDesign.fxml"));
        primaryStage.setTitle("Circus Of Plates");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setResizable(false);
        this.primaryStage = primaryStage;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static void end() {
		primaryStage.close();
	}
}
	


