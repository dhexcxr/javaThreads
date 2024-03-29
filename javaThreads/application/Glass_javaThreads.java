//Author Name: M. Corey Glass
//Date: 07/14/2022
//Program Name: glass_javaThreads
//Purpose: demonstrate the use of Threads in Java

package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;


public class Glass_javaThreads extends Application {

	private Random random = new Random();
	private static final int ARRAY_LENGTH = 200000000;
	private List<Integer> intList = new ArrayList<>(ARRAY_LENGTH);
	private TextArea numberOfThreadsInput;
	private TableView<Glass_SumResults> outputTable;

	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane mainPane = new BorderPane();

			// setup output table
			HBox outputLayout = new HBox();
			outputLayout.setPadding(new Insets(5));
			outputLayout.setSpacing(4);		
			
			outputTable = new TableView<>();
			TableColumn<Glass_SumResults, String> firstCol = new TableColumn<>("Number of numbers");
			firstCol.setCellValueFactory(new PropertyValueFactory<>("nums"));
			firstCol.setMinWidth(128);
			
			TableColumn<Glass_SumResults, String> secondCol = new TableColumn<>("Threads");
			secondCol.setCellValueFactory(new PropertyValueFactory<>("threads"));
			secondCol.setMinWidth(48);
			
			TableColumn<Glass_SumResults, String> thirdCol = new TableColumn<>("Time (mS)");
			thirdCol.setCellValueFactory(new PropertyValueFactory<>("time"));
			thirdCol.setMinWidth(64);
			
			TableColumn<Glass_SumResults, String> fourthCol = new TableColumn<>("Sum");
			fourthCol.setCellValueFactory(new PropertyValueFactory<>("sum"));
			fourthCol.setMinWidth(96);
			
			outputTable.getColumns().addAll(Arrays.asList(firstCol, secondCol, thirdCol, fourthCol));
			outputLayout.getChildren().add(outputTable);
			
			// setup input boxes and buttons
			HBox inputLayout = new HBox();
			inputLayout.setPadding(new Insets(10));
			inputLayout.setSpacing(4);
			
			Label numberOfThreadsLabel = new Label("Enter number of threads to use:");
			numberOfThreadsLabel.setWrapText(true);
			numberOfThreadsLabel.setMaxWidth(128);
			
			numberOfThreadsInput = new TextArea();
			numberOfThreadsInput.setMaxSize(192, 32);
			numberOfThreadsInput.setMinSize(64, 32);
			numberOfThreadsInput.setWrapText(true);
			numberOfThreadsInput.setText("1");
			
			inputLayout.getChildren().add(numberOfThreadsLabel);
			inputLayout.getChildren().add(numberOfThreadsInput);

			Button startButton = new Button();
			startButton.setMaxSize(256, 32);
			startButton.setMinSize(128, 32);
			startButton.setText("Start Calculation");
			startButton.setOnAction(event -> startCalc());
			inputLayout.getChildren().add(startButton);

			// setup main pane
			mainPane.setCenter(outputLayout);
			mainPane.setBottom(inputLayout);
			Scene scene = new Scene(mainPane, 400, 400);

			primaryStage.setTitle("Array Summer");
			primaryStage.setScene(scene);
			primaryStage.show();

			// generate the large array with random numbers
			buildArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void buildArray() {
		// prep Random with a seed
		long seed = java.lang.System.currentTimeMillis();
		random.setSeed(seed);
		
		// generate a large list with which to test
		for (int i = 0; i < ARRAY_LENGTH; i++) {
			Integer element = random.nextInt(11);
			intList.add(element);
//			intList.add(1);			// for testing purposes
		}
	}

	private void startCalc() {		
		long sum = 0l;

		// get num of threads from user input
		int numOfThreads = 1;
		try {
			numOfThreads = Integer.parseInt(numberOfThreadsInput.getText());
			if (numOfThreads < 0) {
				throw new NumberFormatException();
			}
		} catch (Exception NumberFormatException) {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Error");
			error.setContentText("Please enter a number larger than 0.");
			error.show();
			return;
		}

		// start timer and setup threads
		long arraySumStart = System.currentTimeMillis();
		Glass_SummerThread[] threads = new Glass_SummerThread[numOfThreads];
		int elementsPerThread = ARRAY_LENGTH / numOfThreads;
		int threadStartElement = 0;
		
		// account for uneven division of arrays elements between threads
		int threadsWithExtraElements = ARRAY_LENGTH % numOfThreads;
		if (threadsWithExtraElements != 0) {
			elementsPerThread++;
		}
		
		// divide elements between threads
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Glass_SummerThread(intList, threadStartElement, elementsPerThread);
			threadStartElement += elementsPerThread;
			if (--threadsWithExtraElements == 0) {
				elementsPerThread--;
			}
		}
		
		// start jobs
		for (int i = 0; i < threads.length; i++) {
			threads[i].start();
		}
		
		// stop all threads, and sum the results
		try {
			for (Glass_SummerThread thread : threads) {
				thread.join();
				sum += thread.getResults();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
		// stop timer
		long arraySumEnd = System.currentTimeMillis();

		// add output results to table
		outputTable.getItems().add(new Glass_SumResults(ARRAY_LENGTH, numOfThreads, arraySumEnd - arraySumStart, sum));
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
