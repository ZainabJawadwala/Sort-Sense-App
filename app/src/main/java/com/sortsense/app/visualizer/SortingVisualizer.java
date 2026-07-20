package com.sortsense.app.visualizer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import androidx.annotation.NonNull;

public class SortingVisualizer extends View {

    private final Paint mPaint;
    private final Paint outerPaint;
    private int[] randomArray;
    private float screenWidth, screenHeight;
    private int col1 = -1, col2 = -1;
    private int comp1 = -1, comp2 = -1;
    private int index = -1;
    
    // Configurable properties
    private float barWidthFactor = 1.0f; 
    private float scaleFactor = 1.0f; // Overall zoom/scale
    private final int COLOR_DEFAULT = Color.parseColor("#2196F3"); // Blue
    private final int COLOR_ACTION = Color.RED; // Comparing, Index, Pivot
    private final int COLOR_SUCCESS = Color.GREEN; // Swapping, Found

    public SortingVisualizer(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        
        outerPaint = new Paint();
        outerPaint.setStyle(Paint.Style.FILL);
        outerPaint.setColor(Color.TRANSPARENT);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        if (randomArray == null || randomArray.length == 0) return;
        
        canvas.drawPaint(outerPaint);
        
        float lineGap = 1;
        float availableWidth = screenWidth - (randomArray.length * lineGap);
        float baseStrokeWidth = (availableWidth / randomArray.length) * scaleFactor;
        mPaint.setStrokeWidth(baseStrokeWidth * barWidthFactor);

        float startX = (baseStrokeWidth * barWidthFactor) / 2 + lineGap;

        for (int i = 0; i < randomArray.length; i++) {
            if (comp1 == i || comp2 == i || index == i) {
                mPaint.setColor(COLOR_ACTION);
            } else if (col1 == i || col2 == i) {
                mPaint.setColor(COLOR_SUCCESS);
            } else {
                mPaint.setColor(COLOR_DEFAULT);
            }
            
            float maxVal = getMaxValue(randomArray) + 1;
            float barHeight = (randomArray[i] * (screenHeight / maxVal)) * scaleFactor;
            canvas.drawLine(startX, screenHeight, startX, screenHeight - barHeight, mPaint);
            startX += (availableWidth / randomArray.length) * scaleFactor + lineGap;
        }
    }

    private int getMaxValue(int[] array) {
        int max = 0;
        for (int i : array) if (i > max) max = i;
        return max;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        screenWidth = MeasureSpec.getSize(widthMeasureSpec);
        screenHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    public void colSwap(int col1, int col2) {
        this.col1 = col1;
        this.col2 = col2;
        postInvalidate();
    }

    public void colComp(int comp1, int comp2) {
        this.comp1 = comp1;
        this.comp2 = comp2;
        postInvalidate();
    }

    public void colIndex(int index) {
        this.index = index;
        postInvalidate();
    }

    public void setRandomArray(int[] randomArray) {
        this.randomArray = randomArray;
        postInvalidate();
    }

    public void setBarWidthFactor(float factor) {
        this.barWidthFactor = factor;
        postInvalidate();
    }

    public void setScaleFactor(float factor) {
        this.scaleFactor = factor;
        postInvalidate();
    }
}
