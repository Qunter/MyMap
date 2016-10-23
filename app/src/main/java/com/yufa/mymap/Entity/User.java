package com.yufa.mymap.Entity;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by luyufa on 2016/8/28.
 */
public class User extends UserBase {

    private String name;
    private String qq;
    private String weChat;
    private String sinaweibo;
    private BmobFile image;
    private String personality;

    public User() {
    }

    public BmobFile getImage() {
        return image;
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


    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public String getSinaweibo() {
        return sinaweibo;
    }

    public void setSinaweibo(String sinaweibo) {
        this.sinaweibo = sinaweibo;
    }

    public String getPersonality() {
        return personality;
    }

    public void setPersonality(String personality) {
        this.personality = personality;
    }
}
