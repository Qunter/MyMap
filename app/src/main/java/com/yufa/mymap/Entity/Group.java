package com.yufa.mymap.Entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by luyufa on 2016/9/3.
 */
public class Group extends BmobObject{

    private String groupName;
    private String userName;

    public Group() {

    }

    public Group(String groupName, String userName) {
        this.groupName = groupName;
        this.userName = userName;
    }
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
