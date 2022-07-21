//Author Name: M. Corey Glass
//Date: 07/14/2022
//Program Name: glass_javaThreads
//Purpose: demonstrate the use of Threads in Java

package application;

import java.util.List;

public class Glass_SummerThread extends Thread {

	private long result;
	private List<Integer> intList;
	private int threadStartElement;
	private int threadElements;	

	public Glass_SummerThread(List<Integer> intList, int threadStartElement, int numberOfElements) {
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
