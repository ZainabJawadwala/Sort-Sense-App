package com.sortsense.app.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.sortsense.app.R;

public class PathFindingTheoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_finding_theory);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Path Finding Theory");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
