package com.yufa.mymap.Entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by luyufa on 2016/10/22.
 */

public class Relationship extends BmobObject{

    private String userName;
    private String friend;

    public Relationship() {
    }

    public Relationship(String userName, String friend) {
        this.userName = userName;
        this.friend = friend;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }
}
