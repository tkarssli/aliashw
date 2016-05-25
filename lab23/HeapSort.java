package lab23;

import java.util.Random;

/**
 * Created by Alia Karssli
 */

public class HeapSort
{

    // Heapify 3-ary sized heap
    void heapify(int arr[], int n, int i)
    {
        int largest = i;
        int l = 2*i + 1;  // left
        int m = 2*i + 2;  // middle
        int r = 2*i + 3;  // right

        // Check left child is larger than root
        if (l < n && arr[l] > arr[largest])
            largest = l;

        // Check middle child is larger than current largest
        if (m < n && arr[m] > arr[largest])
            largest = m;


        // Check right child is larger than current largest
        if (r < n && arr[r] > arr[largest])
            largest = r;

        // If largest isn't root
        if (largest != i)
        {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    }



    public void sort(int arr[])
    {
        int n = arr.length;

        // Build heap
        for (int i = (n-1)/3; i >= 0; i--)
            heapify(arr, n, i);

        
        for (int i=n-1; i>=0; i--)
        {
            // Move root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, i, 0);
        }
    }

    // Print array
    static void printArray(int arr[])
    {
        int n = arr.length;
        for (int i=0; i<n; ++i)
            System.out.print(arr[i]+" ");
        System.out.println();
    }

    // Driver program
    public static void main(String args[])
    {
        Random r = new Random();


        int arraySize = 100;
        int arr[] = new int[arraySize];

        for(int i=0; i<arraySize; i++){
            arr[i] = r.nextInt(arraySize+1);
        }

        HeapSort ob = new HeapSort();
        ob.sort(arr);

        System.out.print("Sorted Array: ");
        printArray(arr);
    }
}