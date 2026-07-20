package com.sortsense.app.algorithms;

import android.app.Activity;
import android.widget.Toast;
import com.sortsense.app.utilities.DataUtils;
import com.sortsense.app.visualizer.SortingVisualizer;

public class LinearSearch extends SortingAlgorithm {
    int searchElement = 5;
    boolean isPresent;
    int i = 0;

    public LinearSearch(SortingVisualizer visualizer, int sizeOfArray, Activity activity) {
        this.visualizer = visualizer;
        this.activity = activity;
        randomArray = DataUtils.generateRandomArray(sizeOfArray);
        visualizer.setRandomArray(randomArray);
    }

    public void search() {
        i = 0;
        isPresent = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                isRunning = true;
                for (i = 0; i < randomArray.length; i++) {
                    colComp(i, -1);
                    delay(time);
                    if (randomArray[i] == randomArray[searchElement]) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, "element found at index " + i, Toast.LENGTH_SHORT).show();
                            }
                        });
                        colSwap(i, -1);
                        delay(time * 3);
                        isPresent = true;
                        break;
                    }
                }
                if (!isPresent) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, "element not present in array", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                isRunning = false;
            }
        }).start();
    }

    public void setSearchElement(int searchElement) {
        this.searchElement = searchElement;
    }
}
