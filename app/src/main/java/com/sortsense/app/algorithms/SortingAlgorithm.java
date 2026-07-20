package com.sortsense.app.algorithms;

import android.app.Activity;
import com.sortsense.app.visualizer.SortingVisualizer;

public abstract class SortingAlgorithm {
    protected SortingVisualizer visualizer;
    protected Activity activity;
    protected int[] randomArray;
    protected int time = 100;

    protected volatile boolean isPaused = false;
    protected volatile boolean isRunning = false;
    protected Thread algorithmThread;

    public void colSwap(int col1, int col2) {
        if (visualizer != null) visualizer.colSwap(col1, col2);
    }

    public void colIndex(int index) {
        if (visualizer != null) visualizer.colIndex(index);
    }

    public void colComp(int comp1, int comp2) {
        if (visualizer != null) visualizer.colComp(comp1, comp2);
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public synchronized void pause() {
        isPaused = true;
    }

    public synchronized void resume() {
        isPaused = false;
        notifyAll();
    }

    public boolean isPaused() {
        return isPaused;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    protected synchronized void checkWait() {
        while (isPaused) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void setArray(int[] array) {
        this.randomArray = array;
        if (visualizer != null) visualizer.setRandomArray(array);
    }

    protected void swapIndex(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    protected void delay(int time) {
        checkWait();
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Legacy support for classes that might still use the old field name or method
    @Deprecated
    public void setSorting(boolean sorting) {
        if (sorting) resume(); else pause();
    }
}
