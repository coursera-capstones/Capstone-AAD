package com.coursera.aad.capstoneapp.models;

import androidx.annotation.NonNull;

import com.google.gson.GsonBuilder;

import java.io.Serializable;

public class User implements Serializable {
    private int id = 0;
    private String fullName = "";
    private String email = "";
    private String password = "";
    private int status = 0;
    private Boolean isSignIn = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Boolean getSignIn() {
        return isSignIn;
    }

    public void setSignIn(Boolean signIn) {
        isSignIn = signIn;
    }

    @NonNull
    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this);
    }
}
