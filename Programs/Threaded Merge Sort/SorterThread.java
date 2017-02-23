package project2;
import project2.MergeSort;


public class SorterThread implements Runnable {
	private int[] numberArray;
	private int threadCount;
	
	public SorterThread(int[] numberArray, int threadCount) {
		this.numberArray = numberArray;
		this.threadCount = threadCount;
	}
	
	public void run() {
		MergeSort.parallelMergeSort(numberArray, threadCount);
	}
}