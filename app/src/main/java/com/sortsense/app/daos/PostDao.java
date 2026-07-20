package com.sortsense.app.daos;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.sortsense.app.activities.DoubtActivity;
import com.sortsense.app.models.Comment;
import com.sortsense.app.models.Post;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PostDao {
    private final FirebaseFirestore database;
    private final Activity activity;
    private final UserDao userDao;

    public PostDao(Activity activity) {
        this.activity = activity;
        this.database = FirebaseFirestore.getInstance();
        this.userDao = new UserDao(activity);
    }

    public void addPost(Post post) {
        String id = database.collection("posts").document().getId();
        post.setDocumentId(id);
        database.collection("posts")
                .document(id)
                .set(post)
                .addOnSuccessListener(aVoid -> {
                    activity.runOnUiThread(() -> {
                        Toast.makeText(activity, "doubt added successfully", Toast.LENGTH_SHORT).show();
                        activity.startActivity(new Intent(activity, DoubtActivity.class));
                        activity.finish();
                    });
                })
                .addOnFailureListener(e -> {
                    activity.runOnUiThread(() -> Toast.makeText(activity, "unable to add doubt", Toast.LENGTH_SHORT).show());
                });
    }

    public void likePost(Post post) {
        String currentUsername = userDao.getSession();
        if (currentUsername == null) return;

        if (post.getLikedBy().contains(currentUsername)) {
            post.getLikedBy().remove(currentUsername);
        } else {
            post.getLikedBy().add(currentUsername);
        }
        database.collection("posts")
                .document(post.getDocumentId())
                .update("likedBy", post.getLikedBy());
    }

    public void addComment(Comment comment) {
        String currentUsername = userDao.getSession();
        if (currentUsername == null) return;

        database.collection("posts")
                .document(comment.getPostId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Post post = documentSnapshot.toObject(Post.class);
                        if (post != null) {
                            post.getCommentedBy().add(currentUsername);
                            database.collection("posts")
                                    .document(post.getDocumentId())
                                    .update("commentedBy", post.getCommentedBy());

                            String id = database.collection("posts").document().getId();
                            comment.setDocumentId(id);
                            database.collection("posts")
                                    .document(comment.getPostId())
                                    .collection("comments")
                                    .document(id)
                                    .set(comment)
                                    .addOnSuccessListener(aVoid -> activity.runOnUiThread(() -> Toast.makeText(activity, "answer added successfully", Toast.LENGTH_SHORT).show()))
                                    .addOnFailureListener(e -> activity.runOnUiThread(() -> Toast.makeText(activity, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show()));
                        }
                    }
                });
    }

    public void deleteComment(Comment comment) {
        String currentUsername = userDao.getSession();
        if (currentUsername == null) return;

        database.collection("posts")
                .document(comment.getPostId())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    Post post = documentSnapshot.toObject(Post.class);
                    if (post != null) {
                        post.getCommentedBy().remove(currentUsername);
                        database.collection("posts")
                                .document(post.getDocumentId())
                                .update("commentedBy", post.getCommentedBy());
                        
                        database.collection("posts")
                                .document(comment.getPostId())
                                .collection("comments")
                                .document(comment.getDocumentId())
                                .delete()
                                .addOnSuccessListener(aVoid -> activity.runOnUiThread(() -> Toast.makeText(activity, "answer deleted", Toast.LENGTH_SHORT).show()));
                    }
                });
    }

    public void editPost(Post post, String updatedPostContent) {
        database.collection("posts")
                .document(post.getDocumentId())
                .update("postContent", updatedPostContent)
                .addOnSuccessListener(unused -> activity.runOnUiThread(() -> Toast.makeText(activity, "doubt edited successfully", Toast.LENGTH_SHORT).show()))
                .addOnFailureListener(e -> activity.runOnUiThread(() -> Toast.makeText(activity, "unable to edit doubt", Toast.LENGTH_SHORT).show()));
    }
}
