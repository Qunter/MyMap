package com.yufa.mymap.Entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by luyufa on 2016/9/3.
 */
public class Group extends BmobObject{

    private String groupName;
    private String userName;
    private String friend;
    private String friendName;

    public Group() {

    }

    public Group(String groupName, String userName, String friend, String friendName) {
        this.groupName = groupName;
        this.userName = userName;
        this.friend = friend;
        this.friendName = friendName;
    }

    public Group(String userName, String friend, String friendName) {
        this.userName = userName;
        this.friend = friend;
        this.friendName = friendName;
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

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }
}
