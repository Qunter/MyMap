package com.yufa.mymap.Entity;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by luyufa on 2016/8/28.
 */
public class User extends UserBase {

    private String name;
    private String userName;
    private BmobFile image;
    private String personality;

    public User() {
    }

    public BmobFile getImage() {
        return image;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonality() {
        return personality;
    }

    public void setPersonality(String personality) {
        this.personality = personality;
    }
}
