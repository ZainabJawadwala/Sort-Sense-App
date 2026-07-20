package com.sortsense.app.algorithms;

import android.app.Activity;
import com.sortsense.app.utilities.DataUtils;
import com.sortsense.app.visualizer.SortingVisualizer;

public class InsertionSort extends SortingAlgorithm {

    int i, j;

    public InsertionSort(SortingVisualizer visualizer, int sizeOfArray, Activity activity) {
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
                for (i = 1; i < randomArray.length; i++) {
                    int temp = randomArray[i];
                    j = i - 1;
                    while (j >= 0 && randomArray[j] > temp) {
                        colSwap(i,-1);
                        colComp(j,-1);
                        delay(time);
                        randomArray[j + 1] = randomArray[j];
                        colComp(j,j+1);
                        delay(time);
                        j--;
                    }
                    randomArray[j + 1] = temp;
                    colSwap(i,j+1);
                    delay(time);
                }
                colComp(-1, -1);
                colSwap(-1, -1);
                isRunning = false;
            }
        }).start();
    }
}
