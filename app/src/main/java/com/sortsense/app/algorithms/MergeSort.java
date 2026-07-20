package com.sortsense.app.algorithms;

import android.app.Activity;
import com.sortsense.app.utilities.DataUtils;
import com.sortsense.app.visualizer.SortingVisualizer;

public class MergeSort extends SortingAlgorithm {

    public MergeSort(SortingVisualizer visualizer, int sizeOfArray, Activity activity) {
        this.visualizer = visualizer;
        this.activity = activity;
        randomArray = DataUtils.generateRandomArray(sizeOfArray);
        visualizer.setRandomArray(randomArray);
    }

    public void sort() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                isRunning = true;
                mergeSort(0, randomArray.length - 1);
                colComp(-1, -1);
                colSwap(-1, -1);
                isRunning = false;
            }
        }).start();
    }

    private void merge(int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        for (int i = 0; i < n1; i++) {
            leftArray[i] = randomArray[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = randomArray[mid + j + 1];
        }
        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            colComp(left + i, mid + j);
            delay(time);
            if (leftArray[i] <= rightArray[j]) {
                randomArray[k] = leftArray[i];
                colSwap(k, left + i);
                delay(time);
                i++;
            } else {
                randomArray[k] = rightArray[j];
                colSwap(k, mid + j);
                delay(time);
                j++;
            }
            k++;
        }

        while (i < n1) {
            randomArray[k] = leftArray[i];
            colSwap(k, left + i);
            delay(time);
            i++;
            k++;
        }
        while (j < n2) {
            randomArray[k] = rightArray[j];
            colSwap(k, mid + j);
            delay(time);
            j++;
            k++;
        }
        colComp(-1, -1);
        colSwap(-1, -1);
    }

    private void mergeSort(int left, int right) {
        if (left < right) {
            checkWait();
            int mid = left + (right - left) / 2;
            mergeSort(left, mid);
            mergeSort(mid + 1, right);
            merge(left, mid, right);
        }
    }
}
