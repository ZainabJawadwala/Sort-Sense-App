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

import com.sortsense.app.R;
import com.sortsense.app.algorithms.BinarySearch;
import com.sortsense.app.algorithms.LinearSearch;
import com.sortsense.app.algorithms.SortingAlgorithm;
import com.sortsense.app.utilities.DataUtils;
import com.sortsense.app.visualizer.SortingVisualizer;

public class SearchingActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    Button btnRandom, btnSearch, btnStart, btnStop;
    SeekBar seekBarSize, seekBarTime, seekBarIndex, seekBarScale;
    int sizeOfArray = 40;
    SortingVisualizer sortingVisualizer;
    LinearSearch linearSearch;
    BinarySearch binarySearch;
    String algorithmName = "LINEAR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);
        initialize();
        setClicks();
    }

    private void initialize() {
        frameLayout = findViewById(R.id.frameLayout1);
        ImageView btnBack = findViewById(R.id.btnBack);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
        btnRandom = findViewById(R.id.btnRandom);
        btnSearch = findViewById(R.id.btnSearch);
        btnStart = findViewById(R.id.btnStartSearch);
        btnStop = findViewById(R.id.btnStopSearch);
        
        seekBarSize = findViewById(R.id.seekBarSize);
        seekBarTime = findViewById(R.id.seekBarT);
        seekBarIndex = findViewById(R.id.seekBarIndex);
        seekBarScale = findViewById(R.id.seekBarScale);
        
        sortingVisualizer = new SortingVisualizer(SearchingActivity.this);
        binarySearch = new BinarySearch(sortingVisualizer, sizeOfArray, SearchingActivity.this);
        linearSearch = new LinearSearch(sortingVisualizer, sizeOfArray, SearchingActivity.this);

        getSupportActionBar().setTitle("Searching Visualizer");
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#3F51B5"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar_login));
        }

        frameLayout.addView(sortingVisualizer, 0);
        seekBarSize.setMax(200);
        seekBarIndex.setMax(sizeOfArray);
        seekBarSize.setProgress(this.sizeOfArray);
        seekBarTime.setMax(700);
        seekBarTime.setProgress(linearSearch.getTime());
        seekBarScale.setProgress(10);
    }

    private void setClicks() {
        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortingVisualizer.setRandomArray(DataUtils.generateRandomArray(sizeOfArray));
                btnSearch.setEnabled(true);
                resetAlgorithms();
                sortingVisualizer.invalidate();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSearch.setEnabled(false);
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

        seekBarSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                btnSearch.setEnabled(true);
                sizeOfArray = Math.max(progress, 5);
                seekBarIndex.setMax(sizeOfArray);
                resetAlgorithms();
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                linearSearch.setTime(Math.max(progress, 0));
                binarySearch.setTime(Math.max(progress, 0));
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekBarIndex.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                btnSearch.setEnabled(true);
                sortingVisualizer.colComp(progress, -1);
                linearSearch.setSearchElement(progress);
                binarySearch.setSearchElement(progress);
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

    private void resetAlgorithms() {
        linearSearch = new LinearSearch(sortingVisualizer, sizeOfArray, SearchingActivity.this);
        binarySearch = new BinarySearch(sortingVisualizer, sizeOfArray, SearchingActivity.this);
        sortingVisualizer.colComp(-1, -1);
        sortingVisualizer.colSwap(-1, -1);
    }

    private void startAlgorithm() {
        if (algorithmName.equals("LINEAR")) {
            linearSearch.resume();
            linearSearch.search();
        } else {
            binarySearch.resume();
            binarySearch.search();
        }
    }

    private void pauseAlgorithm() {
        if (algorithmName.equals("LINEAR")) {
            linearSearch.pause();
        } else {
            binarySearch.pause();
        }
    }

    private void resumeAlgorithm() {
        if (algorithmName.equals("LINEAR")) {
            if (!linearSearch.isRunning()) {
                linearSearch.search();
            }
            linearSearch.resume();
        } else {
            if (!binarySearch.isRunning()) {
                binarySearch.search();
            }
            binarySearch.resume();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        btnSearch.setEnabled(true);
        resetAlgorithms();
        
        int itemId = item.getItemId();
        if (itemId == R.id.linearSearch) {
            btnSearch.setText("Linear Search");
            algorithmName = "LINEAR";
        } else if (itemId == R.id.binarySearch) {
            btnSearch.setText("Binary Search");
            algorithmName = "BINARY";
        }
        sortingVisualizer.invalidate();
        return super.onOptionsItemSelected(item);
    }
}
