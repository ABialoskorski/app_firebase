package com.example.artur.app;

public class UserProfile {
    public String userName;
    public UserProfile(){

    }

    public UserProfile(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
