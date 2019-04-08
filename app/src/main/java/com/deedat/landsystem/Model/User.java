package com.deedat.landsystem.Model;

public class User {
    public String fullname;
    public String username;
    public String email;
    public String publicKey;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String fullname, String username, String email, String publicKey) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.publicKey = publicKey;
    }
}
