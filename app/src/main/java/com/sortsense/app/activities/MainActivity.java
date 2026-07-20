package com.sortsense.app.activities;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sortsense.app.R;
import com.sortsense.app.adapters.CategoryAdapter;
import com.sortsense.app.daos.UserDao;
import com.sortsense.app.models.Category;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView categoryRV;
    CardView cardView;
    LinearLayout linearAbout;
    ArrayList<Category> categoryArrayList = new ArrayList<>();
    TextView txtBtnAbout;
    boolean isAbout = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        setOnClicks();

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finishAffinity();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {

            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.logout_dialog, null);
            Button btnCancel = view.findViewById(R.id.btnCancel);
            Button btnLogout = view.findViewById(R.id.btnLogout);

            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                    .setView(view)
                    .create();
            dialog.show();

            btnLogout.setOnClickListener(v -> {
                dialog.dismiss();
                AlertDialog loadingDialog = new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Signing out...")
                        .setCancelable(false)
                        .create();
                loadingDialog.show();
                
                UserDao userDao = new UserDao(MainActivity.this);
                userDao.logout();
                
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    loadingDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }, 1000);
            });
            btnCancel.setOnClickListener(v -> dialog.dismiss());
        }

        return super.onOptionsItemSelected(item);
    }

    public void initialize() {
        categoryRV = findViewById(R.id.categoryRV);
        cardView = findViewById(R.id.cardView);
        txtBtnAbout = findViewById(R.id.txtBtnAbout);
        linearAbout = findViewById(R.id.linearAbout);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.action_bar)));
        }
        
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar_login));
        }

        categoryArrayList.add(new Category(R.drawable.sorting, "Sorting"));
        categoryArrayList.add(new Category(R.drawable.searching, "Searching"));
        categoryArrayList.add(new Category(R.drawable.queen, "Backtracking"));
        categoryArrayList.add(new Category(R.drawable.path, "Path Finding"));

        categoryRV.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        categoryRV.setAdapter(new CategoryAdapter(MainActivity.this, categoryArrayList));
    }

    public void setOnClicks() {
        txtBtnAbout.setOnClickListener(v -> {
            if (!isAbout){
                linearAbout.setVisibility(View.VISIBLE);
                isAbout = true;
            }
            else {
                linearAbout.setVisibility(View.GONE);
                isAbout = false;
            }
        });


        cardView.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, DoubtActivity.class)));
    }
}
