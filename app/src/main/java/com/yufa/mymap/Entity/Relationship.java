package com.yufa.mymap.Entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by luyufa on 2016/10/22.
 */

public class Relationship extends BmobObject{

    private String userName;
    private User friend;

    public Relationship() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }
}
