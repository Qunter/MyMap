package com.yufa.mymap.Entity;

/**
 * Created by luyufa on 2016/8/28.
 */
public class User extends UserBase {

    private String name;
    private LocationInfo location;
    private String address;


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
}
