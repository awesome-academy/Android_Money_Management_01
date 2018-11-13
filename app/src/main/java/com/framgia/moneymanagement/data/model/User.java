package com.framgia.moneymanagement.data.model;

import com.google.firebase.auth.FirebaseUser;

public class User {
    private String mId;
    private String mEmail;
    private String mPassword;
    private String mName;
    private String mImage;

    public User() {
    }

    public User(String id, String email, String name, String image) {
        mId = id;
        mEmail = email;
        mName = name;
        mImage = image;
    }

    public User(FirebaseUser user) {
        mId = user.getUid();
        mEmail = user.getEmail();
        mName = user.getDisplayName();
        mImage = String.valueOf(user.getPhotoUrl());
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public static class Key {
        public static final String USER = "user";
    }
}
