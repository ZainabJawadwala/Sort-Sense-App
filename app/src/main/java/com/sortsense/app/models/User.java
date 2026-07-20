package com.sortsense.app.models;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class User {
    private String name;
    private String email;
    private String username;
    private String password;
    private String uid;

    @ServerTimestamp
    private Date createdAt;

    public User() {
    }

    public User(String name, String email, String username, String password, String uid) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
