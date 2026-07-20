package com.sortsense.app.algorithms;

import android.app.Activity;
import com.sortsense.app.utilities.DataUtils;
import com.sortsense.app.visualizer.SortingVisualizer;

public class SelectionSort extends SortingAlgorithm {

    int i, j;
    int high1 = -1;
    int high2 = -1;

    public SelectionSort(SortingVisualizer visualizer, int sizeOfArray, Activity activity) {
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
                for (i = 0; i < randomArray.length; i++) {
                    int min = i;
                    for (j = i + 1; j < randomArray.length; j++) {
                        colComp(min,j);
                        delay(time);
                        if (randomArray[j] < randomArray[min]) {
                            high1 = j;
                            high2 = min;
                            swapIndex(randomArray,j,min);
                            colSwap(high1, high2);
                            delay(time);
                        }
                    }
                }
                colComp(-1, -1);
                colSwap(-1, -1);
                isRunning = false;
            }
        }).start();
    }
}
