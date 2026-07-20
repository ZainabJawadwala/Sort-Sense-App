package com.sortsense.app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.sortsense.app.R;
import com.sortsense.app.daos.PostDao;
import com.sortsense.app.daos.UserDao;
import com.sortsense.app.models.Post;
import com.sortsense.app.models.User;
import com.google.firebase.firestore.FirebaseFirestore;

public class PostActivity extends AppCompatActivity {
    private EditText edtPostContent;
    private Button btnPost;
    private PostDao postDao;
    private FirebaseFirestore database;
    private UserDao userDao;
    private LottieAnimationView progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        initialize();
        addClicks();
    }

    private void initialize() {
        edtPostContent = findViewById(R.id.edtPostContent);
        btnPost = findViewById(R.id.btnPost);
        progressBar = findViewById(R.id.progressBarPost);
        postDao = new PostDao(PostActivity.this);
        database = FirebaseFirestore.getInstance();
        userDao = new UserDao(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Ask a Doubt");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.action_bar)));
        }
        
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar_login));
        }
    }

    private void addClicks() {
        btnPost.setOnClickListener(v -> {
            String postContent = edtPostContent.getText().toString().trim();
            if (postContent.isEmpty()) {
                edtPostContent.setError("Please enter something");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            btnPost.setEnabled(false);

            String currentUsername = userDao.getSession();
            if (currentUsername == null) {
                Toast.makeText(this, "Session expired, please login again", Toast.LENGTH_SHORT).show();
                return;
            }

            database.collection("users").document(currentUsername).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        User user = documentSnapshot.toObject(User.class);
                        if (user != null) {
                            Post post = new Post(user.getName(), postContent, user.getUsername());
                            postDao.addPost(post);
                        } else {
                            progressBar.setVisibility(View.GONE);
                            btnPost.setEnabled(true);
                            Toast.makeText(PostActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        progressBar.setVisibility(View.GONE);
                        btnPost.setEnabled(true);
                        Toast.makeText(PostActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }
}
