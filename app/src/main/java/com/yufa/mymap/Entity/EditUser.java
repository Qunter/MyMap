package com.yufa.mymap.Entity;

/**
 * Created by Administrator on 2016/10/6.
 */

public class EditUser {

    private String username;
    private String personality;
    private String qq;
    private String wechat;
    private String sina;

    public EditUser(){}

    public EditUser(String username, String personality, String qq, String wechat, String sina) {
        this.username = username;
        this.personality = personality;
        this.qq = qq;
        this.wechat = wechat;
        this.sina = sina;
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

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getSina() {
        return sina;
    }

    public void setSina(String sina) {
        this.sina = sina;
    }
}
