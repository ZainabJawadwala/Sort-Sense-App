package com.sortsense.app.algorithms;

import android.app.Activity;
import com.sortsense.app.utilities.DataUtils;
import com.sortsense.app.visualizer.SortingVisualizer;

public class BubbleSort extends SortingAlgorithm {
    int i, j;
    int high1 = -1;
    int high2 = -1;

    public BubbleSort(SortingVisualizer visualizer, int sizeOfArray, Activity activity) {
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
                    for (j = 0; j < randomArray.length -i- 1; j++) {
                        colComp(j,j+1);
                        delay(time);
                        if (randomArray[j] > randomArray[j + 1]) {
                            high1 = j;
                            high2 = j + 1;
                            swapIndex(randomArray,j,j+1);
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
