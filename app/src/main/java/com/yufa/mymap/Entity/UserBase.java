package com.yufa.mymap.Entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by luyufa on 2016/8/25.
 */
public class UserBase extends BmobObject{
    private String password;
    private String phoneNumber;

    public UserBase() {
    }

    public UserBase( String password, String phoneNumber) {
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
