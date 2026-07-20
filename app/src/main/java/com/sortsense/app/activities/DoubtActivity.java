package com.sortsense.app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sortsense.app.R;
import com.sortsense.app.adapters.PostAdapter;
import com.sortsense.app.daos.PostDao;
import com.sortsense.app.daos.UserDao;
import com.sortsense.app.models.Post;
import com.sortsense.app.models.User;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class DoubtActivity extends AppCompatActivity implements PostAdapter.OnItemClickListener {
    RecyclerView postRV;
    FloatingActionButton floatingActionButton;
    PostAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    EditText edtQuickAsk;
    ImageView btnQuickAsk;
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    CollectionReference postRef = database.collection("posts");
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doubt);
        initialize();
        setUpRecyclerView();
        addOnClicks();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.post_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.myPosts) {
            startActivity(new Intent(DoubtActivity.this, MyPostsActivity.class));
        } else if (item.getItemId() == R.id.dsaReference) {
            startActivity(new Intent(DoubtActivity.this, DSAReferenceActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void initialize() {
        postRV = findViewById(R.id.postRV);
        floatingActionButton = findViewById(R.id.btnFloat);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        edtQuickAsk = findViewById(R.id.edtQuickAsk);
        btnQuickAsk = findViewById(R.id.btnQuickAsk);
        userDao = new UserDao(this);
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Doubt Solver");
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
        Query query = postRef.orderBy("createdAt", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Post> options = new FirestoreRecyclerOptions.Builder<Post>()
                .setQuery(query, Post.class)
                .build();
        adapter = new PostAdapter(options, userDao);
        postRV.setHasFixedSize(true);
        postRV.setLayoutManager(new LinearLayoutManager(DoubtActivity.this));
        postRV.setAdapter(adapter);
        adapter.onItemClickListener(this);
    }

    private void addOnClicks() {
        floatingActionButton.setOnClickListener(v -> startActivity(new Intent(DoubtActivity.this, PostActivity.class)));

        btnQuickAsk.setOnClickListener(v -> {
            String doubtContent = edtQuickAsk.getText().toString().trim();
            if (doubtContent.isEmpty()) {
                Toast.makeText(this, "Please enter your doubt", Toast.LENGTH_SHORT).show();
                return;
            }

            String currentUsername = userDao.getSession();
            if (currentUsername == null) {
                Toast.makeText(this, "Session expired, please login again", Toast.LENGTH_SHORT).show();
                return;
            }

            database.collection("users").document(currentUsername).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        User user = documentSnapshot.toObject(User.class);
                        if (user != null) {
                            Post post = new Post(user.getName(), doubtContent, user.getUsername());
                            PostDao postDao = new PostDao(DoubtActivity.this);
                            postDao.addPost(post);
                            edtQuickAsk.setText("");
                        }
                    })
                    .addOnFailureListener(e -> Toast.makeText(DoubtActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        });
    }


    @Override
    public void onLikeClick(DocumentSnapshot documentSnapshot, int position) {
        PostDao postDao = new PostDao(DoubtActivity.this);
        Post post = documentSnapshot.toObject(Post.class);
        if (post != null) {
            postDao.likePost(post);
        }
    }

    @Override
    public void onCommentClick(DocumentSnapshot documentSnapshot, int position) {
        Post post = documentSnapshot.toObject(Post.class);
        if (post != null) {
            Intent intent = new Intent(DoubtActivity.this, CommentActivity.class);
            intent.putExtra("POST", post.getDocumentId());
            startActivity(intent);
        }
    }
}
