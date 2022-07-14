//Author Name: M. Corey Glass
//Date: 07/14/2022
//Program Name: glass_javaThreads
//Purpose: demonstrate the use of Threads in Java

package application;
	
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;


public class Glass_javaThreads extends Application {
	
	TextArea outputResults;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane mainPane = new BorderPane();
			
			// setup output, currently textArea
			HBox output = new HBox();
			outputResults = new TextArea();
			output.getChildren().add(outputResults);
			
			// setup input boxes and buttons
			HBox input = new HBox();
			input.setPadding(new Insets(10));
			input.setSpacing(4);
			// TODO add button for num of threads, start button, and maybe num of nums to sum
			// maybe labels
			TextArea numOfThreads = new TextArea();
			numOfThreads.setMaxSize(256, 32);
			numOfThreads.setWrapText(true);
			input.getChildren().add(numOfThreads);
			
			Button startButton = new Button();
			startButton.setMaxSize(256, 32);
			startButton.setText("Start Calculation");
			startButton.setOnAction(event -> startCalc());
			input.getChildren().add(startButton);
			
			mainPane.setCenter(output);
			mainPane.setBottom(input);
			Scene scene = new Scene(mainPane,400,400);
			
			primaryStage.setTitle("Array Summer");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void startCalc() {
		outputResults.setText("This is a calling");
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
