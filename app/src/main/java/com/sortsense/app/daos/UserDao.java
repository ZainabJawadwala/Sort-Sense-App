package com.sortsense.app.daos;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.sortsense.app.models.User;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.MemoryCacheSettings;
import com.google.firebase.firestore.PersistentCacheSettings;

import java.util.Objects;

public class UserDao {
    private final FirebaseFirestore database;
    private final SharedPreferences sharedPreferences;

    public UserDao(Activity activity) {
        database = FirebaseFirestore.getInstance();
        
        // Enable offline persistence
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setLocalCacheSettings(PersistentCacheSettings.newBuilder().build())
                .build();
        database.setFirestoreSettings(settings);
        
        sharedPreferences = activity.getSharedPreferences("SortSensePrefs", Context.MODE_PRIVATE);
    }

    public void addUser(User user, OnAuthListener listener) {
        database.collection("users").document(user.getUsername()).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot result = task.getResult();
                        if (result != null && result.exists()) {
                            listener.onFailure("Username already exists");
                        } else {
                            database.collection("users").document(user.getUsername()).set(user)
                                    .addOnCompleteListener(t -> {
                                        if (t.isSuccessful()) {
                                            saveSession(user.getUsername());
                                            listener.onSuccess();
                                        } else {
                                            listener.onFailure(formatError(t.getException()));
                                        }
                                    });
                        }
                    } else {
                        listener.onFailure(formatError(task.getException()));
                    }
                });
    }

    public void loginUser(String username, String password, OnAuthListener listener) {
        database.collection("users").document(username).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot result = task.getResult();
                        if (result != null && result.exists()) {
                            User user = result.toObject(User.class);
                            if (user != null && Objects.equals(user.getPassword(), password)) {
                                saveSession(username);
                                listener.onSuccess();
                            } else {
                                listener.onFailure("Invalid password");
                            }
                        } else {
                            listener.onFailure("Username not found");
                        }
                    } else {
                        listener.onFailure(formatError(task.getException()));
                    }
                });
    }

    public void saveSession(String username) {
        sharedPreferences.edit().putString("username", username).apply();
    }

    public String getSession() {
        return sharedPreferences.getString("username", null);
    }

    public void logout() {
        sharedPreferences.edit().remove("username").apply();
    }

    private String formatError(Exception e) {
        if (e == null) return "Unknown error";
        String msg = e.getMessage();
        if (msg != null && (msg.contains("offline") || msg.contains("unavailable"))) {
            return "Client is offline. Please check your internet connection or use Test Mode.";
        }
        return msg;
    }

    public interface OnAuthListener {
        void onSuccess();
        void onFailure(String error);
    }
}
