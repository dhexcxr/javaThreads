//Author Name: M. Corey Glass
//Date: 07/14/2022
//Program Name: glass_javaThreads
//Purpose: demonstrate the use of Threads in Java

package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Glass_javaThreads extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane mainPane = new BorderPane();
			
			
			Scene scene = new Scene(mainPane,400,400);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
