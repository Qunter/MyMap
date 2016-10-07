package com.yufa.mymap.Entity;

/**
 * Created by Administrator on 2016/10/6.
 */

public class EditUser {

    private String username;
    private String personality;
    private String address;

    public EditUser(){}

    public EditUser(String username, String personality, String address) {
        this.username = username;
        this.personality = personality;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonality() {
        return personality;
    }

    public void setPersonality(String personality) {
        this.personality = personality;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
