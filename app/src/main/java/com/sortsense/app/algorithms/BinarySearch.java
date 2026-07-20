package com.sortsense.app.algorithms;

import android.app.Activity;
import android.widget.Toast;
import com.sortsense.app.utilities.DataUtils;
import com.sortsense.app.visualizer.SortingVisualizer;

public class BinarySearch extends SortingAlgorithm {
    int searchElement = 5;

    public BinarySearch(SortingVisualizer visualizer, int sizeOfArray, Activity activity) {
        this.visualizer = visualizer;
        this.activity = activity;
        randomArray = DataUtils.generateSortedArray(sizeOfArray);
        visualizer.setRandomArray(randomArray);
    }

    public void search() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                isRunning = true;
                int low = 0;
                int high = randomArray.length - 1;
                while (low <= high) {
                    int mid = (low + high) / 2;
                    colComp(mid, -1);
                    delay(time * 2);
                    
                    if (randomArray[mid] == randomArray[searchElement]) {
                        colSwap(mid, -1);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, "element found at index " + mid, Toast.LENGTH_SHORT).show();
                            }
                        });
                        delay(time * 3);
                        break;
                    }

                    if (randomArray[mid] < randomArray[searchElement]) {
                        low = mid + 1;
                    } else {
                        high = mid - 1;
                    }
                    colComp(low, high);
                    delay(time * 2);
                }
                isRunning = false;
            }
        }).start();
    }

    public void setSearchElement(int searchElement) {
        this.searchElement = searchElement;
    }
}
