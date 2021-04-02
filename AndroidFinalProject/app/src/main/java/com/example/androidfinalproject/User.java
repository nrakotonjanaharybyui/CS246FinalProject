package com.example.androidfinalproject;

/**
 * User class
 * @author Narindra Rakotonjanahary
 * @version 03/16/2021
 * Handles all informations about the User class
 * provides getters and setters
 */
public class User {
    private String fullName, address, email;

    public User(){}

    public User(String fullName, String address, String email) {
        this.fullName = fullName;
        this.address = address;
        this.email = email;
    }

    //Public getters
    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    //Public setters
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
