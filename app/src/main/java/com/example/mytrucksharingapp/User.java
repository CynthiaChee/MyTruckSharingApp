package com.example.mytrucksharingapp;

import android.graphics.Bitmap;

public class User {

    //Instance variables
    private int userID;
    private String fullName, userName, passWord, phoneNo;
    private Bitmap userPhoto;

    //Constructor for initialization
    public User(String fullName, String userName, Bitmap userPhoto, String passWord, String phoneNo) {
        this.fullName = fullName;
        this.userName = userName;
        this.userPhoto = userPhoto;
        this.passWord = passWord;
        this.phoneNo = phoneNo;
    }

    //Getters and setters
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPhoneNumber() { return phoneNo; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNo = phoneNumber; }

    public Bitmap getUserPhoto() { return userPhoto; }
    public void setUserPhoto(Bitmap userPhoto) { this.userPhoto = userPhoto; }




}
