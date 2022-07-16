//Author Name: M. Corey Glass
//Date: 07/14/2022
//Program Name: glass_javaThreads
//Purpose: demonstrate the use of Threads in Java

package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;


class SummerThread extends Thread {

	private long result;
	private List<Integer> intList;
	private int threadStartElement;
	private int threadElements;	

	public SummerThread(List<Integer> intList, int threadStartElement, int numberOfElements) {
		super();
		this.result = 0l;
		this.intList = intList;
		this.threadStartElement = threadStartElement;
		this.threadElements = numberOfElements;
	}

	public long getResults() {
		return result;
	}

	@Override
	public void run() {
		// sum the elements of the array that we were given
		for (int i = threadStartElement; i < threadStartElement + threadElements; i++) {
			result += intList.get(i);
		}				
	}
}


public class Glass_javaThreads extends Application {

	private int ARRAY_LENGTH = 200000000;
	private List<Integer> intList = new ArrayList<>(ARRAY_LENGTH);
	private TextArea numberOfThreadsInput;
	private TextArea outputResults;

	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane mainPane = new BorderPane();

			// setup output, currently textArea
			HBox outputLayout = new HBox();
			outputResults = new TextArea();
			outputLayout.getChildren().add(outputResults);
			
			// setup input boxes and buttons
			HBox inputLayout = new HBox();
			inputLayout.setPadding(new Insets(10));
			inputLayout.setSpacing(4);
			// TODO add button for num of threads, start button, and maybe num of nums to
				// sum
				// maybe labels
			numberOfThreadsInput = new TextArea();
			numberOfThreadsInput.setMaxSize(256, 32);
			numberOfThreadsInput.setWrapText(true);
			inputLayout.getChildren().add(numberOfThreadsInput);

			Button startButton = new Button();
			startButton.setMaxSize(256, 32);
			startButton.setText("Start Calculation");
			startButton.setOnAction(event -> startCalc());
			inputLayout.getChildren().add(startButton);

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
		Random random = new Random();
		random.setSeed(seed);
		
		// generate a large list with which to test
		for (int i = 0; i < ARRAY_LENGTH; i++) {
			Integer element = random.nextInt(11);
			intList.add(element);
//			intList.add(1);			// just for testing purposes
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
			// TODO: handle exception
			// make dialog popup to complain
		}

		// start timer and setup threads
		long arraySumStart = System.currentTimeMillis();
		SummerThread[] threads = new SummerThread[numOfThreads];
		final int elementsPerThread = ARRAY_LENGTH / numOfThreads;
		int threadStartElement = 0;
		
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new SummerThread(intList, threadStartElement, elementsPerThread);
			threadStartElement += elementsPerThread;
		}
		
		// start jobs
		for (int i = 0; i < threads.length; i++) {
			threads[i].start();
		}
		
		// stop all threads, and sum the results
		try {
			for (SummerThread thread : threads) {
				thread.join();
				sum += thread.getResults();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// stop timer
		long arraySumEnd = System.currentTimeMillis();

		outputResults.setText("This is a calling\n");
		outputResults.setText(outputResults.getText() + "sum = " + sum + " -- time = " + (arraySumEnd - arraySumStart));
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
