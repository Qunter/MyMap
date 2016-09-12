package com.yufa.mymap.Entity;

/**
 * Created by luyufa on 2016/9/12.
 */
public class DataString {
    private String str1;
    private String str2;
    private Boolean phone;

    public Boolean getPhone() {
        return phone;
    }

    public void setPhone(Boolean phone) {
        this.phone = phone;
    }

    public DataString() {
    }

    public DataString(String str1, String str2, Boolean phone) {
        this.str1 = str1;
        this.str2 = str2;
        this.phone = phone;
    }

    public DataString(String str1, String str2) {
        this.str1 = str1;
        this.str2 = str2;
    }

    public String getStr1() {
        return str1;
    }

    public void setStr1(String str1) {
        this.str1 = str1;
    }

    public String getStr2() {
        return str2;
    }

    public void setStr2(String str2) {
        this.str2 = str2;
    }
}
