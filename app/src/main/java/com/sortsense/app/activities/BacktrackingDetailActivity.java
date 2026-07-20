package com.sortsense.app.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.sortsense.app.R;
import com.sortsense.app.fragments.BacktrackingTheoryFragment;

public class BacktrackingDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backtracking_detail);

        String algoName = getIntent().getStringExtra("ALGO_NAME");
        
        TextView txtHeaderTitle = findViewById(R.id.txtHeaderTitle);
        ImageView btnBack = findViewById(R.id.btnBack);

        if (txtHeaderTitle != null) {
            txtHeaderTitle.setText(algoName);
        }

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, BacktrackingTheoryFragment.newInstance(algoName))
                    .commit();
        }
    }
}
