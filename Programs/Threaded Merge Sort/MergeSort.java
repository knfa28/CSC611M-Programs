package project2;

import java.util.*;

public class MergeSort
    {

    private static final int NUM_OF_ELEMENTS = 20000000;
    private static final int RUNS = 1;
    private static final int THREADS = 1;

    public static void main(String[] args) throws Throwable
        {
        int[] dataSet = createNumberList();
        int[] sacrificialDataSet;
        long absoluteStartTime;
        long startTime;
        long endTime;
        
        System.out.println("Elements: " + NUM_OF_ELEMENTS);
        absoluteStartTime = System.nanoTime();
        
        for (int i = 1; i <= RUNS; i++)
            {
            sacrificialDataSet = dataSet;
            startTime = System.nanoTime();
            parallelMergeSort(sacrificialDataSet, THREADS);
            endTime = System.nanoTime();

            if (!isSorted(sacrificialDataSet))
                {
                System.out.println("Sorting failed");
                throw new RuntimeException("Sorting was a failure: " + Arrays.toString(dataSet));
                }

            System.out.println("Run " + i + ":    " + (endTime - startTime) / 1000000000f + " Seconds");

            }
        System.out.println("Run Total: "+ + (System.nanoTime() - absoluteStartTime) / 1000000000f + " Seconds" );
        }

    public static void parallelMergeSort(int[] dataSet, int threadCount)
        {
        if (threadCount <= 1)
            {
            mergeSort(dataSet);
            } else
            {
            if (dataSet.length >= 2)
                {

                int[] left = new int[dataSet.length / 2];
                int[] right = new int[dataSet.length / 2];
                System.arraycopy(dataSet, 0, left, 0, dataSet.length / 2);
                System.arraycopy(dataSet, dataSet.length / 2, right, 0, dataSet.length / 2);

                Thread lThread = new Thread(new SorterThread(left, threadCount / 2));
                Thread rThread = new Thread(new SorterThread(right, threadCount / 2));
                lThread.start();
                rThread.start();

                try
                    {
                    lThread.join();
                    rThread.join();
                    } catch (InterruptedException ie)
                    {
                    }

                merge(left, right, dataSet);
                }
            }
        }

    public static void mergeSort(int[] dataSet)
        {
        if (dataSet.length >= 2)
            {
            int[] left = Arrays.copyOfRange(dataSet, 0, dataSet.length / 2);
            int[] right = Arrays.copyOfRange(dataSet, dataSet.length / 2, dataSet.length);
            mergeSort(left);
            mergeSort(right);
            merge(left, right, dataSet);
            }
        }

    public static void merge(int[] left, int[] right, int[] dataSet)
        {

        int x = 0;
        int y = 0;
        for (int i = 0; i < dataSet.length; i++)
            {
            if (y >= right.length || (x < left.length && left[x] < right[y]))
                {
                dataSet[i] = left[x];
                x++;
                } else
                {
                dataSet[i] = right[y];
                y++;
                }
            }
        }
  
    public static boolean isSorted(int[] input)
        {
        for (int i = 0; i < NUM_OF_ELEMENTS - 1; i++)
            {
            //System.out.println(input[i]);
            if (input[i + 1] < input[i])
                {
                return false;
                }
            }
        return true;
        }

    public static int[] createNumberList()
        {
        Random rand = new Random();

        int[] numArray = new int[NUM_OF_ELEMENTS];

        for (int i = 0; i < NUM_OF_ELEMENTS; i++)
            {
            numArray[i] = rand.nextInt(1000000);
            }
        return numArray;
        }
    }
