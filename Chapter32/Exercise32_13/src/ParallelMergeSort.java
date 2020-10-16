/*
*	Edited By: Christian Harris.
*	Date: 16 October 2020.
*	Description: This program has been modified to implement a generic parallel merge sort.
*/

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;
import java.util.Arrays;

public class ParallelMergeSort {
	public static void main(String[] args) {
		final int SIZE = 7000000;
		Integer[] list1 = new Integer[SIZE];
		Integer[] list2 = new Integer[SIZE];

		for (int i = 0; i < list1.length; i++)
			list1[i] = list2[i] = (int)(Math.random() * 10000000);

		long startTime = System.currentTimeMillis();
		parallelMergeSort(list1); // Invoke parallel merge sort
		long endTime = System.currentTimeMillis();
		System.out.println("\nParallel time with "
			+ Runtime.getRuntime().availableProcessors() + 
			" processors is " + (endTime - startTime) + " milliseconds");

		startTime = System.currentTimeMillis();
		MergeSort.mergeSort(list2); // MergeSort is in Listing 24.5
		endTime = System.currentTimeMillis();
		System.out.println("\nSequential time is " + 
			(endTime - startTime) + " milliseconds");
	}

	public static <E extends Comparable<E>> void parallelMergeSort(E[] list) {
		RecursiveAction mainTask = new SortTask<E>(list);
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(mainTask);
	}

	private static class SortTask<E extends Comparable<E>> extends RecursiveAction {
		private final int THRESHOLD = 500;
		private E[] list;

		SortTask(E[] list) {
			this.list = list;
		}

		@Override
		protected void compute() {
			if (list.length < THRESHOLD)
				java.util.Arrays.sort(list);
			else {
				// Obtain the first half
				E[] firstHalf = Arrays.copyOf(list, list.length / 2);

				// Obtain the second half
				int secondHalfLength = list.length - list.length / 2;
				E[] secondHalf = Arrays.copyOfRange(list, list.length / 2, list.length); 

				// Recursively sort the two halves
				invokeAll(new SortTask(firstHalf), 
					new SortTask(secondHalf));

				// Merge firstHalf with secondHalf into list
				MergeSort.merge(firstHalf, secondHalf, list);
			}
		}
	}
}