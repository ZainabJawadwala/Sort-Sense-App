package com.sortsense.app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.sortsense.app.R;
import com.sortsense.app.adapters.CommentAdapter;
import com.sortsense.app.daos.PostDao;
import com.sortsense.app.daos.UserDao;
import com.sortsense.app.models.Comment;
import com.sortsense.app.models.User;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;

public class CommentActivity extends AppCompatActivity {
    private String postId;
    private RecyclerView commentRV;
    private final FirebaseFirestore database = FirebaseFirestore.getInstance();
    private CollectionReference commentRef;
    private ImageView btnSendComment;
    private EditText edtComment;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CommentAdapter adapter;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        postId = getIntent().getStringExtra("POST");
        commentRef = database.collection("posts").document(postId).collection("comments");
        userDao = new UserDao(this);
        initialize();
        setUpRecyclerView();
        addClicks();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void initialize() {
        commentRV = findViewById(R.id.commentRV);
        btnSendComment = findViewById(R.id.btnSendComment);
        edtComment = findViewById(R.id.edtComment);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout2);
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Help & Answers");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.action_bar)));
        }
        
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar_login));
        }
    }

    private void setUpRecyclerView() {
        Query query = commentRef.orderBy("createdAt", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Comment> options = new FirestoreRecyclerOptions.Builder<Comment>()
                .setQuery(query, Comment.class)
                .build();
        adapter = new CommentAdapter(options);
        commentRV.setLayoutManager(new LinearLayoutManager(CommentActivity.this));
        commentRV.setAdapter(adapter);

        adapter.setOnItemClickListener((documentSnapshot, position) -> {
            Comment comment = documentSnapshot.toObject(Comment.class);
            String currentUsername = userDao.getSession();
            if (comment != null && currentUsername != null && Objects.equals(currentUsername, comment.getUserUid())) {
                PostDao postDao = new PostDao(CommentActivity.this);
                postDao.deleteComment(comment);
            }
        });
    }

    private void addClicks() {
        btnSendComment.setOnClickListener(v -> {
            String commentText = edtComment.getText().toString().trim();
            if (commentText.isEmpty()) {
                return;
            }
            
            String currentUsername = userDao.getSession();
            if (currentUsername == null) return;

            database.collection("users").document(currentUsername).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        User user = documentSnapshot.toObject(User.class);
                        if (user != null) {
                            Comment commentObj = new Comment(user.getName(), commentText, user.getUsername(), postId);
                            PostDao postDao = new PostDao(CommentActivity.this);
                            postDao.addComment(commentObj);
                            edtComment.setText("");
                        }
                    });
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        });
    }
}
