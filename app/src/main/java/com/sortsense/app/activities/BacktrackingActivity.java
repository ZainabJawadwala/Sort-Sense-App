package com.sortsense.app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.sortsense.app.R;
import com.sortsense.app.adapters.CategoryAdapter;
import com.sortsense.app.models.Category;

import java.util.ArrayList;

public class BacktrackingActivity extends AppCompatActivity {
    RecyclerView backtrackingRV;
    ArrayList<Category> algorithmList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backtracking);
        initialize();
    }

    private void initialize() {
        backtrackingRV = findViewById(R.id.backtrackingRV);
        ImageView btnBack = findViewById(R.id.btnBack);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Backtracking Algorithms");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.action_bar)));
        }

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar_login));
        }

        algorithmList.add(new Category(R.drawable.queen, "N-Queens"));
        algorithmList.add(new Category(R.drawable.path, "Rat in a Maze"));
        algorithmList.add(new Category(R.drawable.ic_sudoku_logo, "Sudoku Solver"));
        algorithmList.add(new Category(R.drawable.ic_diag_permutation, "Permutations"));
        algorithmList.add(new Category(R.drawable.ic_diag_rat_maze_v2, "Maze Solver"));

        backtrackingRV.setLayoutManager(new GridLayoutManager(this, 2));
        backtrackingRV.setAdapter(new CategoryAdapter(this, algorithmList));
    }
}
