package com.example.artur.app;

public class UserProfile {
    public String userName;
    public String userNote;

    public UserProfile(){

    }


    public UserProfile(String userName, String userNote) {
        this.userName = userName;
        this.userNote = userNote;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNote() {
        return userNote;
    }

    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }
}
