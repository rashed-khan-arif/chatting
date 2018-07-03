package com.project.chatting.model;

import com.project.chatting.core.ParseName;

public class User {
    @ParseName("user_id")
    private int userId;
    @ParseName("full_name")
    private String fullName;
    @ParseName("email")
    private String email;
    @ParseName("image")
    private String image="img";
    @ParseName("contact_number")
    private int contactNumber;
    @ParseName("active")
    private int active;
    @ParseName("password")
    private String password;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(int contactNumber) {
        this.contactNumber = contactNumber;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
