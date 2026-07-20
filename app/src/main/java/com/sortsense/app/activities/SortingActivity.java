package com.sortsense.app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.sortsense.app.algorithms.InsertionSort;
import com.sortsense.app.algorithms.MergeSort;
import com.sortsense.app.algorithms.QuickSort;
import com.sortsense.app.algorithms.SelectionSort;
import com.sortsense.app.algorithms.SortingAlgorithm;
import com.sortsense.app.utilities.DataUtils;
import com.sortsense.app.R;
import com.sortsense.app.algorithms.BubbleSort;
import com.sortsense.app.visualizer.SortingVisualizer;

public class SortingActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    Button btnShuffle, btnSort, btnStart, btnStop;
    SeekBar seekBar, seekBarTime, seekBarScale;
    int sizeOfArray = 40;
    BubbleSort bubbleSort;
    SelectionSort selectionSort;
    InsertionSort insertionSort;
    QuickSort quickSort;
    MergeSort mergeSort;
    SortingVisualizer sortingVisualizer;
    String algorithmName = "BUBBLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorting);
        initialize();
        addOnClicks();
    }

    private void initialize() {
        frameLayout = findViewById(R.id.frameLayout);
        ImageView btnBack = findViewById(R.id.btnBack);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
        btnShuffle = findViewById(R.id.btnShuffle);
        btnSort = findViewById(R.id.btnBubbleSort);
        btnStart = findViewById(R.id.btnStartSort);
        btnStop = findViewById(R.id.btnStopSort);
        
        seekBar = findViewById(R.id.seekBar);
        seekBarTime = findViewById(R.id.seekBarTime);
        seekBarScale = findViewById(R.id.seekBarScale);

        getSupportActionBar().setTitle("Sorting Visualizer");
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#3F51B5"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar_login));
        }

        sortingVisualizer = new SortingVisualizer(SortingActivity.this);
        frameLayout.addView(sortingVisualizer, 0);
        
        resetAlgorithms();

        seekBar.setMax(200);
        seekBar.setProgress(this.sizeOfArray);
        seekBarTime.setMax(700);
        seekBarTime.setProgress(bubbleSort.getTime());
        seekBarScale.setProgress(10);
    }

    private void resetAlgorithms() {
        bubbleSort = new BubbleSort(sortingVisualizer, sizeOfArray, SortingActivity.this);
        selectionSort = new SelectionSort(sortingVisualizer, sizeOfArray, SortingActivity.this);
        insertionSort = new InsertionSort(sortingVisualizer, sizeOfArray, SortingActivity.this);
        quickSort = new QuickSort(sortingVisualizer, sizeOfArray, SortingActivity.this);
        mergeSort = new MergeSort(sortingVisualizer, sizeOfArray, SortingActivity.this);
        
        sortingVisualizer.colComp(-1, -1);
        sortingVisualizer.colSwap(-1, -1);
    }

    private SortingAlgorithm getCurrentAlgorithm() {
        switch (algorithmName) {
            case "BUBBLE": return bubbleSort;
            case "SELECTION": return selectionSort;
            case "INSERTION": return insertionSort;
            case "QUICK": return quickSort;
            case "MERGE": return mergeSort;
            default: return bubbleSort;
        }
    }

    private void addOnClicks() {
        btnShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortingVisualizer.setRandomArray(DataUtils.generateRandomArray(sizeOfArray));
                btnSort.setEnabled(true);
                resetAlgorithms();
                sortingVisualizer.invalidate();
            }
        });

        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSort.setEnabled(false);
                startAlgorithm();
            }
        });
        
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resumeAlgorithm();
            }
        });
        
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseAlgorithm();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                btnSort.setEnabled(true);
                sizeOfArray = Math.max(progress, 5);
                resetAlgorithms();
                sortingVisualizer.invalidate();
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int t = Math.max(progress, 0);
                bubbleSort.setTime(t);
                selectionSort.setTime(t);
                insertionSort.setTime(t);
                quickSort.setTime(t);
                mergeSort.setTime(t);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        
        seekBarScale.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sortingVisualizer.setScaleFactor(progress / 10.0f);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void startAlgorithm() {
        SortingAlgorithm alg = getCurrentAlgorithm();
        alg.resume();
        if (alg instanceof BubbleSort) ((BubbleSort)alg).sort();
        else if (alg instanceof SelectionSort) ((SelectionSort)alg).sort();
        else if (alg instanceof InsertionSort) ((InsertionSort)alg).sort();
        else if (alg instanceof QuickSort) ((QuickSort)alg).sort();
        else if (alg instanceof MergeSort) ((MergeSort)alg).sort();
    }

    private void pauseAlgorithm() {
        getCurrentAlgorithm().pause();
    }

    private void resumeAlgorithm() {
        SortingAlgorithm alg = getCurrentAlgorithm();
        if (!alg.isRunning()) {
            startAlgorithm();
        }
        alg.resume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        btnSort.setEnabled(true);
        resetAlgorithms();
        int itemId = item.getItemId();
        if (itemId == R.id.bubbleSort) {
            btnSort.setText("Bubble Sort");
            algorithmName = "BUBBLE";
        } else if (itemId == R.id.insertionSort) {
            btnSort.setText("Insertion Sort");
            algorithmName = "INSERTION";
        } else if (itemId == R.id.selectionSort) {
            btnSort.setText("Selection Sort");
            algorithmName = "SELECTION";
        } else if (itemId == R.id.quickSort) {
            btnSort.setText("Quick Sort");
            algorithmName = "QUICK";
        } else if (itemId == R.id.mergeSort) {
            btnSort.setText("Merge Sort");
            algorithmName = "MERGE";
        }
        sortingVisualizer.invalidate();
        return super.onOptionsItemSelected(item);
    }
}
