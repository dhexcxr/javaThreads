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


class MyThread extends Thread {

	long sum;
	List<Integer> intList;
	int tse;
	int threadElements;
	
	

	public MyThread(List<Integer> intList, int tse, int threadElements) {
		super();
		this.intList = intList;
		this.tse = tse;
		this.threadElements = threadElements;
	}

	public long getResults() {
		return sum;
	}

	@Override
	public void run() {
		sum = summer(intList, tse, threadElements);				
	}
	
	private long summer(List<Integer> array, int threadStartElement, int threadElements) {
		long result = 0;
		for (int i = threadStartElement; i < threadStartElement + threadElements; i++) {
			result += array.get(i);
		}
		return result;
	}

}


public class Glass_javaThreads extends Application {

	int ARRAY_LENGTH = 200000000;
	int iterator = 0;
	List<Integer> intList = new ArrayList<>(ARRAY_LENGTH);
//	Long sum = 0l;
	TextArea inputThreads;
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
			// TODO add button for num of threads, start button, and maybe num of nums to
			// sum
			// maybe labels
			inputThreads = new TextArea();
			inputThreads.setMaxSize(256, 32);
			inputThreads.setWrapText(true);
			input.getChildren().add(inputThreads);

			Button startButton = new Button();
			startButton.setMaxSize(256, 32);
			startButton.setText("Start Calculation");
			startButton.setOnAction(event -> startCalc());
			input.getChildren().add(startButton);

			mainPane.setCenter(output);
			mainPane.setBottom(input);
			Scene scene = new Scene(mainPane, 400, 400);

			primaryStage.setTitle("Array Summer");
			primaryStage.setScene(scene);
			primaryStage.show();

			buildArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void buildArray() {
		// build randomized array
		long seed = java.lang.System.currentTimeMillis();
		Random random = new Random();
		random.setSeed(seed);
		// generate a large list to test
		

		for (int i = 0; i < ARRAY_LENGTH; i++) {
			Integer element = random.nextInt(11);
//			intList.add(element);
			intList.add(1);
		}
	}

	private void startCalc() {		
		iterator = 0;
		long sum = 0l;

		// get num of threads from user input
		int numOfThreads = 1;
		try {
			numOfThreads = Integer.parseInt(inputThreads.getText());
			if (numOfThreads < 0) {
				throw new NumberFormatException();
			}
		} catch (Exception NumberFormatException) {
			// TODO: handle exception
			// make dialog popup to complain
		}

		// setup threads
		long arraySumStart = System.currentTimeMillis();
		MyThread[] threads = new MyThread[numOfThreads];
		final int threadElements = ARRAY_LENGTH / numOfThreads;
		int threadStartElement = 0;
		for (int i = 0; i < threads.length; i++) {
			int tse = threadStartElement;
//			Runnable summerThread = new Runnable();
			
			threads[i] = new MyThread(intList, threadStartElement, threadElements);
			threadStartElement += threadElements;
		}
		
		// start jobs
		for (int i = 0; i < threads.length; i++) {
			threads[i].start();
		}
		
		// stop all threads
		
		try {
			for (MyThread thread : threads) {
				thread.join();
				sum += thread.getResults();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long arraySumEnd = System.currentTimeMillis();

		outputResults.setText("This is a calling\n");
		outputResults.setText(outputResults.getText() + "sum = " + sum + " -- time = " + (arraySumEnd - arraySumStart));
		outputResults.setText(outputResults.getText() + "\n\n" + "iter in startCalc, adter summer: " + iterator + "\n");
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
