//Author Name: M. Corey Glass
//Date: 07/14/2022
//Program Name: glass_javaThreads
//Purpose: demonstrate the use of Threads in Java

package application;

public class Glass_SumResults {
		int nums;
		int threads;
		long time;
		long sum;
		
		public Glass_SumResults(int nums, int threads, long time, long sum) {
			this.nums = nums;
			this.threads = threads;
			this.time = time;
			this.sum = sum;
		}

		public int getNums() {
			return nums;
		}

		public void setNums(int nums) {
			this.nums = nums;
		}

		public int getThreads() {
			return threads;
		}

		public void setThreads(int threads) {
			this.threads = threads;
		}

		public long getTime() {
			return time;
		}

		public void setTime(long time) {
			this.time = time;
		}

		public long getSum() {
			return sum;
		}

		public void setSum(long sum) {
			this.sum = sum;
		}	
	}