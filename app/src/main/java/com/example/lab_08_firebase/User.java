package com.example.lab_08_firebase;

public class User {
   // private String keyID;
    public String name;
    public String email;
    public String password;
    public int smile;
    public int angry;
    public int bored;

    public User() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSmile() {
        return smile;
    }

    public void setSmile(int smile) {
        this.smile = smile;
    }

    public int getAngry() {
        return angry;
    }

    public void setAngry(int angry) {
        this.angry = angry;
    }

    public int getBored() {
        return bored;
    }

    public void setBored(int bored) {
        this.bored = bored;
    }

    public User(String name, String email, String password, int smile, int angry, int bored) {
        //this.keyID = keyID;
        this.email = email;
        this.name = name;
        this.password = password;
        this.smile = smile;
        this.angry = angry;
        this.bored = bored;
    }
}
