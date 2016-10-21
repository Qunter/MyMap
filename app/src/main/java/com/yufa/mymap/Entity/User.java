package com.yufa.mymap.Entity;

/**
 * Created by luyufa on 2016/8/28.
 */
public class User extends UserBase {

    private String name;
    private LocationInfo location;
    private String address;
    private String qq;
    private String weChat;
    private String sinaweibo;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationInfo getLocation() {
        return location;
    }

    public void setLocation(LocationInfo location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}
