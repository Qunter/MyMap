package com.yufa.mymap.Entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by luyufa on 2016/9/3.
 */
public class Friend extends BmobObject{
    private String name;
    private Integer image;
    private String address;
    private LocationInfo location;

    public Friend() {

    }

    public Friend(String name, Integer image, String address, LocationInfo location) {
        this.name = name;
        this.image = image;
        this.address = address;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocationInfo getLocation() {
        return location;
    }

    public void setLocation(LocationInfo location) {
        this.location = location;
    }
}
