package com.yufa.mymap.Entity;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by luyufa on 2016/9/3.
 */
public class Group extends BmobObject{

    private Integer image;
    private String name;
    private String some;
    private List<Friend> friends;

    public Group() {

    }

    public Group(Integer image, String name, String some, List<Friend> friends) {
        this.image = image;
        this.name = name;
        this.some = some;
        this.friends = friends;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSome() {
        return some;
    }

    public void setSome(String some) {
        this.some = some;
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }
}
